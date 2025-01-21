package pl.iseebugs.domain.structuregenerator.dto;

public class StructureDTO {
    private String groupId;
    private String artifactId;
    private String moduleName;
    private boolean hasSpringBoot;
    private boolean hasLombok;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(final String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(final String artifactId) {
        this.artifactId = artifactId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(final String moduleName) {
        this.moduleName = moduleName;
    }

    public boolean isHasSpringBoot() {
        return hasSpringBoot;
    }

    public void setHasSpringBoot(final boolean hasSpringBoot) {
        this.hasSpringBoot = hasSpringBoot;
    }

    public boolean isHasLombok() {
        return hasLombok;
    }

    public void setHasLombok(final boolean hasLombok) {
        this.hasLombok = hasLombok;
    }
}
