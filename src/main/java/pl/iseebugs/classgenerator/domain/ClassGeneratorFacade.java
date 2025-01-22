package pl.iseebugs.classgenerator.domain;

import pl.iseebugs.structuregenerator.dto.ModuleProperties;

import java.io.File;
import java.nio.file.Path;

public class ClassGeneratorFacade {


    public static void generatePort(ModuleProperties moduleProperties) {
        Path basePath = Path.of(System.getProperty("user.dir"), "src", "main", "java");
        String packagePath = moduleProperties.getGroupId()
                + "." + moduleProperties.getModuleName()
                + "." + moduleProperties.getLogicPackage()                ;

        String fullPackagePath = basePath.resolve(packagePath.replace(".", File.separator)).toString();
        String fileName = capitalizeFirstLetter(moduleProperties.getModuleName() + "Port");
        String filePath= fullPackagePath + File.separator + fileName + ".java";

        String moduleName = capitalizeFirstLetter(moduleProperties.getModuleName().toLowerCase());
        String moduleDTO = moduleName + "DTO";
        String modulePath = moduleProperties.getGroupId() + "." +
                moduleProperties.getModuleName();
        String logicPath = modulePath + "." +
                moduleProperties.getLogicPackage();
        String dtoPath = modulePath + ".dto";

            String content ="package " + logicPath + ";\n\n"
                + "import " + dtoPath + "." + moduleDTO + ";\n\n"
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

    public static void generateException(ModuleProperties moduleProperties) {
        Path basePath = Path.of(System.getProperty("user.dir"), "src", "main", "java");
        String packagePath = moduleProperties.getGroupId()
                + "." + moduleProperties.getModuleName()
                + "." + moduleProperties.getLogicPackage()                ;
        String fullPackagePath = basePath.resolve(packagePath.replace(".", File.separator)).toString();

        String fileName = capitalizeFirstLetter(moduleProperties.getModuleName() + "NotFoundException");
        String filePath= fullPackagePath + File.separator + fileName + ".java";

        String content = "package " + packagePath + ";\n\n"
                + "public class " + fileName + " extends Exception {\n\n"
                + "    public " + fileName + "() {\n"
                + "        super(\"" + moduleProperties.getModuleName() + " not found.\");\n"
                + "    }\n"
                + "    public " + fileName + "(String message) {\n"
                + "        super(message);\n"
                + "    }\n"
                + "}\n";

        ClassWriter.writeFile(filePath, content);
    }

    public static void generateRepository(ModuleProperties moduleProperties) {
        Path basePath = Path.of(System.getProperty("user.dir"), "src", "main", "java");
        String packagePath = moduleProperties.getGroupId()
                + "." + moduleProperties.getModuleName()
                + "." + moduleProperties.getLogicPackage();
        String fullPackagePath = basePath.resolve(packagePath.replace(".", File.separator)).toString();

        String className = capitalizeFirstLetter(moduleProperties.getModuleName());
        String fileName = className + "Repository";
        String filePath = fullPackagePath + File.separator + fileName + ".java";

        String content = "package " + packagePath + ";\n\n"
                + "import org.springframework.data.jpa.repository.JpaRepository;\n"
                + "import org.springframework.stereotype.Repository;\n\n"
                + "@Repository\n"
                + "public interface " + fileName + " extends JpaRepository<" + className + "DTO, Long> {\n"
                + "}\n";

        ClassWriter.writeFile(filePath, content);
    }

    public static void generateDTO(ModuleProperties moduleProperties) {
        Path basePath = Path.of(System.getProperty("user.dir"), "src", "main", "java");
        String packagePath = moduleProperties.getGroupId()
                + "." + moduleProperties.getModuleName()
                + ".dto";
        String fullPackagePath = basePath.resolve(packagePath.replace(".", File.separator)).toString();

        String className = capitalizeFirstLetter(moduleProperties.getModuleName()) + "DTO";
        String filePath = fullPackagePath + File.separator + className + ".java";

        StringBuilder content = new StringBuilder();
        content.append("package ").append(packagePath).append(";\n\n");

        if (moduleProperties.isHasLombok()) {
            content.append("import lombok.AllArgsConstructor;\n")
                    .append("import lombok.Builder;\n")
                    .append("import lombok.Data;\n")
                    .append("import lombok.NoArgsConstructor;\n\n");
            content.append("@Data\n")
                    .append("@Builder\n")
                    .append("@NoArgsConstructor\n")
                    .append("@AllArgsConstructor\n");
        }

        content.append("public class ").append(className).append(" {\n\n")
                .append("    private Long id;\n")
                .append("    private String name;\n")
                .append("    private String description;\n")
                .append("    private Long number;\n");

        if (!moduleProperties.isHasLombok()) {
            content.append("    public Long getId() {\n")
                    .append("        return id;\n")
                    .append("    }\n\n")
                    .append("    public void setId(Long id) {\n")
                    .append("        this.id = id;\n")
                    .append("    }\n\n")
                    .append("    public String getName() {\n")
                    .append("        return name;\n")
                    .append("    }\n\n")
                    .append("    public void setName(String name) {\n")
                    .append("        this.name = name;\n")
                    .append("    }\n\n")
                    .append("    public String getDescription() {\n")
                    .append("        return description;\n")
                    .append("    }\n\n")
                    .append("    public void setDescription(String description) {\n")
                    .append("        this.description = description;\n")
                    .append("    }\n\n")
                    .append("    public Long getNumber() {\n")
                    .append("        return number;\n")
                    .append("    }\n\n")
                    .append("    public void setNumber(Long number) {\n")
                    .append("        this.number = number;\n")
                    .append("    }\n");
        }

        content.append("}\n");

        ClassWriter.writeFile(filePath, content.toString());
    }


    private static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
