package de.fhdo.lemma.eclipse.ui.utils;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import java.io.File;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorMapping;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Utility class for the LEMMA UI plugin.
 */
@SuppressWarnings("all")
public final class LemmaUiUtils {
  public static final String SERVICE_DSL_EDITOR_ID = "de.fhdo.lemma.ServiceDsl";
  
  public static final String MAPPING_DSL_EDITOR_ID = "de.fhdo.lemma.technology.mappingdsl.MappingDsl";
  
  public static final String OPERATION_DSL_EDITOR_ID = "de.fhdo.lemma.operationdsl.OperationDsl";
  
  /**
   * Helper to get file extensions for registered DSL editors
   */
  public static List<String> getFileExtensions(final String... forEditorIds) {
    final IFileEditorMapping[] fileEditorMappings = PlatformUI.getWorkbench().getEditorRegistry().getFileEditorMappings();
    final Function1<IFileEditorMapping, Boolean> _function = (IFileEditorMapping it) -> {
      final Function1<IEditorDescriptor, String> _function_1 = (IEditorDescriptor it_1) -> {
        return it_1.getId();
      };
      final Function1<String, Boolean> _function_2 = (String it_1) -> {
        return Boolean.valueOf(((List<String>)Conversions.doWrapArray(forEditorIds)).contains(it_1));
      };
      return Boolean.valueOf(IterableExtensions.<String>exists(ListExtensions.<IEditorDescriptor, String>map(((List<IEditorDescriptor>)Conversions.doWrapArray(it.getEditors())), _function_1), _function_2));
    };
    final Iterable<IFileEditorMapping> editorDescriptions = IterableExtensions.<IFileEditorMapping>filter(((Iterable<IFileEditorMapping>)Conversions.doWrapArray(fileEditorMappings)), _function);
    final Function1<IFileEditorMapping, String> _function_1 = (IFileEditorMapping it) -> {
      return it.getExtension();
    };
    return IterableExtensions.<String>toList(IterableExtensions.<IFileEditorMapping, String>map(editorDescriptions, _function_1));
  }
  
  /**
   * Walk through workspace projects and find files with given extensions. If no extensions are
   * specified, all files of all projects will be retrieved.
   */
  public static HashMap<IProject, List<IFile>> findFilesInWorkspaceProjects(final String... extensions) {
    final HashMap<IProject, List<IFile>> resultFiles = CollectionLiterals.<IProject, List<IFile>>newHashMap();
    final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
    final Consumer<IProject> _function = (IProject it) -> {
      boolean _isOpen = it.isOpen();
      if (_isOpen) {
        final ArrayList<IFile> resultFilesForProject = LemmaUiUtils.findFilesInProject(it, extensions);
        resultFiles.put(it, resultFilesForProject);
      }
    };
    ((List<IProject>)Conversions.doWrapArray(projects)).forEach(_function);
    return resultFiles;
  }
  
