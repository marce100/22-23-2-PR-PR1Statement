package uoc.ds.pr;


import edu.uoc.ds.adt.sequential.QueueArrayImpl;
import edu.uoc.ds.traversal.*;
import uoc.ds.pr.model.*;
import uoc.ds.pr.exceptions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import edu.uoc.ds.adt.sequential.LinkedList;

public class UniversityEventsImpl implements UniversityEvents {

    private ArrayList<Attendee> attendees ;                 // Attendees: Java Array: Attendee [].
    //private LinkedList<Event> entityEvents;                 // Events organized by an entity: Linked List: LinkedList.
    private QueueArrayImpl<EventRequest> requests;          // Requests : Queue: QueueArrayImpl.
    private LinkedList<EventRequest> rejectedRequests;      // Rejected Requests: Linked List: LinkedList.
    private ArrayList<Entity> entities ;                    // Entities: Java Array: Entities [].
    private ArrayList<Event> events;                        // Events: Java Array: Events [].


    private LinkedList<AttendeeEvent> attendeesEvents;      // Events an attendee goes to: Linked List: LinkedList.
    private LinkedList<uoc.ds.pr.model.Rating> eventEvaluations;            // Evaluations of an event: Linked List: LinkedList.
    //private QueueArrayImpl<Attendee> eventRegistrations;    // Attendees pointed to an event : Queue: QueueArrayImpl.


    private int totalRequests;                              // Total Requests : Integer: Integer.
    private int totalRejectedRequests;                      // Total Rejected Requests : Integer: Integer.
    private Attendee mostActiveAttendee;                    // Most Active Attendee: Pointer to Attendee.
    //private OrdererVector highestRatedEvent;                // Highest Rated Event: Ordered Vector: OrderedVector.


    public UniversityEventsImpl() {
        attendees= new ArrayList<Attendee>();
        //entityEvents= new LinkedList<Event>();
        requests= new QueueArrayImpl<EventRequest>();
        rejectedRequests= new LinkedList<EventRequest>();
        entities= new ArrayList<Entity>();
        events= new ArrayList<Event>();


        attendeesEvents= new LinkedList<AttendeeEvent>();
        eventEvaluations= new LinkedList<uoc.ds.pr.model.Rating>();
        //eventRegistrations= new QueueArrayImpl<Attendee>();



        totalRequests= 0;
        totalRejectedRequests= 0;
        mostActiveAttendee= null;
        //highestRatedEvent;
    }

    @Override
    public void addEntity(String id, String name, String description, EntityType entityType) {

        Entity found= null;
        for(Entity entity : entities)
            if(entity.getId().equals(id))
                found= entity;

        // The entity code is not new, so the entity data will have been updated.
        if (found!=null)
            entities.remove(found);

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

        Entity foundEntity= null;
        for(Entity entity : entities)
            if(entity.getId().equals(entityId))
                foundEntity= entity;

        // The entity does not exist.
        if (foundEntity==null)
            throw new EntityNotFoundException("The entity does not exist.");

        boolean foundRequet= false;
        Iterator i= requests.values();
        while (i.hasNext()){
            if ( ((EventRequest)i.next()).getRequestId().equals(id) )
                foundRequet= true;
        }
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
            // Añadir al Array de Eventos
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
            // Añadir  a la Lista enlazada de Solicitudes rechazadas
            rejectedRequests.insertEnd(er);
            totalRejectedRequests++;
        }

