import java.util.*;
import java.time.*;
import java.time.format.*;

public class Babysitter {

    /*
    Variable declaration
     */
    private final LocalTime earliestTime = LocalTime.of(17, 0);
    private final LocalTime latestTime = LocalTime.of(4, 0);
    int pay;
    char family;
    boolean busy;
    LocalTime startTime, endTime;

    /*
    Constructor(s)
     */
    Babysitter() {
        this.pay = 0;
        this.busy = false;
    }

    /*
    Functions
     */
    void setFamily(String family) {
        // Throw error if babysitter is already busy
        if (this.busy == true) {
            throw new RuntimeException("Babysitter is busy tonight");
        }

        // Check if family is valid, throw error otherwise
        if (family.matches("[a-cA-C]")) {
            this.family = family.toUpperCase().charAt(0);
            this.busy = true;
        } else {
            throw new IllegalArgumentException("Invalid family input");
        }
    }

    void setStart(String inputTime) {
        this.startTime = LocalTime.parse(inputTime, DateTimeFormatter.ofPattern("h:mm a"));
        // Check if time is within range of working hours
        if (this.startTime.compareTo(earliestTime) < 0 && this.startTime.compareTo(latestTime) > 0) {
            throw new IllegalArgumentException("Babysitter is not available during specified time");
        }
    }

    void setEnd(String inputTime) {
        this.endTime = LocalTime.parse(inputTime, DateTimeFormatter.ofPattern("h:mm a"));
        // Check if time is within range of working hours
        if (this.endTime.compareTo(earliestTime) < 0 && this.startTime.compareTo(latestTime) > 0) {
            throw new IllegalArgumentException("Babysitter is not available during specified time");
        }
    }

    String getStart() {
        return this.startTime.format(DateTimeFormatter.ofPattern("h:mm a"));
    }

    String getEnd() {
        return this.endTime.format(DateTimeFormatter.ofPattern("h:mm a"));
    }

    char getFamily() {
        return this.family;
    }

    int getPay() {
        return this.pay;
    }

    boolean isBusy() {
        return this.busy;
    }

    void calculatePay() {

    }
}
