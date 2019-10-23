package de.fhdo.lemma.typechecking

import de.fhdo.lemma.data.Type

/**
 * Interface for type checkers of a certain Type
 *
 * 
 */
interface TypeCheckerI<T extends Type> {
    def boolean compatible(T basicType, T typeToCheck)

    def String typeName(T type)
}