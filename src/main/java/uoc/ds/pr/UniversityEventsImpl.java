package uoc.ds.pr;


import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.adt.sequential.QueueArrayImpl;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.model.*;
import uoc.ds.pr.exceptions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import edu.uoc.ds.adt.sequential.LinkedList;

public class UniversityEventsImpl implements UniversityEvents {

    private ArrayList<Attendee> attendees ;                 // Attendees: Java Array: Attendee [].
    //private LinkedList<Event> entityEvents;                 // Events organized by an entity: Linked List: LinkedList.
    private QueueArrayImpl<EventRequest> requests;          // Requests : Queue: QueueArrayImpl.
    private LinkedList<EventRequest> rejectedRequests;      // Rejected Requests: Linked List: LinkedList.
    private ArrayList<Entity> entities ;                    // Entities: Java Array: Entities [].
    private ArrayList<Event> events;                        // Events: Java Array: Events [].
    //private LinkedList<Event> attendeeEvents;               // Events an attendee goes to: Linked List: LinkedList.
    //private LinkedList<Rating> eventEvaluations;            // Evaluations of an event: Linked List: LinkedList.
    //private QueueArrayImpl<Attendee> eventRegistrations;    // Attendees pointed to an event : Queue: QueueArrayImpl.
    //private int totalRequests;                              // Total Requests : Integer: Integer.
    //private int totalRejectedRequests;                      // Total Rejected Requests : Integer: Integer.
    //private Attendee mostActiveAttendee;                    // Most Active Attendee: Pointer to Attendee.
    //private OrdererVector highestRatedEvent;                // Highest Rated Event: Ordered Vector: OrderedVector.


    public UniversityEventsImpl() {
        attendees= new ArrayList<Attendee>();
        //entityEvents= new LinkedList<Event>();
        requests= new QueueArrayImpl<EventRequest>();
        rejectedRequests= new LinkedList<EventRequest>();
        entities= new ArrayList<Entity>();
        events= new ArrayList<Event>();
        //attendeeEvents= new LinkedList<Event>();
        //eventEvaluations= new LinkedList<Rating>();
        //eventRegistrations= new QueueArrayImpl<Attendee>();
        //totalRequests= 0;
        //totalRejectedRequests= 0;
        //mostActiveAttendee= null;
        //highestRatedEvent;
    }

    @Override
    public void addEntity(String id, String name, String description, EntityType entityType) {
        Entity found= null;
        for(Entity entity : entities)
            if(entity.getId().equals(id))
                found= entity;

        if (found==null) {
            entities.add(new Entity(id, name, description, entityType));
        }else {
            found.setName(name);
            found.setDescription(description);
            found.setEntityType(entityType);
        }
    }

    @Override
    public void addAttendee(String id, String name, String surname, LocalDate dateOfBirth) {
        Attendee found= null;
        for(Attendee attendee : attendees)
            if(attendee.getId().equals(id))
                found= attendee;

        if (found==null) {
            attendees.add(new Attendee(id, name, surname, dateOfBirth));
        }else {
            found.setName(name);
            found.setSurname(surname);
            found.setDateOfBirth(dateOfBirth);
        }
    }

    @Override
    public void addEventRequest(String id, String eventId, String entityId, String description, InstallationType installationType, byte resources, int max, LocalDate startDate, LocalDate endDate, boolean allowRegister) throws EntityNotFoundException {
        boolean found= false;
        for(Entity entity : entities)
            if(entity.getId().equals(id))
                found= true;

        if (!found) {
            throw new EntityNotFoundException("The entity does not exist.");
        }else {
            requests.add(new EventRequest(id, eventId, entityId, description, installationType, resources, max, startDate, endDate, allowRegister));
        }
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
            rejectedRequests.insertEnd(eventRequest);
        }

