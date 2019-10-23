package de.fhdo.lemma.typechecking;

import de.fhdo.lemma.data.Type;

/**
 * Interface for type checkers of a certain Type
 */
@SuppressWarnings("all")
public interface TypeCheckerI<T extends Type> {
  public abstract boolean compatible(final T basicType, final T typeToCheck);
  
  public abstract String typeName(final T type);
}
