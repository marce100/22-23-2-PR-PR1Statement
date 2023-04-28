package uoc.ds.pr.model;


import uoc.ds.pr.UniversityEvents;

import java.time.LocalDate;

public class Event extends EventRequest{



    private float rating;

    public Event(String id, String eventId, String entityId, String description, UniversityEvents.InstallationType installationType, byte resources, int max, LocalDate startDate, LocalDate endDate, boolean allowRegister, float rating) {
        super(id, eventId, entityId, description, installationType, resources, max, startDate, endDate, allowRegister);

        this.rating = rating;
    }

    public float rating() {
        return rating;
    }

    public String getEventId(){
        return super.getRequestId();
    }


}
