package pl.iseebugs.domain.structuregenerator;

import pl.iseebugs.domain.structuregenerator.dto.StructureDTO;

interface StructureGeneratorPort {
    void generateStructure(StructureDTO structureDTO);
}
