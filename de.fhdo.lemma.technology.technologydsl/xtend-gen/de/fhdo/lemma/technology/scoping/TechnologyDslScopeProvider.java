/**
 * generated by Xtext 2.12.0
 */
package de.fhdo.lemma.technology.scoping;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import de.fhdo.lemma.data.Type;
import de.fhdo.lemma.technology.CommunicationType;
import de.fhdo.lemma.technology.CompatibilityMatrixEntry;
import de.fhdo.lemma.technology.DataFormat;
import de.fhdo.lemma.technology.JoinPointType;
import de.fhdo.lemma.technology.OperationAspect;
import de.fhdo.lemma.technology.OperationAspectPointcut;
import de.fhdo.lemma.technology.PossiblyImportedTechnologySpecificType;
import de.fhdo.lemma.technology.Protocol;
import de.fhdo.lemma.technology.ServiceAspectPointcut;
import de.fhdo.lemma.technology.Technology;
import de.fhdo.lemma.technology.TechnologyImport;
import de.fhdo.lemma.technology.TechnologyPackage;
import de.fhdo.lemma.technology.scoping.AbstractTechnologyDslScopeProvider;
import de.fhdo.lemma.typechecking.TypecheckingUtils;
import de.fhdo.lemma.utils.LemmaUtils;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.MapBasedScope;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * This class implements a custom scope provider for the Technology DSL.
 */
@SuppressWarnings("all")
public class TechnologyDslScopeProvider extends AbstractTechnologyDslScopeProvider {
  /**
   * Build scope for a given context and reference
   */
  @Override
  public IScope getScope(final EObject context, final EReference reference) {
    IScope _switchResult = null;
    boolean _matched = false;
    if (context instanceof Protocol) {
      _matched=true;
      _switchResult = this.getScope(((Protocol)context), reference);
    }
    if (!_matched) {
      if (context instanceof PossiblyImportedTechnologySpecificType) {
        _matched=true;
        _switchResult = this.getScope(((PossiblyImportedTechnologySpecificType)context), reference);
      }
    }
    if (!_matched) {
      if (context instanceof CompatibilityMatrixEntry) {
        _matched=true;
        _switchResult = this.getScope(((CompatibilityMatrixEntry)context), reference);
      }
    }
    if (!_matched) {
      if (context instanceof ServiceAspectPointcut) {
        _matched=true;
        _switchResult = this.getScope(((ServiceAspectPointcut)context), reference);
      }
    }
    if (!_matched) {
      if (context instanceof OperationAspectPointcut) {
        _matched=true;
        _switchResult = this.getScope(((OperationAspectPointcut)context), reference);
      }
    }
    final IScope scope = _switchResult;
    if ((scope != null)) {
      return scope;
    } else {
      if ((scope == null)) {
        return super.getScope(context, reference);
      }
    }
    return null;
  }
  
  /**
   * Build scope for possibly imported technology-specific types
   */
  private IScope getScope(final PossiblyImportedTechnologySpecificType type, final EReference reference) {
    boolean _notEquals = (!Objects.equal(reference, 
      TechnologyPackage.Literals.POSSIBLY_IMPORTED_TECHNOLOGY_SPECIFIC_TYPE__TYPE));
    if (_notEquals) {
      return null;
    }
    String _xifexpression = null;
    TechnologyImport _import = type.getImport();
    boolean _tripleNotEquals = (_import != null);
    if (_tripleNotEquals) {
      _xifexpression = type.getImport().getImportURI();
    }
    final String importUri = _xifexpression;
    return this.getScopeForPossiblyImportedType(type, importUri);
  }
  
  /**
   * Build scope for possibly imported technology-specific types with the containing compatibility
   * matrix as context. Note that the matrix is passed as context, if there is not import given.
   */
  private IScope getScope(final CompatibilityMatrixEntry entry, final EReference reference) {
    return this.getScopeForPossiblyImportedType(entry, null);
  }
  
