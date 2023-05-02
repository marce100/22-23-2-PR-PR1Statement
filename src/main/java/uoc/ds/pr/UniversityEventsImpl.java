package uoc.ds.pr;

import edu.uoc.ds.adt.sequential.QueueArrayImpl;
import edu.uoc.ds.traversal.*;
import uoc.ds.pr.model.*;
import uoc.ds.pr.exceptions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import edu.uoc.ds.adt.sequential.LinkedList;

/**
 * Author: Marcelino Álvarez García
 * Github: https://github.com/marce100/22-23-2-PR-PR1Statement
 *
 * For the implementation of this class, we use the ADTs specified in the exercise 4 of the PEC1 solution:
 *
 * Attendees: Java Array: Attendee [].
 * Events organized by an entity: Linked List: LinkedList.
 * Requests : Queue: QueueArrayImpl.
 * Rejected Requests: Linked List: LinkedList.
 * Entities: Java Array: Entities [].
 * Events: Java Array: Events [].
 * Events an attendee goes to: Linked List: LinkedList.
 * Evaluations of an event: Linked List: LinkedList.
 * Attendees pointed to an event : Queue: QueueArrayImpl.
 * Total Requests : Integer: Integer.
 * Total Rejected Requests : Integer: Integer.
 * Most Active Attendee: Pointer to Attendee.
 * Highest Rated Event: Ordered Vector: OrderedVector.
 *
 */
public class UniversityEventsImpl implements UniversityEvents {

    private ArrayList<Attendee> attendees ;
    private QueueArrayImpl<EventRequest> requests;
    private LinkedList<EventRequest> rejectedRequests;
    private ArrayList<Entity> entities ;
    private ArrayList<Event> events;
    private LinkedList<AttendeeEvent> attendeesEvents;
    private LinkedList<uoc.ds.pr.model.Rating> ratings;
    private int totalRequests;
    private int totalRejectedRequests;
    private Attendee mostActiveAttendee;

    public UniversityEventsImpl() {
        attendees= new ArrayList<Attendee>();
        requests= new QueueArrayImpl<EventRequest>();
        rejectedRequests= new LinkedList<EventRequest>();
        entities= new ArrayList<Entity>();
        events= new ArrayList<Event>();
        attendeesEvents= new LinkedList<AttendeeEvent>();
        ratings= new LinkedList<uoc.ds.pr.model.Rating>();
        totalRequests= 0;
        totalRejectedRequests= 0;
        mostActiveAttendee= null;
    }

    @Override
    public void addEntity(String id, String name, String description, EntityType entityType) {

        // Search for an entity
        Entity found= null;
        for(Entity entity : entities)
            if(entity.getId().equals(id))
                found= entity;

        // The entity code is not new, so the entity data will have been updated.
        if (found!=null)
            entities.remove(found);

        // The entity is added
        switch (entityType) {
            case STUDENT:
                entities.add(new Student(id, name, description));
                break;
            case PROFESSOR:
                entities.add(new Professor(id, name, description));
                break;
            case OTHER:
                entities.add(new Organism(id, name, description));
                break;
        }

    }

    @Override
    public void addAttendee(String id, String name, String surname, LocalDate dateOfBirth) {

        // Search for the attendee
        Attendee found= null;
        for(Attendee attendee : attendees)
            if(attendee.getId().equals(id))
                found= attendee;

        // Is updated or added depending on whether it exists
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

        // Search for an entity
        Entity foundEntity= null;
        for(Entity entity : entities)
            if(entity.getId().equals(entityId))
                foundEntity= entity;

        // The entity does not exist
        if (foundEntity==null)
            throw new EntityNotFoundException("The entity does not exist.");

        // Search request
        boolean foundRequet= false;
        Iterator i= requests.values();
        while (i.hasNext()){
            if ( ((EventRequest)i.next()).getRequestId().equals(id) )
                foundRequet= true;
        }

        // The request does not exist.
        if (!foundRequet) {
            requests.add(new EventRequest(id, eventId, entityId, description, installationType, resources, max, startDate, endDate, allowRegister));
            totalRequests++;
        }

    }

