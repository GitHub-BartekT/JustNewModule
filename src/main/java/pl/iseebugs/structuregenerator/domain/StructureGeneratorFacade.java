package pl.iseebugs.structuregenerator.domain;

import pl.iseebugs.structuregenerator.dto.ModuleStructure;
import pl.iseebugs.structuregenerator.dto.ModuleProperties;

import java.nio.file.Paths;

public class StructureGeneratorFacade implements StructureGeneratorPort{

    private static final ModuleStructure root = new ModuleStructure("src");

    @Override
    public ModuleStructure createScheme(final ModuleProperties moduleProperties) {
        ModuleStructure main = new ModuleStructure("main");
        ModuleStructure java = new ModuleStructure("java");

        String[] packageStructure = moduleProperties.getGroupId().split("\\.");
        ModuleStructure currentGroupIdNode = java;

        for (String part : packageStructure) {
            ModuleStructure groupIdPart = new ModuleStructure(part);
            currentGroupIdNode.addChild(groupIdPart);
            currentGroupIdNode = groupIdPart;
        }


        if (moduleProperties.getArtifactId() != null) {
            ModuleStructure artifact = new ModuleStructure(moduleProperties.getArtifactId());
            currentGroupIdNode.addChild(artifact);
            currentGroupIdNode = artifact;
        }

        ModuleStructure infrastructure = new ModuleStructure(moduleProperties.getInfrastructurePackage());
        ModuleStructure module = new ModuleStructure(moduleProperties.getModuleName());
        ModuleStructure logic = new ModuleStructure(moduleProperties.getLogicPackage());
        ModuleStructure dto = new ModuleStructure("dto");

        logic.addChild(dto);

        module.addChild(logic);
        module.addChild(dto);

        currentGroupIdNode.addChild(module);
        currentGroupIdNode.addChild(infrastructure);

        root.addChild(main);
        main.addChild(java);

        return root;
    }

    @Override
    public void generateStructure(){
        DirectoryCreator.createDirectories(root, Paths.get(System.getProperty("user.dir")));
    };

}
