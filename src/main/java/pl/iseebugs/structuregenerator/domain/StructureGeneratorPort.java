package pl.iseebugs.structuregenerator.domain;

import pl.iseebugs.structuregenerator.dto.ModuleStructure;
import pl.iseebugs.structuregenerator.dto.ModuleProperties;

interface StructureGeneratorPort {
    ModuleStructure createScheme(ModuleProperties moduleProperties);
    void generateStructure();
}
