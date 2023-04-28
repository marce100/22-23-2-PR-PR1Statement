package uoc.ds.pr.model;

import uoc.ds.pr.UniversityEvents;

public class Entity {
    private String id;
    private String name;
    private String description;
    private UniversityEvents.EntityType entityType;

    public Entity(String id, String name, String description, UniversityEvents.EntityType entityType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.entityType = entityType;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UniversityEvents.EntityType getEntityType() {
        return entityType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEntityType(UniversityEvents.EntityType entityType) {
        this.entityType = entityType;
    }
}
