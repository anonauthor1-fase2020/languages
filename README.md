## Source Code and Pre-Bundled Eclipse Versions of the Modeling Languages Mentioned in our FASE2020 Paper on Architecture Reconstruction of Microservice-based Software Systems

This repository contains the modeling languages, model transformations, and related artifacts of the of the modeling languages mentioned in our FASE2020 paper on Architecture Reconstruction of Microservice-based Software Systems.

Please follow these steps if you want to try out the modeling languages on the basis of the [Lakeside Mutual](https://github.com/Microservice-API-Patterns/LakesideMutual) application, which we used for [evaluating](https://github.com/anonauthor1-fase2020/evaluation-package) our reconstruction process:
1. The easiest way to try out the languages is to download one of the [pre-bundled Eclipse releases](https://github.com/anonauthor1-fase2020/languages/releases) for your OS. Then clone the [evaluation repository](https://github.com/anonauthor1-fase2020/evaluation-package) to your harddrive, run the ``eclipse`` executable of the pre-bundled release, and go to step 11.  

   If you are interested in the modeling language's implementation, either download one of the pre-bundled [releases](https://github.com/anonauthor1-fase2020/languages/releases) for your OS and go to step 4. Otherwise, if you instead want to setup your own Eclipse environment for both studying the language's implementation and trying them out, download the [Eclipse IDE for Java and DSL Developers](https://www.eclipse.org/downloads/packages/release/2019-09/r/eclipse-ide-java-and-dsl-developers) first. Then continue with step 2.
2. Run the downloaded Eclipse package by starting the ``eclipse`` executable.
3. Install the [ATL Transformation Language](https://marketplace.eclipse.org/content/atl) from the Eclipse Marketplace in the downloaded Eclipse package.
4. Clone this repository to your harddrive.
5. In the popup dialog, choose an arbitrary workspace location on your harddrive and hit the ``Launch`` button.
6. In the IDE, choose the menu entry ``File > Open Projects from File System...``.
7. In the popup dialog, select the folder of the cloned repository as ``Import Source`` and deselect the ``Example`` entry in the folder list.
8. Click the ``Finish`` button, wait until the importing has completed, and switch to the Eclipse workbench.
9. Wait for any possibly running background tasks, e.g., ``Workspace Build``, to finish.
10. In the Package Explorer, right click on the project ``de.fhdo.lemma.servicedsl`` and choose the menu entry ``Run As > Eclipse Application`` from the context menu. If a new popup dialog with several "Launch Configurations" appears, hit ``OK``. A new Eclipse runtime environment is started.
11. In the started Eclipse runtime environment, choose the menu entry ``File > Open Projects from File System...``.
12. In the popup dialog, select the folder ``Lakeside Mutual/Reconstructed Models`` from the cloned [evaluation repository](https://github.com/anonauthor1-fase2020/evaluation-package) as ``Import Source``.
13. Click the ``Finish`` button and wait until the importing has completed.

The reconstructed ``Lakeside Mutual`` models are now available in the Project Explorer of the Eclipse runtime environment. Except for ``technology``, each of the displayed folders corresponds to a reconstructed ``Lakeside Mutual`` microservice as described in the Evaluation section of the paper. Each microservice-related folder contains four files:
- Files with the ``data`` extension represent reconstructed domain models.
- Files with the ``mapping`` extension represent reconstructed mapping models.
- Files with the ``operation`` extension represent reconstructed operation models.
- Files with the ``services`` extension represent reconstructed service models.

The ``technology`` sub-folder of the ``Lakeside Mutual`` folder contains the reconstructed technology models being mentioned in the paper, i.e., files with the ``technology`` extension.