  /**
   * Build scope for service aspect pointcuts
   */
  private IScope getScope(final ServiceAspectPointcut pointcut, final EReference reference) {
    boolean _matched = false;
    if (Objects.equal(reference, TechnologyPackage.Literals.SERVICE_ASPECT_POINTCUT__PROTOCOL)) {
      _matched=true;
      return this.getScopeForPointcutProtocols(pointcut);
    }
    if (!_matched) {
      if (Objects.equal(reference, TechnologyPackage.Literals.SERVICE_ASPECT_POINTCUT__DATA_FORMAT)) {
        _matched=true;
        return this.getScopeForPointcutDataFormats(pointcut);
      }
    }
    return null;
  }
  
  /**
   * Build scope for protocols of aspects pointcuts
   */
  private IScope getScopeForPointcutProtocols(final ServiceAspectPointcut pointcut) {
    if ((pointcut == null)) {
      return null;
    }
    final Function1<ServiceAspectPointcut, Boolean> _function = (ServiceAspectPointcut it) -> {
      return Boolean.valueOf(it.isForCommunicationType());
    };
    final Function1<ServiceAspectPointcut, CommunicationType> _function_1 = (ServiceAspectPointcut it) -> {
      return it.getCommunicationType();
    };
    final List<CommunicationType> communicationTypePointcuts = IterableExtensions.<CommunicationType>toList(IterableExtensions.<ServiceAspectPointcut, CommunicationType>map(IterableExtensions.<ServiceAspectPointcut>filter(pointcut.getSelector().getPointcuts(), _function), _function_1));
    EObject _rootContainer = EcoreUtil2.getRootContainer(pointcut);
    final Technology technologyModel = ((Technology) _rootContainer);
    Iterable<Protocol> _xifexpression = null;
    boolean _isEmpty = communicationTypePointcuts.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      final Function1<Protocol, Boolean> _function_2 = (Protocol it) -> {
        return Boolean.valueOf(communicationTypePointcuts.contains(it.getCommunicationType()));
      };
      _xifexpression = IterableExtensions.<Protocol>filter(technologyModel.getProtocols(), _function_2);
    } else {
      _xifexpression = technologyModel.getProtocols();
    }
    Iterable<Protocol> scopeElements = _xifexpression;
    return Scopes.scopeFor(scopeElements);
  }
  
  /**
   * Build scope for data formats of aspects pointcuts
   */
  private IScope getScopeForPointcutDataFormats(final ServiceAspectPointcut pointcut) {
    if ((pointcut == null)) {
      return null;
    }
    final Function1<ServiceAspectPointcut, Boolean> _function = (ServiceAspectPointcut it) -> {
      return Boolean.valueOf(it.isForProtocol());
    };
    final List<ServiceAspectPointcut> protocolPointcuts = IterableExtensions.<ServiceAspectPointcut>toList(IterableExtensions.<ServiceAspectPointcut>filter(pointcut.getSelector().getPointcuts(), _function));
    List<DataFormat> _xifexpression = null;
    boolean _isEmpty = protocolPointcuts.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      final Function1<ServiceAspectPointcut, Protocol> _function_1 = (ServiceAspectPointcut it) -> {
        return it.getProtocol();
      };
      final Function1<Protocol, EList<DataFormat>> _function_2 = (Protocol it) -> {
        return it.getDataFormats();
      };
      _xifexpression = IterableExtensions.<DataFormat>toList(Iterables.<DataFormat>concat(ListExtensions.<Protocol, EList<DataFormat>>map(ListExtensions.<ServiceAspectPointcut, Protocol>map(protocolPointcuts, _function_1), _function_2)));
    } else {
      List<DataFormat> _xblockexpression = null;
      {
        EObject _rootContainer = EcoreUtil2.getRootContainer(pointcut);
        final Technology technologyModel = ((Technology) _rootContainer);
        final Function1<Protocol, EList<DataFormat>> _function_3 = (Protocol it) -> {
          return it.getDataFormats();
        };
        _xblockexpression = IterableExtensions.<DataFormat>toList(Iterables.<DataFormat>concat(ListExtensions.<Protocol, EList<DataFormat>>map(technologyModel.getProtocols(), _function_3)));
      }
      _xifexpression = _xblockexpression;
    }
    List<DataFormat> scopeElements = _xifexpression;
    final Function<DataFormat, QualifiedName> _function_3 = (DataFormat it) -> {
      return QualifiedName.create(it.getFormatName());
    };
    return Scopes.<DataFormat>scopeFor(scopeElements, _function_3, 
      IScope.NULLSCOPE);
  }
  
  /**
   * Build scope for operation aspect pointcuts
   */
  private IScope getScope(final OperationAspectPointcut pointcut, final EReference reference) {
    boolean _matched = false;
    if (Objects.equal(reference, TechnologyPackage.Literals.OPERATION_ASPECT_POINTCUT__TECHNOLOGY)) {
      _matched=true;
      return this.getScopeForOperationAspectPointcutTechnologies(pointcut);
    }
    return null;
  }
  
  /**
   * Build scope for technologies of operation aspects pointcuts
   */
  private IScope getScopeForOperationAspectPointcutTechnologies(final OperationAspectPointcut pointcut) {
    if ((pointcut == null)) {
      return null;
    }
    final EList<JoinPointType> joinPoints = EcoreUtil2.<OperationAspect>getContainerOfType(pointcut, OperationAspect.class).getJoinPoints();
    final Technology modelRoot = EcoreUtil2.<Technology>getContainerOfType(pointcut, Technology.class);
    final ArrayList<EObject> scopeElements = CollectionLiterals.<EObject>newArrayList();
    boolean _contains = joinPoints.contains(JoinPointType.CONTAINERS);
    if (_contains) {
      scopeElements.addAll(modelRoot.getDeploymentTechnologies());
    }
    boolean _contains_1 = joinPoints.contains(JoinPointType.INFRASTRUCTURE_NODES);
    if (_contains_1) {
      scopeElements.addAll(modelRoot.getInfrastructureTechnologies());
    }
    return Scopes.scopeFor(scopeElements);
  }
  
  /**
   * Build scope for possibly imported technology-specific types w.r.t. the import's URI
   */
  private IScope getScopeForPossiblyImportedType(final EObject context, final String importUri) {
    final Technology containingTechnology = EcoreUtil2.<Technology>getContainerOfType(context, Technology.class);
    if ((containingTechnology == null)) {
      return IScope.NULLSCOPE;
    }
    final Function<Technology, List<Type>> _function = (Technology it) -> {
      final List<Type> types = CollectionLiterals.<Type>newArrayList();
      types.addAll(it.getPrimitiveTypes());
      types.addAll(it.getListTypes());
      types.addAll(it.getDataStructures());
      return types;
    };
    final Function<Type, List<String>> _function_1 = (Type it) -> {
      return TypecheckingUtils.getTypeNameParts(it, true);
    };
    final IScope scopedElements = LemmaUtils.<Technology, Technology, Type>getScopeForPossiblyImportedConcept(containingTechnology, 
      null, 
      Technology.class, importUri, _function, _function_1);
    return scopedElements;
  }
  
  /**
   * Build scope for protocol default formats
   */
  private IScope getScope(final Protocol protocol, final EReference reference) {
    if ((protocol == null)) {
      return IScope.NULLSCOPE;
    }
    final Function1<DataFormat, IEObjectDescription> _function = (DataFormat it) -> {
      return EObjectDescription.create(it.getFormatName(), it);
    };
    final List<IEObjectDescription> scopeElements = ListExtensions.<DataFormat, IEObjectDescription>map(protocol.getDataFormats(), _function);
    return MapBasedScope.createScope(IScope.NULLSCOPE, scopeElements);
  }
}
