package uoc.ds.pr.model;

import uoc.ds.pr.UniversityEvents;

public class Professor extends Entity {
    public Professor(String id, String name, String description) { super(id, name, description, UniversityEvents.EntityType.PROFESSOR); }


}
