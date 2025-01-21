package pl.iseebugs.infrastructure.moduleCLI;

import java.util.Scanner;

class ConsoleInputHandler {
    private final Scanner scanner;

    public ConsoleInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public String askForString(String message, String defaultValue) {
        System.out.print(message + (defaultValue != null ? " (domyślnie: " + defaultValue + "): " : ": "));
        String input = scanner.nextLine().trim();
        return input.isEmpty() && defaultValue != null ? defaultValue : input;
    }

    public boolean askForConfirmation(String message) {
        System.out.print(message + " [y/n]: ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }
}
