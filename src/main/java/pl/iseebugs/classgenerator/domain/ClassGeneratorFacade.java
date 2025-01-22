package pl.iseebugs.classgenerator.domain;

import pl.iseebugs.structuregenerator.dto.ModuleProperties;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ClassGeneratorFacade {


    public static void generatePort(ModuleProperties moduleProperties) {
        Path basePath = Path.of(System.getProperty("user.dir"), "src", "main", "java");
        String packagePath = moduleProperties.getGroupId()
                + "." + moduleProperties.getModuleName()
                + "." + moduleProperties.getLogicPackage()                ;

        String fullPackagePath = basePath.resolve(packagePath.replace(".", File.separator)).toString();
        String fileName = moduleProperties.getModuleName() + "Port";
        String filePath= fullPackagePath + File.separator + fileName + ".java";

        String moduleName = capitalizeFirstLetter(moduleProperties.getModuleName().toLowerCase());
        String moduleDTO = moduleName + "DTO";
        String modulePath = moduleProperties.getGroupId() + "." +
                moduleProperties.getModuleName();
        String logicPath = modulePath + "." +
                moduleProperties.getLogicPackage();
        String dtoPath = modulePath + ".dto";

            String content ="package " + logicPath + ";\n\n"
                + "import " + dtoPath + " " + moduleDTO + ";\n\n"
                + "import java.util.List;\n\n"
                + "public interface " + fileName + " {\n"
                + "    void create" + moduleName + "(" + moduleDTO + " " + moduleName.toLowerCase() + ");\n"
                + "    " + moduleDTO + " findById(Long id);\n"
                + "    List<" + moduleDTO + "> findAll();\n"
                + "    void update" + moduleName + "(Long id, " + moduleDTO + " " + moduleName.toLowerCase() + ");\n"
                + "    void delete" + moduleName + "(Long id);\n"
                + "}\n";

        ClassWriter.writeFile(filePath, content);
    }

    private static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
