package pl.iseebugs.infrastructure.moduleCLI;

class ConsoleOutputHandler {
    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printError(String errorMessage) {
        System.err.println("Błąd: " + errorMessage);
    }

    public void printTree(String treeStructure) {
        System.out.println("\nStruktura modułu:\n" + treeStructure);
    }
}