  /**
   * Find files in project with the given extensions. If no extensions are specified, all files
   * will be retrieved.
   */
  public static ArrayList<IFile> findFilesInProject(final IProject project, final String... extensions) {
    try {
      if ((project == null)) {
        return null;
      }
      final Function1<IResource, Boolean> _function = (IResource it) -> {
        return Boolean.valueOf(((it instanceof IFolder) || (it instanceof IFile)));
      };
      final Iterable<IResource> foldersAndFiles = IterableExtensions.<IResource>filter(((Iterable<IResource>)Conversions.doWrapArray(project.members())), _function);
      List<IResource> _list = IterableExtensions.<IResource>toList(foldersAndFiles);
      final ArrayDeque<IResource> resourcesTodo = new ArrayDeque<IResource>(_list);
      final ArrayList<IFile> resultFiles = CollectionLiterals.<IFile>newArrayList();
      while ((!resourcesTodo.isEmpty())) {
        {
          final IResource resource = resourcesTodo.pop();
          if ((resource instanceof IFile)) {
            boolean _isEmpty = ((List<String>)Conversions.doWrapArray(extensions)).isEmpty();
            if (_isEmpty) {
              resultFiles.add(((IFile)resource));
            } else {
              boolean _hasExtension = LemmaUiUtils.hasExtension(((IFile)resource), extensions);
              if (_hasExtension) {
                resultFiles.add(((IFile)resource));
              }
            }
          } else {
            if ((resource instanceof IFolder)) {
              final Function1<IResource, Boolean> _function_1 = (IResource it) -> {
                return Boolean.valueOf(((it instanceof IFolder) || (it instanceof IFile)));
              };
              final Consumer<IResource> _function_2 = (IResource it) -> {
                resourcesTodo.push(it);
              };
              IterableExtensions.<IResource>filter(((Iterable<IResource>)Conversions.doWrapArray(((IFolder)resource).members())), _function_1).forEach(_function_2);
            }
          }
        }
      }
      return resultFiles;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Check if a file has one of the given extensions
   */
  public static boolean hasExtension(final IFile file, final String... fileExtensions) {
    if (((file.getFileExtension() == null) || file.getFileExtension().isEmpty())) {
      return false;
    }
    final String extensionLowerCase = file.getFileExtension().toLowerCase();
    final Function1<String, String> _function = (String it) -> {
      return it.toLowerCase();
    };
    return ListExtensions.<String, String>map(((List<String>)Conversions.doWrapArray(fileExtensions)), _function).contains(extensionLowerCase);
  }
  
  /**
   * Create enumeration text in Oxford comma style, e.g., "e1, e2, or e3"
   */
  public static Object createEnumerationText(final List<String> elements) {
    StringBuffer _xblockexpression = null;
    {
      if ((elements == null)) {
        return null;
      }
      int _size = elements.size();
      switch (_size) {
        case 0:
          return "";
        case 1:
          return elements.get(0);
        case 2:
          StringConcatenation _builder = new StringConcatenation();
          String _get = elements.get(0);
          _builder.append(_get);
          _builder.append(" or ");
          String _get_1 = elements.get(1);
          _builder.append(_get_1);
          return _builder.toString();
      }
      final StringBuffer enumerationText = new StringBuffer();
      int _size_1 = elements.size();
      int _minus = (_size_1 - 1);
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _minus, true);
      for (final Integer i : _doubleDotLessThan) {
        String _get_2 = elements.get((i).intValue());
        String _plus = (_get_2 + ", ");
        enumerationText.append(_plus);
      }
      String _last = IterableExtensions.<String>last(elements);
      String _plus_1 = ("or " + _last);
      _xblockexpression = enumerationText.append(_plus_1);
    }
    return _xblockexpression;
  }
  
  /**
   * Create an Image for the given filename with the given resource manager
   */
  public static Image createImage(final ResourceManager resourceManager, final Class<?> clazz, final String filename) {
    return resourceManager.createImage(LemmaUiUtils.createImageDescriptor(clazz, filename));
  }
  
  /**
   * Create an ImageDescriptor for the given filename and the bundle of this class
   */
  public static ImageDescriptor createImageDescriptor(final Class<?> clazz, final String filename) {
    final Bundle bundle = FrameworkUtil.getBundle(clazz);
    Path _path = new Path(("icons/" + filename));
    final URL url = FileLocator.find(bundle, _path, null);
    return ImageDescriptor.createFromURL(url);
  }
  
  /**
   * Load an Xtext file as XtextResource
   */
  public static XtextResource loadXtextResource(final IFile file) {
    try {
      final ResourceSet resourceSet = LemmaUiUtils.createResourceSetFor(file);
      if ((resourceSet == null)) {
        return null;
      }
      final URI fileUri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
      final Resource fileResource = resourceSet.getResource(fileUri, true);
      fileResource.load(null);
      return ((XtextResource) fileResource);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Load an XMI file
   */
  public static XMIResource loadXmiResource(final IFile file) {
    try {
      final ResourceSetImpl resourceSet = new ResourceSetImpl();
      final URI fullPathUri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
      final Resource resource = resourceSet.getResource(fullPathUri, true);
      resource.load(null);
      return ((XMIResource) resource);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Store file in XMI interchange format
   */
  public static void storeAsXmi(final IFile file, final String targetFilePath) {
    try {
      final XtextResource xtextResource = LemmaUiUtils.loadXtextResource(file);
      EcoreUtil2.resolveAll(xtextResource);
      final ResourceSet resourceSet = LemmaUiUtils.createResourceSetFor(file);
      final Resource xmiResource = resourceSet.createResource(URI.createURI(targetFilePath));
      xmiResource.getContents().add(xtextResource.getContents().get(0));
      xmiResource.save(null);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Validate an Xtext resource
   */
  public static List<Issue> validateXtextResource(final XtextResource resource) {
    final IResourceValidator validator = resource.getResourceServiceProvider().getResourceValidator();
    final List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
    return issues;
  }
  
  /**
   * Remove extension from filename and return filename without extension
   */
  public static String removeExtension(final IFile file, final Function<IFile, String> getBasePath) {
    if (((file == null) || (getBasePath == null))) {
      return null;
    }
    if (((file.getName() == null) || file.getName().isEmpty())) {
      return "";
    }
    if (((file.getFileExtension() == null) || file.getFileExtension().isEmpty())) {
      return file.getName();
    }
    final String basePath = getBasePath.apply(file);
    return LemmaUiUtils.removeExtension(basePath);
  }
  
  /**
   * Remove extension from filename and return filename without extension. The extension starts at
   * the last occurrence of a dot (".") in the filename.
   */
  public static String removeExtension(final String filename) {
    if ((filename == null)) {
      return null;
    }
    return filename.substring(0, filename.lastIndexOf("."));
  }
  
  /**
   * Get extension of filename. The extension starts at the last occurrence of a dot (".") in the
   * filename.
   */
  public static String getExtension(final String filename) {
    if ((filename == null)) {
      return null;
    }
    final String filenameWithoutPath = new File(filename).getName();
    final int lastIndexOfDot = filenameWithoutPath.lastIndexOf(".");
    String _xifexpression = null;
    if (((lastIndexOfDot > (-1)) && (filenameWithoutPath.length() > 1))) {
      _xifexpression = filenameWithoutPath.substring((lastIndexOfDot + 1));
    } else {
      _xifexpression = "";
    }
    return _xifexpression;
  }
  
  /**
   * Helper to create ResourceSet from file
   */
  public static ResourceSet createResourceSetFor(final IFile file) {
    final IResourceServiceProvider.Registry resourceSetProviderRegistry = IResourceServiceProvider.Registry.INSTANCE;
    final URI fileUri = URI.createURI(file.getFullPath().toString());
    final IResourceServiceProvider resourceSetProvider = resourceSetProviderRegistry.getResourceServiceProvider(fileUri);
    return resourceSetProvider.<ResourceSet>get(ResourceSet.class);
  }
  
  /**
   * Run UI event loop
   */
  public static void runEventLoop(final Shell shell) {
    final Display display = shell.getDisplay();
    while (((shell != null) && (!shell.isDisposed()))) {
      boolean _readAndDispatch = display.readAndDispatch();
      boolean _not = (!_readAndDispatch);
      if (_not) {
        display.sleep();
      }
    }
    boolean _isDisposed = display.isDisposed();
    boolean _not = (!_isDisposed);
    if (_not) {
      display.update();
    }
  }
  
  /**
   * Get all open editor instances of the current Eclipse workbench
   */
  public static <T extends IEditorPart> Iterable<T> getOpenEditorsOfType(final Class<T> editorType) {
    final Function1<IWorkbenchWindow, List<IWorkbenchPage>> _function = (IWorkbenchWindow it) -> {
      return IterableExtensions.<IWorkbenchPage>toList(((Iterable<IWorkbenchPage>)Conversions.doWrapArray(it.getPages())));
    };
    final Function1<IWorkbenchPage, List<IEditorReference>> _function_1 = (IWorkbenchPage it) -> {
      return IterableExtensions.<IEditorReference>toList(((Iterable<IEditorReference>)Conversions.doWrapArray(it.getEditorReferences())));
    };
    final Iterable<IEditorReference> allOpenEditorReferences = Iterables.<IEditorReference>concat(IterableExtensions.<IWorkbenchPage, List<IEditorReference>>map(Iterables.<IWorkbenchPage>concat(ListExtensions.<IWorkbenchWindow, List<IWorkbenchPage>>map(((List<IWorkbenchWindow>)Conversions.doWrapArray(PlatformUI.getWorkbench().getWorkbenchWindows())), _function)), _function_1));
    final Function1<IEditorReference, Boolean> _function_2 = (IEditorReference it) -> {
      boolean _xblockexpression = false;
      {
        final IEditorPart editor = it.getEditor(false);
        _xblockexpression = ((editor != null) && (editor.<T>getAdapter(editorType) != null));
      }
      return Boolean.valueOf(_xblockexpression);
    };
    final Function1<IEditorReference, T> _function_3 = (IEditorReference it) -> {
      T _adapter = it.getEditor(false).<T>getAdapter(editorType);
      return ((T) _adapter);
    };
    return IterableExtensions.<IEditorReference, T>map(IterableExtensions.<IEditorReference>filter(allOpenEditorReferences, _function_2), _function_3);
  }
}
