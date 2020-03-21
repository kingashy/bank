package account;

public class Pin {
    private int pin;

    public Pin(int pin) {
        if (isValidPinWidth(pin)) this.pin = pin;
    }

    //check if the pin entered is 4 digits
    public boolean isValidPinWidth(int pin) {
        if ((pin > 9999) || (pin < 1000)) {
            System.out.println("Failure: Invalid pin.");
            return false;
        } else return true;
    }

    //attempt to set a 4-digit pin to the specified account
    public void updatePin(int newPin) {
        if (!isValidPinWidth(newPin)) return;

        this.pin = newPin;
    }

    //check if the pin attempted is valid
    public boolean verifyPin(int pinAttempt) {
        if (pinAttempt == pin) {
            return true;
        } else {
            System.out.println("Invalid Pin Entry");
            return false;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(pin);
        return sb.toString();
    }

    public void showInfo() {
        System.out.println("Pin: " + pin);
    }
}
