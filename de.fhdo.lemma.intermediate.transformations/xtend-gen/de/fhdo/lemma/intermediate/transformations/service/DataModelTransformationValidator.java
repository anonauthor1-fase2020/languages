package de.fhdo.lemma.intermediate.transformations.service;

import de.fhdo.lemma.data.DataModel;
import de.fhdo.lemma.intermediate.transformations.AbstractInputModelValidator;
import de.fhdo.lemma.intermediate.transformations.IntermediateTransformationException;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

/**
 * Validator for data models that shall be transformed.
 */
@SuppressWarnings("all")
public class DataModelTransformationValidator extends AbstractInputModelValidator<DataModel> {
  /**
   * Validate input models for errors
   */
  @Override
  public void checkInputModelForErrors(final DataModel dataModel) throws IntermediateTransformationException {
    if ((dataModel == null)) {
      this.error("Data model is empty");
    }
    boolean _isEmpty = IteratorExtensions.isEmpty(dataModel.eAllContents());
    if (_isEmpty) {
      this.error("Data model is empty");
    }
  }
}
