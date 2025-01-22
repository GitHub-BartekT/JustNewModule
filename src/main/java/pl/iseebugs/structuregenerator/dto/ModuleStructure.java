package pl.iseebugs.structuregenerator.dto;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ModuleStructure {
    private String name;
    private Path path;
    private List<ModuleStructure> children;

    public ModuleStructure(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<ModuleStructure> getChildren() {
        return children;
    }

    public void addChild(ModuleStructure child) {
        children.add(child);
    }

    Path getPath() {
        return path;
    }

    void setPath(final Path path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return toString(0);
    }

    private String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("  ".repeat(depth)).append(name).append("\n");
        for (ModuleStructure child : children) {
            sb.append(child.toString(depth + 1));
        }
        return sb.toString();
    }
}
