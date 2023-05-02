package uoc.ds.pr;

import edu.uoc.ds.traversal.Iterator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.Rating;
import uoc.ds.pr.model.*;
import uoc.ds.pr.util.ResourceUtil;

import static uoc.ds.pr.UniversityEvents.*;
import static uoc.ds.pr.util.DateUtils.createLocalDate;

public class UniversityEventsPR1Test2 {

    private UniversityEvents universityEvents;

    @Before
    public void setUp() throws Exception {
        this.universityEvents = FactoryUniversityEventsClub2.getUniversityEvents();
    }

    @After
    public void tearDown() {
        this.universityEvents = null;
    }


    public void initialState() {
        Assert.assertEquals(5, this.universityEvents.numEntities());
        Assert.assertEquals(5, this.universityEvents.numAttendees());

    }

    @Test
    public void addEntityTest() {

        // GIVEN:
        initialState();
        //

        this.universityEvents.addEntity("idEntity6", "Maria 6", "Descripcion 6", EntityType.PROFESSOR);
        Professor professor1 = (Professor) this.universityEvents.getEntity("idEntity1");
        Assert.assertEquals("Maria 1", professor1.getName());
        Assert.assertEquals("Description 1", professor1.getDescription());
        Assert.assertEquals(6, this.universityEvents.numEntities());

        this.universityEvents.addEntity("idEntity7", "Maria 7", "Descripcion 7", EntityType.OTHER);
        Organism organism = (Organism) this.universityEvents.getEntity("idEntity2");
        Assert.assertEquals("Maria 2", organism.getName());
        Assert.assertEquals("Description 2", organism.getDescription());
        Assert.assertEquals(7, this.universityEvents.numEntities());

        this.universityEvents.addEntity("idEntity8", "Maria 8", "Descripcion 8", EntityType.STUDENT);
        Student student1 = (Student) this.universityEvents.getEntity("idEntity3");
        Assert.assertEquals("Maria 3", student1.getName());
        Assert.assertEquals("Description 3", student1.getDescription());
        Assert.assertEquals(8, this.universityEvents.numEntities());

    }


    @Test
    public void addAttendeeTest() {

        // GIVEN:
        initialState();
        //

        this.universityEvents.addAttendee("idAttendee6", "Jesus 6", "Alvarez 6", createLocalDate("03-04-1955"));
        Assert.assertEquals("Jesus 6", this.universityEvents.getAttendee("idAttendee6").getName());
        Assert.assertEquals("Alvarez 6", this.universityEvents.getAttendee("idAttendee6").getSurname());
        Assert.assertEquals(6, this.universityEvents.numAttendees());

        this.universityEvents.addAttendee("idAttendee6", "XXX", "XXXX", createLocalDate("03-04-1955"));
        Assert.assertEquals("XXX", this.universityEvents.getAttendee("idAttendee6").getName());
        Assert.assertEquals("XXXX", this.universityEvents.getAttendee("idAttendee6").getSurname());
        Assert.assertEquals(6, this.universityEvents.numAttendees());

        this.universityEvents.addAttendee("idAttendee7", "Jesus 7", "Alvarez 7", createLocalDate("03-04-1955"));
        Assert.assertEquals("Jesus 7", this.universityEvents.getAttendee("idAttendee7").getName());
        Assert.assertEquals("Alvarez 7", this.universityEvents.getAttendee("idAttendee7").getSurname());
        Assert.assertEquals(7, this.universityEvents.numAttendees());

    }



}