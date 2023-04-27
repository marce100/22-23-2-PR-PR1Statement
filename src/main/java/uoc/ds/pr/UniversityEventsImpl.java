package uoc.ds.pr;

import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.model.Event;

import java.time.LocalDate;

public class UniversityEventsImpl implements UniversityEvents {
    @Override
    public void addEntity(String id, String name, String description, EntityType entityType) {

    }

    @Override
    public void addAttendee(String id, String name, String surname, LocalDate dateOfBirth) {

    }

    @Override
    public void addEventRequest(String id, String eventId, String entityId, String description, InstallationType installationType, byte resources, int max, LocalDate startDate, LocalDate endDate, boolean allowRegister) throws EntityNotFoundException {
        EventRequest eventRequest= new EventRequest(id, eventId, entityId, description, installationType, resources, max, startDate, endDate, allowRegister);
    }

    @Override
    public EventRequest updateEventRequest(Status status, LocalDate date, String message) throws NoEventRequestException {
        return null;
    }

    @Override
    public void signUpEvent(String attendeeId, String eventId) throws AttendeeNotFoundException, EventNotFoundException, NotAllowedException, AttendeeAlreadyInEventException {

    }

    @Override
    public double getPercentageRejectedRequests() {
        return 0;
    }

    @Override
    public Iterator<EventRequest> getRejectedRequests() throws NoEventRequestException {
        return null;
    }

    @Override
    public Iterator<Event> getEventsByEntity(String entityId) throws NoEventsException {
        return null;
    }

    @Override
    public Iterator<Event> getAllEvents() throws NoEventsException {
        return null;
    }

    @Override
    public Iterator<Event> getEventsByAttendee(String attendeeId) throws NoEventsException {
        return null;
    }

    @Override
    public void addRating(String attendeeId, String eventId, Rating rating, String message) throws AttendeeNotFoundException, EventNotFoundException, AttendeeNotInEventException {

    }

    @Override
    public Iterator<uoc.ds.pr.model.Rating> getRatingsByEvent(String eventId) throws EventNotFoundException, NoRatingsException {
        return null;
    }

    @Override
    public Attendee mostActiveAttendee() throws AttendeeNotFoundException {
        return null;
    }

    @Override
    public Event bestEvent() throws EventNotFoundException {
        return null;
    }

    @Override
    public int numEntities() {
        return 0;
    }

    @Override
    public int numAttendees() {
        return 0;
    }

    @Override
    public int numRequests() {
        return 0;
    }

    @Override
    public int numEvents() {
        return 0;
    }

    @Override
    public int numEventsByAttendee(String attendeeId) {
        return 0;
    }

    @Override
    public int numAttendeesByEvent(String eventId) {
        return 0;
    }

    @Override
    public int numSubstitutesByEvent(String eventId) {
        return 0;
    }

    @Override
    public int numEventsByEntity(String entityId) {
        return 0;
    }

    @Override
    public int numRejectedRequests() {
        return 0;
    }

    @Override
    public int numRatingsByEvent(String eventId) {
        return 0;
    }

    @Override
    public Entity getEntity(String entityId) {
        return null;
    }

    @Override
    public Attendee getAttendee(String attendeeId) {
        return null;
    }

    @Override
    public Event getEvent(String eventId) {
        return null;
    }
}
