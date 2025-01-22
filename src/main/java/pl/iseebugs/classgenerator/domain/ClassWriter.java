package pl.iseebugs.classgenerator.domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class ClassWriter {

    public static void writeFile(String filePath, String content) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }
            System.out.println("File created: " + filePath);
        } catch (IOException e) {
            System.err.println("Exception during creating of file: " + filePath);
            e.printStackTrace();
        }
    }
}