    @Override
    public EventRequest updateEventRequest(Status status, LocalDate date, String message) throws NoEventRequestException {

        // If there are no requests in the queue, throw an exception.
        if (requests.isEmpty()) throw new NoEventRequestException("There are no pending requests.");

        // Retrieve the element at the head of the queue and delete it.
        EventRequest er = requests.poll();
        totalRequests--;

        if (status.equals(Status.ENABLED)){
            // Add new event
            events.add(new Event(er.getEventId(),
                    er.getEntityId(),
                    er.getDescription(),
                    er.getInstallationType(),
                    er.getResources(),
                    er.getMax(),
                    er.getStartDate(),
                    er.getEndDate(),
                    er.isAllowRegister()));
        }else{
            // Add rejected request
            rejectedRequests.insertEnd(er);
            totalRejectedRequests++;
        }

        return er;
    }

    @Override
    public void signUpEvent(String attendeeId, String eventId) throws AttendeeNotFoundException, EventNotFoundException, NotAllowedException, AttendeeAlreadyInEventException {

        // Search for an entity
        Event foundEvent= null;
        for(Event event : events)
            if(event.getEventId().equals(eventId))
                foundEvent= event;
        if (foundEvent==null) throw new EventNotFoundException("The event does not exist.");
        if (!foundEvent.isAllowRegister()) throw new NotAllowedException("The event does not admit attendees");

        // Search for the attendee
        Attendee foundAttendee= null;
        for(Attendee attendee : attendees)
            if(attendee.getId().equals(attendeeId))
                foundAttendee= attendee;
        if (foundAttendee==null) throw new AttendeeNotFoundException("The attendee does not exist.");

        // Check if the attendee is already registered
        Iterator i= attendeesEvents.values();
        while (i.hasNext()){
            AttendeeEvent attendeeEvent = (AttendeeEvent) i.next();
            if( attendeeEvent.getEventId().equals(eventId) && attendeeEvent.getAttendeeId().equals(attendeeId) )
                throw new AttendeeAlreadyInEventException("The assistant is already registered for the event.");
        }

        // If the maximum number of registered attendees has been exceeded, they will be added as substitutes.
        int count= 0;
        i= attendeesEvents.values();
        while (i.hasNext())
            if( ((AttendeeEvent)i.next()).getEventId().equals(eventId) )
                count++;
        if (count<5)
            attendeesEvents.insertEnd(new AttendeeEvent(attendeeId,eventId,"normal"));
        else
            attendeesEvents.insertEnd(new AttendeeEvent(attendeeId,eventId,"substitute"));

        // Increase the number of events attended by the attendee
        foundAttendee.incNumEvents();

    }

    @Override
    public double getPercentageRejectedRequests() {

        // Return the percentage of requests that have not been approved
        int total=events.size();
        if (total==0) return 0;
        return (double)numRejectedRequests()/total;

    }

    @Override
    public Iterator<EventRequest> getRejectedRequests() throws NoEventRequestException {

        // If there are no requests in the queue, throw an exception.
        if (rejectedRequests.isEmpty()) throw new NoEventRequestException("There are no pending requests.");

        // Returns an iterator to loop through all the rejected requests.
        return rejectedRequests.values();

    }

    @Override
    public Iterator<Event> getEventsByEntity(String entityId) throws NoEventsException {

        // Search for an Event
        boolean found= false;
        for(Event event : events)
            if(event.getEntityId().equals(entityId))
                found= true;
        if (!found) throw new NoEventsException("There are no events.");

        // Filter by entityId
        ArrayList<Event> filteredList = events.stream()
                .filter(event -> event.getEntityId() == entityId)
                .collect(Collectors.toCollection(ArrayList::new));

        return new IteratorArrayImpl(filteredList.toArray(), filteredList.size(), 0);

    }

