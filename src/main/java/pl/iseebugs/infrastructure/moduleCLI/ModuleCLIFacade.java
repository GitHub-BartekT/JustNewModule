package pl.iseebugs.infrastructure.moduleCLI;

import pl.iseebugs.domain.structuregenerator.dto.ModuleProperties;
import pl.iseebugs.infrastructure.pomanalyzer.PomAnalyzer;
import pl.iseebugs.infrastructure.pomanalyzer.dto.PomData;

import java.util.Optional;

public class ModuleCLIFacade implements ModuleCLIPort {
    private final ConsoleInputHandler inputHandler;
    private final ConsoleOutputHandler outputHandler;
    private final PomAnalyzer pomAnalyzer;

    public ModuleCLIFacade() {
        this.inputHandler = new ConsoleInputHandler();
        this.outputHandler = new ConsoleOutputHandler();
        this.pomAnalyzer = new PomAnalyzer();
    }

    public void run() {

        ModuleProperties moduleProperties = new ModuleProperties();
        outputHandler.printMessage("I am starting to analyze the project ...");

        //TODO Is there any JNM-name.xml file?
        // if yes then use it instead of pom.xml

        PomData pomData = pomAnalyzer.analyzePom();
        if (pomData == null) {
            outputHandler.printError("There is no pom.xml in this directory");
            return;
        }

        boolean hasSpringBootDependency = pomData.getDependencies().stream().anyMatch(dep -> dep.contains("spring-boot"));
        boolean hasSpringLombok = pomData.getDependencies().stream().anyMatch(dep -> dep.contains("lombok"));

        moduleProperties.setGroupId(pomData.getGroupId());
        moduleProperties.setArtifactId(pomData.getArtifactId());
        moduleProperties.setHasSpringBoot(hasSpringBootDependency);
        moduleProperties.setHasLombok(hasSpringLombok);

        Optional.of(pomData)
                .ifPresent(System.out::println);

        //TODO: loop


        String moduleName = inputHandler.askForString("Provide a module name (if empty then -> \"newmodule\"): ", "newmodule");
        String logicPackage = inputHandler.askForString("Provide a logic package name (if empty then -> \"domain\"): ", "domain");
        String infrastructurePackage = inputHandler.askForString("Provide a infrastructure package name (if empty then -> \"infrastructure\"): ", "infrastructure");

        moduleProperties.setModuleName(moduleName);
        moduleProperties.setLogicPackage(logicPackage);
        moduleProperties.setInfrastructurePackage(infrastructurePackage);

        //TODO: StructureGenerator
        String tree = "there will be structure tree";
        outputHandler.printTree(tree);
        //TODO: goto: loop

        //TODO: create xml file

        //TODO: create files
        outputHandler.printMessage("The module structure has been successfully built");
    }
}
