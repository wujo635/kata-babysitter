import java.util.*;
import java.time.*;
import java.time.format.*;

public class Babysitter {

    /*
    Variable declaration
     */
    private final LocalTime earliestTime = LocalTime.of(17, 0);
    private final LocalTime latestTime = LocalTime.of(4, 0);
    private int pay;
    private char family;
    private boolean busy;
    private LocalTime startTime, endTime;

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
        // Check minutes-of-hour field
        if (this.startTime.getMinute() != 0) {
            throw new IllegalArgumentException("Babysitter only works full hours");
        }
    }

    void setEnd(String inputTime) {
        this.endTime = LocalTime.parse(inputTime, DateTimeFormatter.ofPattern("h:mm a"));
        // Check if time is within range of working hours
        if (this.endTime.compareTo(earliestTime) < 0 && this.endTime.compareTo(latestTime) > 0) {
            throw new IllegalArgumentException("Babysitter is not available during specified time");
        }
        // Check minutes-of-hour field
        if (this.endTime.getMinute() != 0) {
            throw new IllegalArgumentException("Babysitter only works full hours");
        }
    }

    String getStart() {
        try {
            return this.startTime.format(DateTimeFormatter.ofPattern("h:mm a"));
        } catch (NullPointerException e) {
            throw new NullPointerException("No start time set");
        }

    }

    String getEnd() {
        try {
            return this.endTime.format(DateTimeFormatter.ofPattern("h:mm a"));
        } catch (NullPointerException e) {
            throw new NullPointerException("No end time set");
        }
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
        // Final checks for good input
        this.checkForErrors();

        // If time elapsed is zero, no need to calculate
        if (Duration.between(this.startTime,this.endTime).toHours() == 0) {
            this.pay = 0;
            return;
        }

        // Switch case for pay per family
        switch (this.family) {
            case 'A':
                break;
            case 'B':
                break;
            case 'C':
                break;
            default:
                // This should never be reached but seems applicable for default case
                throw new IllegalArgumentException("Family not recognized");
        }
    }

    void checkForErrors() {
        boolean timeError = false;

        // Error if any of family, start, or end time is null or not set
        if (this.family == '\0') {
            throw new RuntimeException("Family is not set");
        }
        try {
            this.startTime.toString();
            this.endTime.toString();
        } catch (NullPointerException e) {
            throw new NullPointerException("Babysitter's schedule is not set");
        }

        // Check if start/end times make sense
        if (this.endTime.isBefore(this.startTime)) {
            if (this.startTime.isBefore(this.latestTime.minusMinutes(-1)) && this.endTime.isBefore(this.latestTime.minusMinutes(-1))) {
                timeError = true;
            } else if (this.startTime.isAfter(this.earliestTime.minusMinutes(1)) && this.endTime.isAfter(this.earliestTime.minusMinutes(1))) {
                timeError = true;
            }
        } else if (this.startTime.isBefore(this.latestTime) && this.endTime.isAfter(this.earliestTime)) {
            timeError = true;
        }

        if (timeError) {
            throw new RuntimeException("Babysitter cannot stop work before starting");
        }
    }

}
