package uoc.ds.pr.model;

import uoc.ds.pr.UniversityEvents;

public class Rating {



    private String attendeeId;
    private String eventId;
    private UniversityEvents.Rating rating;
    private String message;



    public Rating(String attendeeId, String eventId, UniversityEvents.Rating rating, String message) {
        this.attendeeId = attendeeId;
        this.eventId = eventId;
        this.rating = rating;
        this.message = message;
    }

    public String getEventId() {
        return eventId;
    }

    public Rating getAttendee() {
        return this;
    }

    public String getId(){
        return attendeeId;
    }

    public UniversityEvents.Rating getRating() {
        return rating;
    }
}