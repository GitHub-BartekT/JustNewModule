package pl.iseebugs.domain.structuregenerator;

import pl.iseebugs.domain.structuregenerator.dto.ModuleStructure;
import pl.iseebugs.domain.structuregenerator.dto.ModuleProperties;

public class StructureGeneratorFacade implements StructureGeneratorPort{
    @Override
    public ModuleStructure generateStructure(final ModuleProperties moduleProperties) {
        ModuleStructure root = new ModuleStructure("src");
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

    private void createScheme(){

    };

}
