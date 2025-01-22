package pl.iseebugs;

import pl.iseebugs.infrastructure.moduleCLI.ModuleCLIFacade;

public class Main {
    public static void main(String[] args) {
        ModuleCLIFacade moduleCLIFacade = new ModuleCLIFacade();
        moduleCLIFacade.run();
    }
}