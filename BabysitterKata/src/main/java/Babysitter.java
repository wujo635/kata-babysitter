import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Babysitter {

    // Variables
    private final LocalTime earliestTime = LocalTime.of(17, 0);
    private final LocalTime latestTime = LocalTime.of(4, 0);
    private int pay;
    private char family;
    private boolean busy;
    private LocalDateTime startTime, endTime;

    // Constructors
    Babysitter() {
        this.pay = 0;
        this.busy = false;
    }

    // Functions
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

    LocalDateTime setupTime(String input) {
        LocalTime time = LocalTime.parse(input, DateTimeFormatter.ofPattern("h:mm a"));
        LocalDate date = LocalDate.now();

        // Check if time is within range of working hours
        if (time.compareTo(earliestTime) < 0 && time.compareTo(latestTime) > 0) {
            throw new IllegalArgumentException("Babysitter is not available during specified time");
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

    void setStart(String inputTime) {
        this.startTime = setupTime(inputTime);
    }

    void setEnd(String inputTime) {
        this.endTime = setupTime(inputTime);
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
