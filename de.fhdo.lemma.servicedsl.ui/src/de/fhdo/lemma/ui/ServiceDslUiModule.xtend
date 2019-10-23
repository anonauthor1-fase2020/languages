/*
 * generated by Xtext 2.12.0
 */
package de.fhdo.lemma.ui

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration
import org.eclipse.xtext.ide.editor.syntaxcoloring.ISemanticHighlightingCalculator
import de.fhdo.lemma.ui.highlighting.HighlightingCalculator
import de.fhdo.lemma.ui.highlighting.HighlightingConfiguration
import org.eclipse.xtext.ui.editor.autoedit.DefaultAutoEditStrategyProvider
import de.fhdo.lemma.ui.autoedit.ServiceDslAutoEditStrategyProvider

/**
 * Use this class to register components to be used within the Eclipse IDE.
 *
 * 
 */
@FinalFieldsConstructor
class ServiceDslUiModule extends AbstractServiceDslUiModule {
    def Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration() {
        HighlightingConfiguration
    }

    def Class<? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator() {
        HighlightingCalculator
    }

    def Class<? extends DefaultAutoEditStrategyProvider> bindDefaultAutoEditStrategyProvider() {
        ServiceDslAutoEditStrategyProvider
    }
}
