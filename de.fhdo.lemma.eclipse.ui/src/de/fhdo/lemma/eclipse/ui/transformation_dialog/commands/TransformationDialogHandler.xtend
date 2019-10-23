package de.fhdo.lemma.eclipse.ui.transformation_dialog.commands

import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import java.util.List
import de.fhdo.lemma.eclipse.ui.ModelFile
import de.fhdo.lemma.eclipse.ui.AbstractUiModelTransformationStrategy
import org.eclipse.ui.PlatformUI
import de.fhdo.lemma.eclipse.ui.transformation_dialog.TransformationDialog

/**
 * Handler for the transformation dialog.
 *
 * 
 */
class TransformationDialogHandler extends AbstractHandler {
    val SHELL = PlatformUI.workbench.activeWorkbenchWindow.shell

    List<ModelFile> inputModelFiles
    boolean outputRefinementModels
    AbstractUiModelTransformationStrategy strategy

    /**
     * Constructor
     */
    new(List<ModelFile> inputModelFiles, boolean outputRefinementModels,
        AbstractUiModelTransformationStrategy strategy) {
        if (inputModelFiles === null)
            throw new IllegalArgumentException("Model files must not be null")
        else if (inputModelFiles.empty)
            throw new IllegalArgumentException("Model files must not be empty")
        else if (strategy === null)
            throw new IllegalArgumentException("Strategy must not be null")

        this.inputModelFiles = inputModelFiles
        this.outputRefinementModels = outputRefinementModels
        this.strategy = strategy
    }

    /**
     * Execute handler
     */
    override execute(ExecutionEvent event) throws ExecutionException {
        /* Create dialog */
        val dialog = new TransformationDialog(SHELL, strategy, inputModelFiles,
            outputRefinementModels)
        dialog.create()
        dialog.openAndRunTransformations()

        return null
    }

}