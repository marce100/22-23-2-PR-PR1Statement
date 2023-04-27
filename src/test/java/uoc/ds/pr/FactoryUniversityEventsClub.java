package uoc.ds.pr;

import static uoc.ds.pr.util.DateUtils.createLocalDate;

import org.junit.Assert;


public class FactoryUniversityEventsClub {


    public static UniversityEvents getUniversityEvents() throws Exception {
        UniversityEvents universityEvents;
        universityEvents = new UniversityEventsImpl();


        ////
        //// ENTITIES
        ////
        universityEvents.addEntity("idEntity1", "Maria Simo", "AI for Human Well-being", UniversityEvents.EntityType.PROFESSOR);
        universityEvents.addEntity("idEntity2", "Àlex Lluna", "eHealhthLab ", UniversityEvents.EntityType.OTHER);
        universityEvents.addEntity("idEntity3", "Pepet Ferra", "Formula Student-UOC", UniversityEvents.EntityType.STUDENT);
        universityEvents.addEntity("idEntity4", "Joana Quilez", "Research group in Epidemiology", UniversityEvents.EntityType.PROFESSOR);
        universityEvents.addEntity("idEntity5", "Armand Morata", "Care and preparedness in the network society", UniversityEvents.EntityType.PROFESSOR);

        ////
        //// ATTENDEES
        ////
        universityEvents.addAttendee("idAttendee1", "Jesus", "Sallent", createLocalDate("07-01-1932"));
        universityEvents.addAttendee("idAttendee2", "Anna", "Casals", createLocalDate("09-07-1988"));
        universityEvents.addAttendee("idAttendee3", "Mariajo", "Padró", createLocalDate("02-06-1992"));
        universityEvents.addAttendee("idAttendee4", "Agustí", "Padró", createLocalDate("15-01-2005"));
        universityEvents.addAttendee("idAttendee5", "Pepet", "Marieta", createLocalDate("23-04-2010"));
        universityEvents.addAttendee("idAttendee6", "Emma", "Garcia", createLocalDate("23-04-2001"));
        universityEvents.addAttendee("idAttendee7", "Pablo", "Gimenez", createLocalDate("23-03-2005"));

        return universityEvents;
    }



}