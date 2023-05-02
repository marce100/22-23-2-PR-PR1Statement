package uoc.ds.pr;

import static uoc.ds.pr.util.DateUtils.createLocalDate;


public class FactoryUniversityEventsClub2 {


    public static UniversityEvents getUniversityEvents() throws Exception {
        UniversityEvents universityEvents;
        universityEvents = new UniversityEventsImpl();


        ////
        //// ENTITIES
        ////
        universityEvents.addEntity("idEntity1", "Maria 1", "Description 1", UniversityEvents.EntityType.PROFESSOR);
        universityEvents.addEntity("idEntity2", "Maria 2", "Description 2", UniversityEvents.EntityType.OTHER);
        universityEvents.addEntity("idEntity3", "Maria 3", "Description 3", UniversityEvents.EntityType.STUDENT);
        universityEvents.addEntity("idEntity4", "Maria 4", "Description 4", UniversityEvents.EntityType.PROFESSOR);
        universityEvents.addEntity("idEntity5", "Maria 5", "Description 5", UniversityEvents.EntityType.PROFESSOR);

        ////
        //// ATTENDEES
        ////
        universityEvents.addAttendee("idAttendee1", "Jesus 1", "Alvarez 1", createLocalDate("07-01-1932"));
        universityEvents.addAttendee("idAttendee2", "Jesus 2", "Alvarez 2", createLocalDate("07-01-1932"));
        universityEvents.addAttendee("idAttendee3", "Jesus 3", "Alvarez 3", createLocalDate("07-01-1932"));
        universityEvents.addAttendee("idAttendee4", "Jesus 4", "Alvarez 4", createLocalDate("07-01-1932"));
        universityEvents.addAttendee("idAttendee5", "Jesus 5", "Alvarez 5", createLocalDate("07-01-1932"));

        return universityEvents;
    }



}