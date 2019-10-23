package de.fhdo.lemma.intermediate.transformations;

import de.fhdo.lemma.intermediate.transformations.IntermediateTransformationExceptionKind;
import de.fhdo.lemma.intermediate.transformations.IntermediateTransformationPhase;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Exception for intermediate transformation errors that allows to distinguish between warnings and
 * "real" errors.
 */
@SuppressWarnings("all")
public class IntermediateTransformationException extends Exception {
  @Accessors(AccessorType.PUBLIC_GETTER)
  private IntermediateTransformationExceptionKind kind;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private IntermediateTransformationPhase phase;
  
  public IntermediateTransformationException(final String message, final IntermediateTransformationExceptionKind kind, final IntermediateTransformationPhase phase) {
    super(message);
    this.kind = kind;
    this.phase = phase;
  }
  
  @Pure
  public IntermediateTransformationExceptionKind getKind() {
    return this.kind;
  }
  
  @Pure
  public IntermediateTransformationPhase getPhase() {
    return this.phase;
  }
}
