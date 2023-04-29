package uoc.ds.pr.model;

public class AttendeeEvent {
    private String attendeeId;
    private String eventId;
    private String typeAttendee;

    public AttendeeEvent(String attendeeId, String eventId, String typeAttendee) {
        this.attendeeId = attendeeId;
        this.eventId = eventId;
        this.typeAttendee = typeAttendee;
    }

    public String getEventId() {
        return eventId;
    }

    public String getAttendeeId() {
        return attendeeId;
    }

    public String getTypeAttendee() { return typeAttendee; }




    /*
    public String toString(){
        return "--> eventId: "+getEventId()+" --> attendeeId: "+getAttendeeId()+" --> typeAttendee: "+getTypeAttendee();
    }
    */

}
