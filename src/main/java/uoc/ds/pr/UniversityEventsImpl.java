package uoc.ds.pr;


import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.adt.sequential.QueueArrayImpl;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.model.*;
import uoc.ds.pr.exceptions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

public class UniversityEventsImpl implements UniversityEvents {

    private ArrayList<Entity> entities ;
    private ArrayList<Attendee> attendees ;
    private ArrayList<Event> events;
    private QueueArrayImpl<EventRequest> requests;
    private LinkedList<EventRequest> rejectedRequests;

    public UniversityEventsImpl() {
        entities= new ArrayList<Entity>();
        attendees= new ArrayList<Attendee>();
        events= new ArrayList<Event>();
        requests= new QueueArrayImpl<EventRequest>();
        rejectedRequests = new LinkedList<EventRequest>();
    }

    @Override
    public void addEntity(String id, String name, String description, EntityType entityType) {
        // Mirar las condiciones para añadir una entidad (NO SE REPITA ID,....)
        entities.add(new Entity(id, name, description, entityType) );

    }

    @Override
    public void addAttendee(String id, String name, String surname, LocalDate dateOfBirth) {
        // Mirar las condiciones para añadir UN ASISTENTE (NO SE REPITA ID,....)
        attendees.add(new Attendee(id, name, surname, dateOfBirth) );

    }

    @Override
    public void addEventRequest(String id, String eventId, String entityId, String description, InstallationType installationType, byte resources, int max, LocalDate startDate, LocalDate endDate, boolean allowRegister) throws EntityNotFoundException {
        // Mirar si hay condiciones para añadir....
        requests.add(new EventRequest(id, eventId, entityId, description, installationType, resources, max, startDate, endDate, allowRegister));
    }

    @Override
    public EventRequest updateEventRequest(Status status, LocalDate date, String message) throws NoEventRequestException {

        // If there are no requests in the queue, throw an exception.
        if (requests.isEmpty()) throw new NoEventRequestException("There are no pending requests.");

        // Retrieve the element at the head of the queue
        EventRequest eventRequest = requests.poll();

        if (status.equals(Status.ENABLED)){
            // Añadir al Array de Eventos
            events.add((Event)eventRequest);
        }else{
            // Añadir  a la Lista enlazada de Solicitudes rechazadas
            rejectedRequests.add(eventRequest);
        }

        return eventRequest;
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
        return entities.size();
    }

    @Override
    public int numAttendees() {
        return attendees.size();
    }

    @Override
    public int numRequests() {
        return requests.size();
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
