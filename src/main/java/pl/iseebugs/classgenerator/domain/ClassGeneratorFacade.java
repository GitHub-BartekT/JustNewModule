package pl.iseebugs.classgenerator.domain;

import pl.iseebugs.structuregenerator.dto.ModuleProperties;

import java.io.File;
import java.nio.file.Path;

public class ClassGeneratorFacade {


    public static void generatePort(ModuleProperties moduleProperties) {
        Path basePath = Path.of(System.getProperty("user.dir"), "src", "main", "java");
        String packagePath = moduleProperties.getGroupId()
                + "." + moduleProperties.getModuleName()
                + "." + moduleProperties.getLogicPackage();

        String fullPackagePath = basePath.resolve(packagePath.replace(".", File.separator)).toString();
        String fileName = capitalizeFirstLetter(moduleProperties.getModuleName() + "Port");
        String filePath = fullPackagePath + File.separator + fileName + ".java";

        String moduleName = capitalizeFirstLetter(moduleProperties.getModuleName().toLowerCase());
        String moduleDTO = moduleName + "DTO";
        String modulePath = moduleProperties.getGroupId() + "." +
                moduleProperties.getModuleName();
        String logicPath = modulePath + "." +
                moduleProperties.getLogicPackage();
        String dtoPath = modulePath + ".dto";

        String content = "package " + logicPath + ";\n\n"
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

    public static void generateService(ModuleProperties moduleProperties) {
        Path basePath = Path.of(System.getProperty("user.dir"), "src", "main", "java");
        String modulePath = moduleProperties.getGroupId()
                + "." + moduleProperties.getModuleName();

        String logicPath = modulePath
                + "." + moduleProperties.getLogicPackage();
        String dtoPath = modulePath + ".dto";

        String fullPackagePath = basePath.resolve(logicPath.replace(".", File.separator)).toString();
        String className = capitalizeFirstLetter(moduleProperties.getModuleName()) + "Service";
        String interfaceName = capitalizeFirstLetter(moduleProperties.getModuleName()) + "Port";
        String entityName = capitalizeFirstLetter(moduleProperties.getModuleName());
        String repositoryName = moduleProperties.getModuleName().toLowerCase() + "Repository";
        String dtoName = entityName + "DTO";
        String filePath = fullPackagePath + File.separator + className + ".java";

        StringBuilder content = new StringBuilder();
        content.append("package ").append(logicPath).append(";\n\n")
                .append("import org.springframework.stereotype.Service;\n")
                .append("import ").append(dtoPath).append(".").append(dtoName).append(";\n")
                .append("import ").append(logicPath).append(".").append(capitalizeFirstLetter(repositoryName)).append(";\n")
                .append("import ").append(logicPath).append(".").append(interfaceName).append(";\n")
                .append("import ").append(logicPath).append(".").append(entityName).append(";\n")
                .append("import java.util.List;\n\n");

        if (moduleProperties.isHasLombok()) {
            content.append("import lombok.RequiredArgsConstructor;\n\n")
                    .append("@Service\n")
                    .append("@RequiredArgsConstructor\n");
        } else {
            content.append("@Service\n");
        }

        content.append("public class ").append(className).append(" implements ").append(interfaceName).append(" {\n\n");

        content.append("    private final ").append(capitalizeFirstLetter(repositoryName)).append(" ").append(moduleProperties.getModuleName().toLowerCase()).append("Repository;\n\n");

        if (!moduleProperties.isHasLombok()) {
            content.append("    public ").append(className).append("(").append(capitalizeFirstLetter(repositoryName)).append(" ").append(moduleProperties.getModuleName().toLowerCase()).append("Repository) {\n")
                    .append("        this.").append(moduleProperties.getModuleName().toLowerCase()).append("Repository = ").append(moduleProperties.getModuleName().toLowerCase()).append("Repository;\n")
                    .append("    }\n\n");
        }

        content.append("    @Override\n")
                .append("    public void create").append(entityName).append("(").append(dtoName).append(" ").append(moduleProperties.getModuleName().toLowerCase()).append(") {\n")
                .append("        ").append(entityName).append(" entity = new ").append(entityName).append("();\n")
                .append("        entity.setName(").append(moduleProperties.getModuleName().toLowerCase()).append(".getName());\n")
                .append("        entity.setDescription(").append(moduleProperties.getModuleName().toLowerCase()).append(".getDescription());\n")
                .append("        entity.setNumber(").append(moduleProperties.getModuleName().toLowerCase()).append(".getNumber());\n")
                .append("        ").append(moduleProperties.getModuleName().toLowerCase()).append("Repository.save(entity);\n")
                .append("    }\n\n");

        content.append("    @Override\n")
                .append("    public ").append(dtoName).append(" findById(Long id) {\n")
                .append("        return ").append(moduleProperties.getModuleName().toLowerCase()).append("Repository.findById(id)\n")
                .append("                .map(entity -> {\n")
                .append("                    ").append(dtoName).append(" dto = new ").append(dtoName).append("();\n")
                .append("                    dto.setId(entity.getId());\n")
                .append("                    dto.setName(entity.getName());\n")
                .append("                    dto.setDescription(entity.getDescription());\n")
                .append("                    dto.setNumber(entity.getNumber());\n")
                .append("                    return dto;\n")
                .append("                })\n")
                .append("                .orElseThrow(() -> new RuntimeException(\"").append(entityName).append(" not found\"));\n")
                .append("    }\n\n");

        content.append("    @Override\n")
                .append("    public List<").append(dtoName).append("> findAll() {\n")
                .append("        return ").append(moduleProperties.getModuleName().toLowerCase()).append("Repository.findAll()\n")
                .append("                .stream()\n")
                .append("                .map(entity -> {\n")
                .append("                    ").append(dtoName).append(" dto = new ").append(dtoName).append("();\n")
                .append("                    dto.setId(entity.getId());\n")
                .append("                    dto.setName(entity.getName());\n")
                .append("                    dto.setDescription(entity.getDescription());\n")
                .append("                    dto.setNumber(entity.getNumber());\n")
                .append("                    return dto;\n")
                .append("                })\n")
                .append("                .toList();\n")
                .append("    }\n\n");

        content.append("    @Override\n")
                .append("    public void update").append(entityName).append("(Long id, ").append(dtoName).append(" ").append(moduleProperties.getModuleName().toLowerCase()).append(") {\n")
                .append("        ").append(entityName).append(" entity = ").append(moduleProperties.getModuleName().toLowerCase()).append("Repository.findById(id)\n")
                .append("                .orElseThrow(() -> new RuntimeException(\"").append(entityName).append(" not found\"));\n")
                .append("        entity.setName(").append(moduleProperties.getModuleName().toLowerCase()).append(".getName());\n")
                .append("        entity.setDescription(").append(moduleProperties.getModuleName().toLowerCase()).append(".getDescription());\n")
                .append("        entity.setNumber(").append(moduleProperties.getModuleName().toLowerCase()).append(".getNumber());\n")
                .append("        ").append(moduleProperties.getModuleName().toLowerCase()).append("Repository.save(entity);\n")
                .append("    }\n\n");

        content.append("    @Override\n")
                .append("    public void delete").append(entityName).append("(Long id) {\n")
                .append("        if (!").append(moduleProperties.getModuleName().toLowerCase()).append("Repository.existsById(id)) {\n")
                .append("            throw new RuntimeException(\"").append(entityName).append(" not found\");\n")
                .append("        }\n")
                .append("        ").append(moduleProperties.getModuleName().toLowerCase()).append("Repository.deleteById(id);\n")
                .append("    }\n");

        content.append("}\n");

        ClassWriter.writeFile(filePath, content.toString());
    }

    public static void generateGameEntity(ModuleProperties moduleProperties) {
        Path basePath = Path.of(System.getProperty("user.dir"), "src", "main", "java");
        String packagePath = moduleProperties.getGroupId()
                + "." + moduleProperties.getModuleName()
                + ".domain";
        String fullPackagePath = basePath.resolve(packagePath.replace(".", File.separator)).toString();

        String className = "Game";
        String filePath = fullPackagePath + File.separator + className + ".java";

        StringBuilder content = new StringBuilder();
        content.append("package ").append(packagePath).append(";\n\n")
                .append("import jakarta.persistence.*;\n")
                .append("import java.util.Objects;\n");

        if (moduleProperties.isHasLombok()) {
            content.append("import lombok.*;\n");
        }

        content.append("\n@Entity\n")
                .append("@Table(name = \"games\")\n");

        if (moduleProperties.isHasLombok()) {
            content.append("@Getter\n")
                    .append("@Setter\n")
                    .append("@Builder\n")
                    .append("@NoArgsConstructor\n")
                    .append("@AllArgsConstructor\n");
        }

        content.append("public class ").append(className).append(" {\n\n")
                .append("    @Id\n")
                .append("    @GeneratedValue(strategy = GenerationType.IDENTITY)\n")
                .append("    private Long id;\n\n")
                .append("    private String name;\n\n")
                .append("    private String description;\n\n")
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
                    .append("    public Integer getNumber() {\n")
                    .append("        return number;\n")
                    .append("    }\n\n")
                    .append("    public void setNumber(Integer number) {\n")
                    .append("        this.number = number;\n")
                    .append("    }\n");
        }

        content.append("\n    @Override\n")
                .append("    public boolean equals(Object o) {\n")
                .append("        if (this == o) return true;\n")
                .append("        if (o == null || getClass() != o.getClass()) return false;\n")
                .append("        ").append(className).append(" game = (").append(className).append(") o;\n")
                .append("        return Objects.equals(id, game.id) && Objects.equals(name, game.name);\n")
                .append("    }\n\n")
                .append("    @Override\n")
                .append("    public int hashCode() {\n")
                .append("        return Objects.hash(id, name);\n")
                .append("    }\n");

        content.append("}\n");

        ClassWriter.writeFile(filePath, content.toString());
    }

