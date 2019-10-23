/**
 * generated by Xtext 2.12.0
 */
package de.fhdo.lemma.technology.ui;

import de.fhdo.lemma.technology.ui.AbstractTechnologyDslUiModule;
import de.fhdo.lemma.technology.ui.highlighting.HighlightingCalculator;
import de.fhdo.lemma.technology.ui.highlighting.HighlightingConfiguration;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor;
import org.eclipse.xtext.ide.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
@FinalFieldsConstructor
@SuppressWarnings("all")
public class TechnologyDslUiModule extends AbstractTechnologyDslUiModule {
  public Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration() {
    return HighlightingConfiguration.class;
  }
  
  public Class<? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator() {
    return HighlightingCalculator.class;
  }
  
  public TechnologyDslUiModule(final AbstractUIPlugin plugin) {
    super(plugin);
  }
}