        return er;
    }

    @Override
    public void signUpEvent(String attendeeId, String eventId) throws AttendeeNotFoundException, EventNotFoundException, NotAllowedException, AttendeeAlreadyInEventException {

        Event foundEvent= null;
        for(Event event : events)
            if(event.getEventId().equals(eventId))
                foundEvent= event;

        if (foundEvent==null) throw new EventNotFoundException("The event does not exist.");
        if (!foundEvent.isAllowRegister()) throw new NotAllowedException("The event does not admit attendees");

        Attendee foundAttendee= null;
        for(Attendee attendee : attendees)
            if(attendee.getId().equals(attendeeId))
                foundAttendee= attendee;

        if (foundAttendee==null) throw new AttendeeNotFoundException("The attendee does not exist.");

        Iterator i= attendeesEvents.values();
        while (i.hasNext()){
            AttendeeEvent attendeeEvent = (AttendeeEvent) i.next();
            if( attendeeEvent.getEventId().equals(eventId) && attendeeEvent.getAttendeeId().equals(attendeeId) )
                throw new AttendeeAlreadyInEventException("The assistant is already registered for the event.");
        }

        int count= 0;
         i= attendeesEvents.values();
        while (i.hasNext()){
            if( ((AttendeeEvent)i.next()).getEventId().equals(eventId) )
                count++;
        }
        if (count</*MAX_NUM_ATTENDEES*/ 5)
            attendeesEvents.insertEnd(new AttendeeEvent(attendeeId,eventId,"normal"));
        else
            attendeesEvents.insertEnd(new AttendeeEvent(attendeeId,eventId,"substitute"));

        foundAttendee.incNumEvents();

    }

    @Override
    public double getPercentageRejectedRequests() {

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

        boolean found= false;
        for(Event event : events)
            if(event.getEntityId().equals(entityId))
                found= true;

        if (!found) throw new NoEventsException("There are no events.");

        // Filter by entityId
        ArrayList<Event> filteredList = events.stream()
                .filter(event -> event.getEntityId() == entityId)
                .collect(Collectors.toCollection(ArrayList::new));

        return new IteratorArrayImpl(filteredList.toArray(), events.size(), 0);

    }

    @Override
    public Iterator<Event> getAllEvents() throws NoEventsException {
        //return null;

        // If there are no events in the queue, throw an exception.
        if (events.isEmpty()) throw new NoEventsException("There are no events.");

        return new IteratorArrayImpl(events.toArray(), events.size(), 0);

        //return (Iterator)events.listIterator();


    }

    @Override
    public Iterator<Event> getEventsByAttendee(String attendeeId) throws NoEventsException {
        //return null;
        /**
         * @pre the attendee exists.
         * @post
         *
         * @return returns an iterator for looping through events attended by an attendee.
         * @throws NoEventsException If the attendee has not participated in any event, the error will be reported
         */

        ArrayList<Event> eventsAux = new ArrayList<>();
        AttendeeEvent ae;
        int count = 0;

        Iterator i= attendeesEvents.values();
        while (i.hasNext()){
            ae= (AttendeeEvent) i.next();
            if( ae.getAttendeeId().equals(attendeeId) ){

                count ++;
                for(Event event : events)
                    if(event.getEntityId().equals(ae.getEventId()))
                        eventsAux.add(event);

            }
        }
        if (count==0) throw new NoEventsException("The attendee has not participated in any event.");
        return new IteratorArrayImpl(eventsAux.toArray(), eventsAux.size(), 0);

    }

    @Override
    public void addRating(String attendeeId, String eventId, Rating rating, String message) throws AttendeeNotFoundException, EventNotFoundException, AttendeeNotInEventException {

        Attendee foundAttendee= null;
        for(Attendee attendee : attendees)
            if(attendee.getId().equals(attendeeId))
                foundAttendee= attendee;
        if (foundAttendee==null) throw new AttendeeNotFoundException("The attendee does not exist.");

        Event foundEvent= null;
        for(Event event : events)
            if(event.getEventId().equals(eventId))
                foundEvent= event;
        if (foundEvent==null) throw new EventNotFoundException("The event does not exist.");

        boolean found= false;
        Iterator i= attendeesEvents.values();
        AttendeeEvent ae;
        while (i.hasNext()){
            ae= (AttendeeEvent) i.next();
            if ( ae.getAttendeeId().equals(attendeeId) && ae.getEventId().equals(eventId) )
                found= true;
        }
        if (found==false) throw new AttendeeNotInEventException("The assistant does not take part in the event.");

        eventEvaluations.insertEnd(new uoc.ds.pr.model.Rating(attendeeId, eventId, rating, message));

        // Hay que actualizar también el rating del evento
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

        ArrayList<uoc.ds.pr.model.Rating> eventsAux = new ArrayList<>();
        uoc.ds.pr.model.Rating ee;
        int count = 0;

        Iterator i= eventEvaluations.values();
        while (i.hasNext()){
            ee= (uoc.ds.pr.model.Rating) i.next();
            if( ee.getEventId().equals(eventId) ){

                count ++;
                eventsAux.add(ee);

            }
        }
        if (count==0) throw new EventNotFoundException("The event does not exist.");
        if (eventsAux.size()==0) throw new EventNotFoundException("There are no ratings.");
        return new IteratorArrayImpl(eventsAux.toArray(), eventsAux.size(), 0);

    }

    @Override
    public Attendee mostActiveAttendee() throws AttendeeNotFoundException {

        if (attendeesEvents.isEmpty()) throw new AttendeeNotFoundException("There is no more active assistant.");

        mostActiveAttendee = attendees.get(0);
        for(Attendee attendee : attendees)
            if (attendee.numEvents() > mostActiveAttendee.numEvents())
                mostActiveAttendee = attendee;
        return mostActiveAttendee;

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
        return totalRequests;
    }

    @Override
    public int numEvents() {
        return events.size();
    }

    @Override
    public int numEventsByAttendee(String attendeeId) {

        int count=0;
        Iterator i= attendeesEvents.values();
        while (i.hasNext()){
            if ( ((AttendeeEvent)i.next()).getAttendeeId().equals(attendeeId) )
                count++;
        }
        return count;

    }

    @Override
    public int numAttendeesByEvent(String eventId) {

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
        return 0;




    }

    @Override
    public int numRejectedRequests() {
        return totalRejectedRequests;
    }

    @Override
    public int numRatingsByEvent(String eventId) {

        int count = 0;
        Iterator i= eventEvaluations.values();
        while (i.hasNext()){
            if (  ((uoc.ds.pr.model.Rating) i.next()).getEventId().equals(eventId) )
                count++;
        }
        return count;

    }

    @Override
    public Entity getEntity(String entityId) {
        for(Entity entity : entities)
            if(entity.getId().equals(entityId))
                if (entity instanceof Professor) {
                    return (Professor) entity;
                } else if (entity instanceof Student) {
                    return (Student) entity;
                } else if (entity instanceof Organism){
                    return (Organism) entity;
                }
                //return entity;
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
