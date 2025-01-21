package pl.iseebugs.infrastructure.pomanalyzer.dto;

import java.util.ArrayList;
import java.util.List;

public class PomData {
    private String groupId;
    private String artifactId;
    private String name;
    private final List<String> dependencies;

    public PomData() {
        this.dependencies = new ArrayList<>();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public void addDependency(String dependency) {
        this.dependencies.add(dependency);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\nGroup ID: ").append(getGroupId()).append("\n");
        sb.append("Artifact ID: ").append(getArtifactId()).append("\n");
        sb.append("Name: ").append(getName()).append("\n");


        sb.append("Dependencies:\n");

        getDependencies().stream()
                .sorted()
                .forEach(dependency -> sb.append("  - ").append(dependency).append("\n"));


        if (getDependencies().stream().anyMatch(dep -> dep.contains("spring-boot"))) {
            sb.append("\nProject uses Spring Boot.\n");
        }
        if (getDependencies().stream().anyMatch(dep -> dep.contains("lombok"))) {
            sb.append("Project uses Lombok.\n");
        }

        return sb.toString();
    }
}