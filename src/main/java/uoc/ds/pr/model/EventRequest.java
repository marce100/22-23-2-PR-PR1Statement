package uoc.ds.pr.model;

import uoc.ds.pr.UniversityEvents;

import java.time.LocalDate;

public class EventRequest {
    private String id;
    private String eventId;
    private String entityId;
    private String description;
    private UniversityEvents.InstallationType installationType;
    private byte resources;
    private int max;
    private LocalDate startDate;
    private java.time.LocalDate endDate;
    private boolean allowRegister;

    public EventRequest(String id, String eventId, String entityId, String description, UniversityEvents.InstallationType installationType, byte resources, int max, LocalDate startDate, LocalDate endDate, boolean allowRegister) {
        this.id = id;
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

    public String getRequestId() {
        return id;
    }

    public String getId() {
        return id;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getDescription() {
        return description;
    }

    public UniversityEvents.InstallationType getInstallationType() {
        return installationType;
    }

    public byte getResources() {
        return resources;
    }

    public int getMax() {
        return max;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isAllowRegister() {
        return allowRegister;
    }
}
