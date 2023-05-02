package uoc.ds.pr.model;

import uoc.ds.pr.UniversityEvents;

public class Student extends Entity {

    public Student(String id, String name, String description) { super(id, name, description, UniversityEvents.EntityType.STUDENT); }
}
