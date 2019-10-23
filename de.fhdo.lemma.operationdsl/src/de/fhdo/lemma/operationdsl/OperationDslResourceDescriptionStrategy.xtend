package de.fhdo.lemma.operationdsl

import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.util.IAcceptor
import org.eclipse.xtext.resource.IEObjectDescription
import de.fhdo.lemma.service.Import

/**
 * Custom resource description strategy for the OperationDsl.
 *
 * 
 */
class OperationDslResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy {
    /**
     * Export selected EObjects from DSL models
     */
    override createEObjectDescriptions(EObject eObject, IAcceptor<IEObjectDescription> acceptor) {
        switch (eObject) {
            // Don't export imports to prevent transitive imports
            Import: return false
        }

        return super.createEObjectDescriptions(eObject, acceptor)
    }
}