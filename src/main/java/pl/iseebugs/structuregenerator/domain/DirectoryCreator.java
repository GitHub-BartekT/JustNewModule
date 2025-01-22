package pl.iseebugs.structuregenerator.domain;

import pl.iseebugs.structuregenerator.dto.ModuleStructure;

import java.nio.file.Files;
import java.nio.file.Path;

class DirectoryCreator {

    public static void createDirectories(ModuleStructure structure, Path basePath) {
        Path currentPath = basePath.resolve(structure.getName());
        createDirectoryIfNotExists(structure.getName(), currentPath);

        for (ModuleStructure child : structure.getChildren()) {
            createDirectories(child, currentPath);
        }
    }

    public static void createDirectoryIfNotExists(String folderName, Path folderPath) {
        if (Files.exists(folderPath)) {
            System.out.println("Directory '" + folderName + "' already exists.");
        } else {
            try {
                Files.createDirectory(folderPath);
                System.out.println("Directory " + folderName + " has created.");
            } catch (Exception e) {
                System.out.println("Something goes wrong with directory: " + folderName + "\n" + e.getMessage());
            }
        }
    }

    public static void createDirectoryStructure(){

    }
}
