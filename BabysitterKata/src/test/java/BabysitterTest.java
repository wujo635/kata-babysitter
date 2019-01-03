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
        babysitter.setFamily("D");
    }

    // Family input is valid
    @Test
    public void testValidFamily() {
        babysitter.setFamily("A");
        assertEquals('A', babysitter.getFamily());
    }

    // Babysitter with job is busy
    @Test
    public void testBusyBabysitter() {
        babysitter.setFamily("B");
        assertEquals(true, babysitter.isBusy());
    }

    // Babysit only one family per night
    @Test
    public void testMultipleFamilies() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Babysitter is busy tonight");
        babysitter.setFamily("A");
        babysitter.setFamily("B");
    }

    // Babysitter with earliest start time
    @Test
    public void testEarliestStartTime() {
        babysitter.setStart("5:00 PM");
        assertEquals("5:00 PM", babysitter.getStart());
    }

    // Start time is too early for babysitter
    @Test
    public void testStartTimeTooEarly() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Babysitter is not available during specified time");
        babysitter.setStart("4:59 PM");
    }

    // Start time is too late for babysitter
    @Test
    public void testStartTimeTooLate() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Babysitter is not available during specified time");
        babysitter.setStart("4:01 AM");
    }

    // End time is too early for babysitter
    @Test
    public void testEndTimeTooEarly() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Babysitter is not available during specified time");
        babysitter.setEnd("4:59 PM");
    }

    // End time is too late for babysitter
    @Test
    public void testEndTimeTooLate() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Babysitter is not available during specified time");
        babysitter.setEnd("4:01 AM");
    }

}
