package pl.iseebugs.infrastructure.moduleCLI;

import pl.iseebugs.infrastructure.pomanalyzer.PomAnalyzer;
import pl.iseebugs.infrastructure.pomanalyzer.dto.PomData;

import java.util.Optional;

public class ModuleCLIFacade {
    private final ConsoleInputHandler inputHandler;
    private final ConsoleOutputHandler outputHandler;
    private final PomAnalyzer pomAnalyzer;

    public ModuleCLIFacade() {
        this.inputHandler = new ConsoleInputHandler();
        this.outputHandler = new ConsoleOutputHandler();
        this.pomAnalyzer = new PomAnalyzer();
    }

    public void run() {
        outputHandler.printMessage("I am starting project analyze...");

        PomData pomData = pomAnalyzer.analyzePom();
        if (pomData == null) {
            outputHandler.printError("There is no pom.xml in this directory");
            return;
        }

        Optional.of(pomData)
                .ifPresent(System.out::println);

        //TODO: loop
        String moduleName = inputHandler.askForString("Provide a module name (if empty then -> \"newmodule\"): ", "newmodule");
        String domainPackage = inputHandler.askForString("Provide a logic package name (if empty then -> \"domain\"): ", "domain");
        String infrastructurePackage = inputHandler.askForString("Provide a infrastructure package name (if empty then -> \"infrastructure\"): ", "adapters");

        //TODO: StructureGenerator
        String tree = "there will be structure tree";
        outputHandler.printTree(tree);
        //TODO: goto: loop

        //TODO: create xml file

        //TODO: create files
        outputHandler.printMessage("The module structure has been successfully built");
    }
}
