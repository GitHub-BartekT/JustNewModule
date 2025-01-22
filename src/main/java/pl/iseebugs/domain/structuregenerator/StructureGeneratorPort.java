package pl.iseebugs.domain.structuregenerator;

import pl.iseebugs.domain.structuregenerator.dto.ModuleStructure;
import pl.iseebugs.domain.structuregenerator.dto.ModuleProperties;

interface StructureGeneratorPort {
    ModuleStructure generateStructure(ModuleProperties moduleProperties);
}