    public static void generateGameMapper(ModuleProperties moduleProperties) {
        Path basePath = Path.of(System.getProperty("user.dir"), "src", "main", "java");
        String packagePath = moduleProperties.getGroupId()
                + "." + moduleProperties.getModuleName()
                + "." + moduleProperties.getLogicPackage();

        String fullPackagePath = basePath.resolve(packagePath.replace(".", File.separator)).toString();

        String className = "GameMapper";
        String entityName = "Game";
        String dtoName = "GameDTO";
        String domainPath = moduleProperties.getGroupId()
                + "." + moduleProperties.getModuleName()
                + ".domain";
        String dtoPath = moduleProperties.getGroupId()
                + "." + moduleProperties.getModuleName()
                 + ".dto";
        String filePath = fullPackagePath + File.separator + className + ".java";

        String content = "package " + packagePath + ";\n\n"
                + "import " + domainPath + "." + entityName + ";\n"
                + "import " + dtoPath + "." + dtoName + ";\n\n"
                + "public class " + className + " {\n\n"
                + "    public static " + dtoName + " toDTO(" + entityName + " entity) {\n"
                + "        if (entity == null) {\n"
                + "            return null;\n"
                + "        }\n"
                + "        " + dtoName + " dto = new " + dtoName + "();\n"
                + "        dto.setId(entity.getId());\n"
                + "        dto.setName(entity.getName());\n"
                + "        dto.setDescription(entity.getDescription());\n"
                + "        dto.setNumber(entity.getNumber().longValue());\n"
                + "        return dto;\n"
                + "    }\n\n"
                + "    public static " + entityName + " toEntity(" + dtoName + " dto) {\n"
                + "        if (dto == null) {\n"
                + "            return null;\n"
                + "        }\n"
                + "        " + entityName + " entity = new " + entityName + "();\n"
                + "        entity.setName(dto.getName());\n"
                + "        entity.setDescription(dto.getDescription());\n"
                + "        entity.setNumber(dto.getNumber());\n"
                + "        return entity;\n"
                + "    }\n"
                + "}";

        ClassWriter.writeFile(filePath, content);
    }

