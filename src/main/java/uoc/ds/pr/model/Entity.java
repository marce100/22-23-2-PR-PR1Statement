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

    public Entity(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
