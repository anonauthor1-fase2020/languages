package de.fhdo.lemma.technology.mappingdsl;

import de.fhdo.lemma.service.Import;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy;
import org.eclipse.xtext.util.IAcceptor;

/**
 * Custom resource description strategy for the Mapping DSL.
 */
@SuppressWarnings("all")
public class MappingDslResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy {
  /**
   * Export selected EObjects from DSL models
   */
  @Override
  public boolean createEObjectDescriptions(final EObject eObject, final IAcceptor<IEObjectDescription> acceptor) {
    boolean _matched = false;
    if (eObject instanceof Import) {
      _matched=true;
      return false;
    }
    return super.createEObjectDescriptions(eObject, acceptor);
  }
}
