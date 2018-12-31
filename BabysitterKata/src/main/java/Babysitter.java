public class Babysitter {

    /*
    Variable declaration
     */
    int pay;
    char family;
    boolean busy;

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
        if (!family.matches("[a-cA-C]")) {
            throw new IllegalArgumentException("Invalid family input");
        } else {
            this.family = family.toUpperCase().charAt(0);
            this.busy = true;
        }
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
