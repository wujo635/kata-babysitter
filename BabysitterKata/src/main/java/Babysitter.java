import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Babysitter {

    /*
    Variable declaration
     */
    int pay;
    char family;
    boolean busy;
    String startTime, endTime;

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
    void setJob(String family) {
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

    void setStart(String time) {
        Date date;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
            this.startTime = dateFormat.format(dateFormat.parse(time));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    String getStart() {
        return this.startTime;
    }

    void calculatePay() {

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
}
