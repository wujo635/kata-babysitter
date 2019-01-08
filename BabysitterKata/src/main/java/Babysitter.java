import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/** 
 * Represents a babysitter which can calculate a night's pay based off a given family,
 * start time, and end time. There are restrictions to these inputs, listed below.
 * 
 * The babysitter:
 *    > starts no earlier than 5:00PM
 *    > only babysits for one family per night
 *    > gets paid for full hours (no fractional hours)
 *    > should be prevented from mistakes when entering times
 *
 * The calculated pay is based off the following rules for the job performed.

 * The job:
 *    > Pays different rates for each family (based on bedtimes, kids and pets, etc...)
 *    > Family A pays $15 per hour before 11pm, and $20 per hour the rest of the night
 *    > Family B pays $12 per hour before 10pm, $8 between 10 and 12, and $16 the rest of the night
 *    > Family C pays $21 per hour before 9pm, then $15 the rest of the night
 *    > The time ranges are the same as the babysitter (5pm through 4am)
 * 
 * @author Johnny Wu
 * @version 1.0  
*/
public class Babysitter {

    /*
     * Private variables to encapsulate members of the class. The EARLIEST and LATEST times are 
     * final to prevent any modification after initialization.
     */
    private final LocalTime EARLIEST = LocalTime.of(17, 0);
    private final LocalTime LATEST = LocalTime.of(4, 0);
    private int pay;
    private char family;
    private boolean busy;
    private LocalDateTime startTime, endTime;

    /*
     * Default constructor for Babysitter initializes the pay and job status
     */
    Babysitter() {
        this.pay = 0;
        this.busy = false;
    }

    /*
     * Check the family input and if valid set babysitter as busy.
     * @param family Expect a single character string that is one of A, B, or C
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

    /*
     * Check input to be within babysitter's working hours. Assume the babysitter operates the same
     * everyday and make use of LocalDateTime's now() function to differentiate between today and
     * tomorrow for times before and after midnight, respectively.
     * @param input Time expected in format h:mm a where h, m, a represent hour, minute, am/pm
     */
    LocalDateTime setupTime(String input) {
        LocalTime time = LocalTime.parse(input, DateTimeFormatter.ofPattern("h:mm a"));
        LocalDate date = LocalDate.now();

        // Check if time is within range of working hours
        if (time.compareTo(EARLIEST) < 0 && time.compareTo(LATEST) > 0) {
            throw new IllegalArgumentException("Babysitter not available during specified time");
        }
        // Check minutes-of-hour field
        if (time.getMinute() != 0) {
            throw new IllegalArgumentException("Babysitter only works full hours");
        }
        // Move day to tomorrow if hour crosses midnight
        if (time.getHour() <= 4) {
            date = date.plusDays(1);
        }
        return LocalDateTime.of(date, time);
    }

    /*
     * Set the babysitter's start time
     * @param inputTime The start time for babysitter's job
     */
    void setStart(String inputTime) {
        this.startTime = setupTime(inputTime);
    }

    /*
     * Set the babysitter's end time
     * @param inputTime The end time for babysitter's job
     */
    void setEnd(String inputTime) {
        this.endTime = setupTime(inputTime);
    }

    /*
     * Return the start time for babysitter's job
     */
    String getStart() {
        try {
            return this.startTime.format(DateTimeFormatter.ofPattern("h:mm a"));
        } catch (NullPointerException e) {
            throw new NullPointerException("No start time set");
        }

    }

    /*
     * Return the end time for babysitter's job
     */
    String getEnd() {
        try {
            return this.endTime.format(DateTimeFormatter.ofPattern("h:mm a"));
        } catch (NullPointerException e) {
            throw new NullPointerException("No end time set");
        }
    }

    /*
     * Return the family for babysitter's job
     */
    char getFamily() {
        return this.family;
    }

    /*
     * Return the total pay for babysitter's job
     */
    int getPay() {
        return this.pay;
    }

    /*
     * Return whether or not babysitter has a job set
     */
    boolean isBusy() {
        return this.busy;
    }

    /*
     * Calculate the pay for the babysitter's job
     */
    void calculatePay() {
        int hour;
        int hoursToWork;
        LocalDateTime counter;

        // Final checks for good input
        this.checkForErrors();

        // If start = end time, no need to calculate
        if (this.startTime.compareTo(this.endTime) == 0) {
            this.pay = 0;
            return;
        }

        hoursToWork = (int) ChronoUnit.HOURS.between(startTime, endTime);
        counter = this.startTime;

        // Add amount paid per hour worked to running total
        for (int i = 0; i < hoursToWork; i++) {
            // Switch case for pay per family
            switch (this.family) {
                case 'A':
                    if (counter.getHour() >= 17 && counter.getHour() < 23) {
                        this.pay += 15;
                    } else {
                        this.pay += 20;
                    }
                    break;
                case 'B':
                    if (counter.getHour() >= 17 && counter.getHour() < 22) {
                        this.pay += 12;
                    } else if (counter.getHour() >= 22) {
                        this.pay += 8;
                    } else {
                        this.pay += 16;
                    }
                    break;
                case 'C':
                    if (counter.getHour() >= 17 && counter.getHour() < 21) {
                        this.pay += 21;
                    } else {
                        this.pay += 15;
                    }
                    break;
                default:
                    // This should never be reached but seems applicable for default case
                    throw new IllegalArgumentException("Family not recognized");
            }
            counter = counter.plusHours(1);
        }
    }

    /*
     * Calculate the pay for the babysitter's job given the job parameters as input
     * @param family Family for whom the babysitter works for
     * @param start Start time for job
     * @param end End time for job
     */
    void calculatePay(String family, String start, String end) {
        this.setFamily(family);
        this.setStart(start);
        this.setEnd(end);
        this.calculatePay();
    }

    /*
     * Check for potential issues prior to calculating pay such as having any required input
     * uninitialized or that the end time occurs prior to start time
     */
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
        if (endTime.isBefore(startTime)) {
            throw new RuntimeException("Babysitter cannot stop work before starting");
        }
    }

}