    public static void generateException(ModuleProperties moduleProperties) {
        Path basePath = Path.of(System.getProperty("user.dir"), "src", "main", "java");
        String packagePath = moduleProperties.getGroupId()
                + "." + moduleProperties.getModuleName()
                + "." + moduleProperties.getLogicPackage();
        String fullPackagePath = basePath.resolve(packagePath.replace(".", File.separator)).toString();

        String fileName = capitalizeFirstLetter(moduleProperties.getModuleName() + "NotFoundException");
        String filePath = fullPackagePath + File.separator + fileName + ".java";

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
                + "." + moduleProperties.getModuleName();

        String logicPath = packagePath
                + "." + moduleProperties.getLogicPackage();
        String dtoPath = packagePath + ".dto";


        String fullPackagePath = basePath.resolve(logicPath.replace(".", File.separator)).toString();

        String className = capitalizeFirstLetter(moduleProperties.getModuleName());
        String fileName = className + "Repository";
        String filePath = fullPackagePath + File.separator + fileName + ".java";
        String moduleName = capitalizeFirstLetter(moduleProperties.getModuleName());
        String dtoName = moduleName + "DTO";

        String content = "package " + logicPath + ";\n\n"
                + "import org.springframework.data.jpa.repository.JpaRepository;\n"
                + "import org.springframework.stereotype.Repository;\n\n"
                + "import " + dtoPath + "." + dtoName + ";\n\n"
                + "@Repository\n"
                + "public interface " + fileName + " extends JpaRepository<" + className + ", Long> {\n"
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

    public static void generateRestController(ModuleProperties moduleProperties) {
        Path basePath = Path.of(System.getProperty("user.dir"), "src", "main", "java");
        String packagePath = moduleProperties.getGroupId()
                + "." + moduleProperties.getModuleName();

        String logicPath = packagePath
                + "." + moduleProperties.getLogicPackage();
        String dtoPath = packagePath + ".dto";

        String fullPackagePath = basePath.resolve(packagePath.replace(".", File.separator)).toString();
        String className = capitalizeFirstLetter(moduleProperties.getModuleName()) + "Controller";
        String serviceName = moduleProperties.getModuleName().toLowerCase() + "Service";
        String entityName = capitalizeFirstLetter(moduleProperties.getModuleName());
        String dtoName = entityName + "DTO";
        String filePath = fullPackagePath + File.separator + className + ".java";

        String content = "package " + packagePath + ";\n\n"
                + "import org.springframework.http.ResponseEntity;\n"
                + "import org.springframework.web.bind.annotation.*;\n"
                + "import org.springframework.beans.factory.annotation.Autowired;\n"
                + "import " + dtoPath + "." + dtoName + ";\n"
                + "import " + logicPath + "." + capitalizeFirstLetter(moduleProperties.getModuleName()) + "Service;\n"
                + "import java.util.List;\n\n"
                + "@RestController\n"
                + "@RequestMapping(\"/" + moduleProperties.getModuleName().toLowerCase() + "s\")\n"
                + "public class " + className + " {\n\n"
                + "    private final " + capitalizeFirstLetter(moduleProperties.getModuleName()) + "Service " + serviceName + ";\n\n"
                + "    @Autowired\n"
                + "    public " + className + "(" + capitalizeFirstLetter(moduleProperties.getModuleName()) + "Service " + serviceName + ") {\n"
                + "        this." + serviceName + " = " + serviceName + ";\n"
                + "    }\n\n"

                + "    @GetMapping\n"
                + "    public ResponseEntity<List<" + dtoName + ">> getAll() {\n"
                + "        List<" + dtoName + "> dtoList = " + serviceName + ".findAll();\n"
                + "        return ResponseEntity.ok(dtoList);\n"
                + "    }\n\n"

                + "    @GetMapping(\"/{id}\")\n"
                + "    public ResponseEntity<" + dtoName + "> getById(@PathVariable Long id) {\n"
                + "        " + dtoName + " dto = " + serviceName + ".findById(id);\n"
                + "        return ResponseEntity.ok(dto);\n"
                + "    }\n\n"

                + "    @PostMapping\n"
                + "    public ResponseEntity<Void> create(@RequestBody " + dtoName + " " + moduleProperties.getModuleName().toLowerCase() + ") {\n"
                + "        " + serviceName + ".create" + entityName + "(" + moduleProperties.getModuleName().toLowerCase() + ");\n"
                + "        return ResponseEntity.ok().build();\n"
                + "    }\n\n"

                + "    @PutMapping(\"/{id}\")\n"
                + "    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody " + dtoName + " " + moduleProperties.getModuleName().toLowerCase() + ") {\n"
                + "        " + serviceName + ".update" + entityName + "(id, " + moduleProperties.getModuleName().toLowerCase() + ");\n"
                + "        return ResponseEntity.ok().build();\n"
                + "    }\n\n"

                + "    @DeleteMapping(\"/{id}\")\n"
                + "    public ResponseEntity<Void> delete(@PathVariable Long id) {\n"
                + "        " + serviceName + ".delete" + entityName + "(id);\n"
                + "        return ResponseEntity.noContent().build();\n"
                + "    }\n"
                + "}";

        ClassWriter.writeFile(filePath, content);
    }


    private static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
