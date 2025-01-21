package pl.iseebugs;

import pl.iseebugs.infrastructure.moduleCLI.ModuleCLIFacade;
import pl.iseebugs.infrastructure.pomanalyzer.PomAnalyzer;
import pl.iseebugs.infrastructure.pomanalyzer.dto.PomData;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        ModuleCLIFacade moduleCLIFacade = new ModuleCLIFacade();
        moduleCLIFacade.run();
    }
}