    @Override
    public Iterator<Event> getAllEvents() throws NoEventsException {

        // If there are no events in the queue, throw an exception.
        if (events.isEmpty()) throw new NoEventsException("There are no events.");

        // Returns an iterator to loop through all events.
        return new IteratorArrayImpl(events.toArray(), events.size(), 0);

    }

    @Override
    public Iterator<Event> getEventsByAttendee(String attendeeId) throws NoEventsException {

        // Create an auxiliary list
        ArrayList<Event> eventsAux = new ArrayList<>();
        AttendeeEvent ae;
        int count = 0;

        // Adds all events of the same attendee to the auxiliary list
        Iterator i= attendeesEvents.values();
        while (i.hasNext()){
            ae= (AttendeeEvent) i.next();
            if( ae.getAttendeeId().equals(attendeeId) ){
                count ++;
                for(Event event : events)
                    if (event.getEventId().equals(ae.getEventId()))
                        eventsAux.add(event);
            }
        }
        if (count==0) throw new NoEventsException("The attendee has not participated in any event.");
        return new IteratorArrayImpl(eventsAux.toArray(), eventsAux.size(), 0);

    }

    @Override
    public void addRating(String attendeeId, String eventId, Rating rating, String message) throws AttendeeNotFoundException, EventNotFoundException, AttendeeNotInEventException {

        // Search for the attendee
        Attendee foundAttendee= null;
        for(Attendee attendee : attendees)
            if(attendee.getId().equals(attendeeId))
                foundAttendee= attendee;
        if (foundAttendee==null) throw new AttendeeNotFoundException("The attendee does not exist.");

        // Search for the event
        Event foundEvent= null;
        for(Event event : events)
            if(event.getEventId().equals(eventId))
                foundEvent= event;
        if (foundEvent==null) throw new EventNotFoundException("The event does not exist.");

        // Check that the attendee participates in the event
        boolean found= false;
        Iterator i= attendeesEvents.values();
        AttendeeEvent ae;
        while (i.hasNext()){
            ae= (AttendeeEvent) i.next();
            if ( ae.getAttendeeId().equals(attendeeId) && ae.getEventId().equals(eventId) )
                found= true;
        }
        if (found==false) throw new AttendeeNotInEventException("The assistant does not take part in the event.");

        // Add new rating
        ratings.insertEnd(new uoc.ds.pr.model.Rating(attendeeId, eventId, rating, message));

        // The rating of the event also needs to be updated
        int numRatings = numRatingsByEvent(eventId);
        float totalRatings = 0;
        try {
            Iterator<uoc.ds.pr.model.Rating> j = getRatingsByEvent(eventId);
            while (j.hasNext()) {
                uoc.ds.pr.model.Rating r = (uoc.ds.pr.model.Rating) j.next();
                if (r.getEventId().equals(eventId)) {
                    totalRatings+=r.getRating().getValue();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        foundEvent.setRating(totalRatings/numRatings);

    }

    @Override
    public Iterator<uoc.ds.pr.model.Rating> getRatingsByEvent(String eventId) throws EventNotFoundException, NoRatingsException {

        // Create an auxiliary list
        ArrayList<uoc.ds.pr.model.Rating> ratingAux = new ArrayList<>();
        uoc.ds.pr.model.Rating ee;
        int count = 0;

        // Add all ratings for an event to the auxiliary list
        Iterator i= ratings.values();
        while (i.hasNext()){
            ee= (uoc.ds.pr.model.Rating) i.next();
            if( ee.getEventId().equals(eventId) ){
                count ++;
                ratingAux.add(ee);
            }
        }
        if (count==0) throw new EventNotFoundException("The event does not exist.");
        if (ratingAux.size()==0) throw new EventNotFoundException("There are no ratings.");
        return new IteratorArrayImpl(ratingAux.toArray(), ratingAux.size(), 0);

    }

    @Override
    public Attendee mostActiveAttendee() throws AttendeeNotFoundException {

        if (attendeesEvents.isEmpty()) throw new AttendeeNotFoundException("There is no more active assistant.");

        // Search for the most active assistant
        mostActiveAttendee = attendees.get(0);
        for(Attendee attendee : attendees)
            if (attendee.numEvents() > mostActiveAttendee.numEvents())
                mostActiveAttendee = attendee;
        return mostActiveAttendee;

    }

    @Override
    public Event bestEvent() throws EventNotFoundException {

        // Search for the best event
        Event bestEvent = null;
        float max = 0;
        for(Event event : events)
            if(event.rating()>max) {
                max = event.rating();
                bestEvent = event;
            }
        if (bestEvent==null) throw new EventNotFoundException("The event does not exist.");
        return bestEvent;

    }

    @Override
    public int numEntities() {
        // Returns the total number of entities
        return entities.size();
    }

    @Override
    public int numAttendees() {
        // Returns the total number of attendees
        return attendees.size();
    }

    @Override
    public int numRequests() {
        // Returns the total number of requests
        return totalRequests;
    }

    @Override
    public int numEvents() {
        // Returns the total number of events
        return events.size();
    }

    @Override
    public int numEventsByAttendee(String attendeeId) {

        // Returns the number of events of an attendee
        int count=0;
        Iterator i= attendeesEvents.values();
        while (i.hasNext())
            if ( ((AttendeeEvent)i.next()).getAttendeeId().equals(attendeeId) )
                count++;
        return count;

    }

    @Override
    public int numAttendeesByEvent(String eventId) {

        // Returns the number of attendees of an event
        int count=0;
        Iterator i= attendeesEvents.values();
        while (i.hasNext()){
            AttendeeEvent ae = (AttendeeEvent)i.next();
            if ( ae.getEventId().equals(eventId) && ae.getTypeAttendee().equals("normal") )
                count++;
        }
        return count;

    }

    @Override
    public int numSubstitutesByEvent(String eventId) {

        // Returns the number of substitutes of an event
        int count=0;
        Iterator i= attendeesEvents.values();
        while (i.hasNext()){
            AttendeeEvent ae = (AttendeeEvent)i.next();
            if ( ae.getEventId().equals(eventId) && ae.getTypeAttendee().equals("substitute") )
                count++;
        }
        return count;

    }

    @Override
    public int numEventsByEntity(String entityId) {

        // Returns the number of events of an entity
        int count=0;
        for(Event event : events)
            if(event.getEntityId().equals(entityId))
                count++;
        return count;
    }

    @Override
    public int numRejectedRequests() {
        // Returns the total number of rejected requests
        return totalRejectedRequests;
    }

    @Override
    public int numRatingsByEvent(String eventId) {

        // Returns the total number of ratings for an event
        int count = 0;
        Iterator i= ratings.values();
        while (i.hasNext())
            if (  ((uoc.ds.pr.model.Rating) i.next()).getEventId().equals(eventId) )
                count++;
        return count;

    }

    @Override
    public Entity getEntity(String entityId) {
        // Return an entity
        for(Entity entity : entities)
            if(entity.getId().equals(entityId))
                if (entity instanceof Professor) {
                    return (Professor) entity;
                } else if (entity instanceof Student) {
                    return (Student) entity;
                } else if (entity instanceof Organism){
                    return (Organism) entity;
                }
        return null;
    }

    @Override
    public Attendee getAttendee(String attendeeId) {
        // Return an attendee
        for(Attendee attendee : attendees)
            if(attendee.getId().equals(attendeeId))
                return attendee;
        return null;
    }

    @Override
    public Event getEvent(String eventId) {
        // Return an event
        for(Event event : events)
            if(event.getEventId().equals(eventId))
                return event;
        return null;
    }
}
