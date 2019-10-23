/*
 * generated by Xtext 2.12.0
 */
package de.fhdo.lemma.data.scoping

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import de.fhdo.lemma.data.DataPackage
import de.fhdo.lemma.data.DataModel
import de.fhdo.lemma.data.ImportedComplexType
import org.eclipse.xtext.EcoreUtil2
import de.fhdo.lemma.data.Version
import de.fhdo.lemma.data.Context
import java.util.List
import de.fhdo.lemma.data.DataStructure
import org.eclipse.xtext.scoping.Scopes
import de.fhdo.lemma.utils.LemmaUtils
import de.fhdo.lemma.data.DataOperation
import de.fhdo.lemma.data.DataField
import de.fhdo.lemma.data.ListType
import de.fhdo.lemma.data.ComplexType

/**
 * Scope provider for data models.
 *
 * 
 */
class DataDslScopeProvider extends AbstractDataDslScopeProvider {
    /**
     * Build scope for a given context and reference
     */
    override getScope(EObject context, EReference reference) {
        val scope = switch (context) {
            /* Data models */
            DataModel: context.getScope(reference)

            /* Imported complex types */
            ImportedComplexType: context.getScope(reference)

            /* Data structures */
            DataStructure: context.getScope(reference)

            /* List types */
            ListType: context.getScope(reference)

            /* Data fields */
            DataField: context.getScope(reference)

            /* Data operations */
            DataOperation: context.getScope(reference)
        }

        if (scope !== null)
            return scope
        // Try default scope resolution, if no scope could be determined
        else if (scope === null)
            return super.getScope(context, reference)
    }

    /**
     * Build scope for the given reference in the context of a data model
     */
    private def getScope(DataModel dataModel, EReference reference) {
        switch (reference) {
            case DataPackage::Literals.DATA_FIELD__COMPLEX_TYPE:
                return dataModel.getScopeForPossiblyImportedComplexTypes(null)
        }
    }

    /**
     * Build scope for the given reference in the context of an imported complex type
     */
    private def getScope(ImportedComplexType complexType, EReference reference) {
        switch (reference) {
            case DataPackage::Literals.IMPORTED_COMPLEX_TYPE__IMPORT: {
                val dataModel = EcoreUtil2.getContainerOfType(complexType, DataModel)
                return Scopes::scopeFor(dataModel.complexTypeImports)
            }

            case DataPackage::Literals.IMPORTED_COMPLEX_TYPE__IMPORTED_TYPE:
                return complexType
                    .getScopeForPossiblyImportedComplexTypes(complexType.import?.importURI)
        }
    }

    /**
     * Convenience method to create a scope for imported complex types of certain types
     */
    private def getScopeForPossiblyImportedComplexTypes(EObject context, String importUri) {
        /* Determine container and qualified name parts of imported complex type */
        var EObject container
        var List<String> qualifiedNameParts
        val containingContext = EcoreUtil2.getContainerOfType(context, Context)
        val containingVersion = EcoreUtil2.getContainerOfType(context, Version)
        val containingDataModel = EcoreUtil2.getContainerOfType(context, DataModel)

        if (containingContext !== null) {
            container = containingContext
            qualifiedNameParts = containingContext.qualifiedNameParts
        } else if (containingVersion !== null) {
            container = containingVersion
            qualifiedNameParts = containingVersion.qualifiedNameParts
        } else if (containingDataModel !== null) {
            container = containingDataModel
            qualifiedNameParts = null
        }

        /* Build and return scope */
        return LemmaUtils.getScopeForPossiblyImportedConcept(
            container,
            qualifiedNameParts,
            DataModel,
            importUri,
            [allComplexTypesDefinedIn(it)],
            [it.qualifiedNameParts]
        )
   }

    /**
     * Helper to get all complex types being defined in a data model
     */
    private def List<ComplexType> allComplexTypesDefinedIn(DataModel dataModel) {
        val allComplexTypes = <ComplexType>newArrayList

        // Get ComplexTypes encapsulated in versions
        dataModel.versions.forEach[
            allComplexTypes.addAll(contexts.map[complexTypes].flatten.toList)
            allComplexTypes.addAll(complexTypes)
        ]

        // Get ComplexTypes encapsulated in contexts
        allComplexTypes.addAll(dataModel.contexts.map[complexTypes].flatten.toList)

        allComplexTypes.addAll(dataModel.complexTypes)

        return allComplexTypes
    }

    /**
     * Build scope for the given reference in the context of a data structure
     */
    private def getScope(DataStructure structure, EReference reference) {
        switch (reference) {
            case DataPackage::Literals.DATA_STRUCTURE__SUPER:
                return structure.getScopeForSuperStructures()

            case DataPackage::Literals.IMPORTED_COMPLEX_TYPE__IMPORT: {
                val dataModel = EcoreUtil2.getContainerOfType(structure, DataModel)
                return Scopes::scopeFor(dataModel.complexTypeImports)
            }

            case DataPackage::Literals.DATA_FIELD__COMPLEX_TYPE:
                return structure.getScopeForPossiblyImportedComplexTypes(null)
        }
    }

    /**
    * Convenience method to create a scope for super structures
    */
   private def getScopeForSuperStructures(DataStructure structure) {
        // Data structures may only inherit from data structures in the same model
        val modelRoot = EcoreUtil2.getContainerOfType(structure, DataModel)
        val localStructures = allComplexTypesDefinedIn(modelRoot)
            .filter[it instanceof DataStructure && it != structure]
        return LemmaUtils.getScopeWithRelativeQualifiedNames(
            localStructures.toList,
            [qualifiedNameParts],
            structure,
            structure.qualifiedNameParts,
            DataModel
        )
   }

    /**
     * Build scope for the given reference in the context of a data structure
     */
    private def getScope(ListType listType, EReference reference) {
        switch (reference) {
            case DataPackage::Literals.IMPORTED_COMPLEX_TYPE__IMPORT: {
                val dataModel = EcoreUtil2.getContainerOfType(listType, DataModel)
                return Scopes::scopeFor(dataModel.complexTypeImports)
            }

            case DataPackage::Literals.DATA_FIELD__COMPLEX_TYPE:
                return listType.getScopeForPossiblyImportedComplexTypes(null)
        }
    }

    /**
     * Build scope for the given reference in the context of a data field
     */
    private def getScope(DataField field, EReference reference) {
        switch (reference) {
            case DataPackage::Literals.IMPORTED_COMPLEX_TYPE__IMPORT: {
                val dataModel = EcoreUtil2.getContainerOfType(field, DataModel)
                return Scopes::scopeFor(dataModel.complexTypeImports)
            }

            case DataPackage::Literals.DATA_FIELD__COMPLEX_TYPE:
                return field.getScopeForPossiblyImportedComplexTypes(null)
        }
    }

    /**
     * Build scope for the given reference in the context of a data operation
     */
    private def getScope(DataOperation operation, EReference reference) {
        switch (reference) {
            case DataPackage::Literals.IMPORTED_COMPLEX_TYPE__IMPORT: {
                val dataModel = EcoreUtil2.getContainerOfType(operation, DataModel)
                return Scopes::scopeFor(dataModel.complexTypeImports)
            }

            case DataPackage::Literals.DATA_OPERATION_PARAMETER__COMPLEX_TYPE,
            case DataPackage::Literals.DATA_OPERATION__COMPLEX_RETURN_TYPE:
                return operation.getScopeForPossiblyImportedComplexTypes(null)
        }
    }
}