        return eventRequest;
    }

    @Override
    public void signUpEvent(String attendeeId, String eventId) throws AttendeeNotFoundException, EventNotFoundException, NotAllowedException, AttendeeAlreadyInEventException {
        /**
         * @pre true.
         * @post the number of attendees registered for an event will be the same plus one.
         * If the maximum number of registered attendees has been exceeded, they will be added as substitutes.
         *
         * @throws AttendeeNotFoundException If the attendee does not exist, the error will be reported.
         * @throws EventNotFoundException If the event does not exist, the error will be reported.
         * @throws NotAllowedException If the event does not admit attendees, the error will be reported.
         * @throws AttendeeAlreadyInEventException If the attendee is already registered in that event, the error will be reported
         */
    }

    @Override
    public double getPercentageRejectedRequests() {
        return 0;
        /**
         * @pre true.
         * @post
         * @return returns a real number with the percentage of requests that have not been approved.
         */
    }

    @Override
    public Iterator<EventRequest> getRejectedRequests() throws NoEventRequestException {

        // If there are no requests in the queue, throw an exception.
        if (requests.isEmpty()) throw new NoEventRequestException("There are no pending requests.");

        // Returns an iterator to loop through all the rejected requests.
        return requests.values();

    }

    @Override
    public Iterator<Event> getEventsByEntity(String entityId) throws NoEventsException {
        return null;
        /**
         * @pre the entity exists.
         * @post
         * @return returns an iterator to loop through all the events of an entity.
         * @throws NoEventsException If there are no events, the error will be reported.
         */
    }

    @Override
    public Iterator<Event> getAllEvents() throws NoEventsException {
        //return null;

        // If there are no events in the queue, throw an exception.
        if (events.isEmpty()) throw new NoEventsException("There are no events.");

        return (Iterator) events.listIterator();
    }

    @Override
    public Iterator<Event> getEventsByAttendee(String attendeeId) throws NoEventsException {
        return null;
        /**
         * @pre the attendee exists.
         * @post
         *
         * @return returns an iterator for looping through events attended by an attendee.
         * @throws NoEventsException If the attendee has not participated in any event, the error will be reported
         */
    }

    @Override
    public void addRating(String attendeeId, String eventId, Rating rating, String message) throws AttendeeNotFoundException, EventNotFoundException, AttendeeNotInEventException {
        /**
         * @pre true.
         * @post the ratings will be the same plus one.
         * @throws AttendeeNotFoundException If the attendee does not exist, the error will be reported.
         * @throws EventNotFoundException If the event does not exist, the error will be reported.
         * @throws AttendeeNotInEventException If the attendee did not participate in the event, the error will be reported.
         */
    }

    @Override
    public Iterator<uoc.ds.pr.model.Rating> getRatingsByEvent(String eventId) throws EventNotFoundException, NoRatingsException {
        return null;
        /**
         * @pre true.
         * @post
         *
         * @return returns an iterator to loop through the ratings of an event.
         * @throws EventNotFoundException If the event does not exist, the error will be reported.
         * @throws NoRatingsException If there are no ratings, the error will be reported.
         */
    }

    @Override
    public Attendee mostActiveAttendee() throws AttendeeNotFoundException {
        return null;
        /**
         * @pre true.
         * @post
         * If there is a tie, the one that has signed up the most times before is provided.
         * @return returns the attendee who has attended the most events, the most active attendee.
         * @throws AttendeeNotFoundException If none exist, the error will be reported.
         */
    }

    @Override
    public Event bestEvent() throws EventNotFoundException {
        return null;
        /**
         * @pre true.
         * @post
         * @return returns the highest-ranked event.
         * @throws EventNotFoundException If no event exists, the error will be reported.
         */
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
        return events.size();
    }

    @Override
    public int numEventsByAttendee(String attendeeId) {
        for(Attendee attendee : attendees)
            if(attendee.getId().equals(attendeeId))
                return attendee.numEvents();
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
        for(Entity entity : entities)
            if(entity.getId().equals(entityId))
                return entity;
        return null;
    }

    @Override
    public Attendee getAttendee(String attendeeId) {
        for(Attendee attendee : attendees)
            if(attendee.getId().equals(attendeeId))
                return attendee;
        return null;
    }

    @Override
    public Event getEvent(String eventId) {
        for(Event event : events)
            if(event.getEventId().equals(eventId))
                return event;
        return null;
    }
}
