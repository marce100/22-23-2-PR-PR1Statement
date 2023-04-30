package uoc.ds.pr.model;


import uoc.ds.pr.UniversityEvents;

import java.time.LocalDate;

public class Event {



    private float rating;


    private String eventId;
    private String entityId;
    private String description;
    private UniversityEvents.InstallationType installationType;
    private byte resources;
    private int max;
    private LocalDate startDate;
    private java.time.LocalDate endDate;
    private boolean allowRegister;

    public Event(String eventId, String entityId, String description, UniversityEvents.InstallationType installationType, byte resources, int max, LocalDate startDate, LocalDate endDate, boolean allowRegister) {

        this.eventId = eventId;
        this.entityId = entityId;
        this.description = description;
        this.installationType = installationType;
        this.resources = resources;
        this.max = max;
        this.startDate = startDate;
        this.endDate = endDate;
        this.allowRegister = allowRegister;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEntityId() { return entityId; }

    public boolean isAllowRegister() {
        return allowRegister;
    }

    public float rating() {
        return rating;
    }


    public void setRating(float rating) {
        this.rating = rating;
    }
}
