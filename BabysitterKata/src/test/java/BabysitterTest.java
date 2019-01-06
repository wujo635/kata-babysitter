import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Ignore;

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

    // Attempt to get babysitter start time without setting it prior
    @Test
    public void testUninitializedStartTime() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("No start time set");
        babysitter.getStart();
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

    // Calculate pay without proper setup, no family set
    @Test
    public void testCalculatePayWithNoFamily() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Family is not set");
        babysitter.calculatePay();
    }

    // Calculate pay without proper setup, no start time
    @Test
    public void testCalculatePayWithNoStartTime() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Babysitter's schedule is not set");
        babysitter.setFamily("A");
        babysitter.calculatePay();
    }

    // Calculate pay without proper setup, no end time
    @Test
    public void testCalculatePayWithNoEndTime() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Babysitter's schedule is not set");
        babysitter.setFamily("A");
        babysitter.setStart("5:00 PM");
        babysitter.calculatePay();
    }

    // End time before start, when both occur before midnight, end at edge case
    @Test
    public void testEndBeforeStartInPM() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Babysitter cannot stop work before starting");
        babysitter.setFamily("A");
        babysitter.setStart("6:00 PM");
        babysitter.setEnd("5:00 PM");
        babysitter.calculatePay();
    }

    // End time before start, when both occur after midnight, start at edge case
    @Test
    public void testEndBeforeStartInAM() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Babysitter cannot stop work before starting");
        babysitter.setFamily("A");
        babysitter.setStart("4:00 AM");
        babysitter.setEnd("3:00 AM");
        babysitter.calculatePay();
    }

    // End time before start, when end is PM, start is AM
    @Test
    public void testEndBeforeStartAcrossMidnight() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Babysitter cannot stop work before starting");
        babysitter.setFamily("A");
        babysitter.setStart("1:00 AM");
        babysitter.setEnd("11:00 PM");
        babysitter.calculatePay();
    }

    // No fractional hours worked allowed
    @Test
    public void testFractionalHoursNotAllowed() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Babysitter only works full hours");
        babysitter.setStart("5:15 PM");
    }

    // Start = end time, pay is zero
    @Test
    public void testZeroHoursSchedule() {
        babysitter.setFamily("A");
        babysitter.setStart("4:00 AM");
        babysitter.setEnd("4:00 AM");
        babysitter.calculatePay();
        assertEquals(0, babysitter.getPay());
    }

    // Family A, work until 11 PM, no payrate changes
    @Test
    public void testFamilyANoPayrateChange() {
        babysitter.setFamily("A");
        babysitter.setStart("5:00 PM");
        babysitter.setEnd("11:00 PM");
        babysitter.calculatePay();
        assertEquals(90, babysitter.getPay());
    }

    // Family A, work until midnight, payrate changes
    @Test
    public void testFamilyAWorkWithPayrateChange() {
        babysitter.setFamily("A");
        babysitter.setStart("5:00 PM");
        babysitter.setEnd("12:00 AM");
        babysitter.calculatePay();
        assertEquals(110, babysitter.getPay());
    }

    // Family A, work full allowable time
    @Test
    public void testFamilyAFulltime() {
        babysitter.setFamily("A");
        babysitter.setStart("5:00 PM");
        babysitter.setEnd("4:00 AM");
        babysitter.calculatePay();
        assertEquals(190, babysitter.getPay());
    }

    // Family A, work after midnight only
    @Test
    public void testFamilyALateNightHoursOnly() {
        babysitter.setFamily("A");
        babysitter.setStart("12:00 AM");
        babysitter.setEnd("4:00 AM");
        babysitter.calculatePay();
        assertEquals(80, babysitter.getPay());
    }

    // Family B, work with two payrate changes
    @Test
    public void testFamilyBTwoPayrateChanges() {
        babysitter.setFamily("B");
        babysitter.setStart("5:00 PM");
        babysitter.setEnd("1:00 AM");
        babysitter.calculatePay();
        assertEquals(92, babysitter.getPay());
    }

    // Family B, work between two payrate changes
    @Test
    public void testFamilyBBetweenPayrateChanges() {
        babysitter.setFamily("B");
        babysitter.setStart("10:00 PM");
        babysitter.setEnd("12:00 AM");
        babysitter.calculatePay();
        assertEquals(16, babysitter.getPay());
    }
}
