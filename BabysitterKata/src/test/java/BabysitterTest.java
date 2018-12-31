/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class BabysitterTest {

    private Babysitter babysitter;

    // New babysitter every test
    @Before
    public void initialize() {
        babysitter = new Babysitter();
    }

    // A new babysitter is not busy
    @Test
    public void testNewBabysitterNotBusy() {
        assertEquals(false, babysitter.isBusy());
    }

}
