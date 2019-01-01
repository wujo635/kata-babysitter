import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;

import static org.junit.Assert.assertEquals;

import org.junit.rules.ExpectedException;

public class BabysitterTest {

    private Babysitter babysitter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    // New babysitter every test
    @Before
    public void initialize() {
        babysitter = new Babysitter();
    }

    // New babysitter is not busy
    @Test
    public void testNewBabysitterNotBusy() {
        assertEquals(false, babysitter.isBusy());
    }

    // New babysitter has zero pay
    @Test
    public void testNewBabysitterPay() {
        assertEquals(0, babysitter.getPay());
    }

    // Family input parameter is not one of A, B, C
    @Test
    public void testInvalidFamily() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid family input");
        babysitter.setJob("D");
    }

    // Family input is valid
    @Test
    public void testValidFamily() {
        babysitter.setJob("A");
        assertEquals('A', babysitter.getFamily());
    }

    // Babysitter with job is busy
    @Test
    public void testBusyBabysitter() {
        babysitter.setJob("B");
        assertEquals(true, babysitter.isBusy());
    }

    // Babysit only one family per night
    @Test
    public void testMultipleFamilies() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Babysitter is busy tonight");
        babysitter.setJob("A");
        babysitter.setJob("B");
    }

    // Babysitter with earliest start time
    @Test
    public void testEarliestStartTime() {
        babysitter.setStart("5:00 PM");
        assertEquals("5:00 PM", babysitter.getStart());
    }

}
