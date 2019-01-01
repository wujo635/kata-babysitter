import java.util.*;
import java.time.*;
import java.time.format.*;

public class Babysitter {

    /*
    Variable declaration
     */
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
    }

    String getStart() {
        return this.startTime.format(DateTimeFormatter.ofPattern("h:mm a"));
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
