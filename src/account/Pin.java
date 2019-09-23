package account;

import random.Generate;

public class Pin
{
    private int accountPin;

    public Pin(int newPin)
    {
        updatePin(newPin);
    }

    //check if the pin entered is 4 digits
    private boolean isValidPinWidth(int accountPin)
    {
        if ((accountPin > 9999) || (accountPin < 0))
        {
            System.out.println("Failure: Invalid pin.");
            return false;
        }
        else return true;
    }

    //attempt to set a 4-digit pin to the specified account
    public void updatePin(int newPin)
    {
        if (!isValidPinWidth(newPin)) return;

        this.accountPin = newPin;
        //System.out.println("New Pin Set");
    }

    //check if the pin attempted is valid
    public boolean isValidPin(int pinAttempt)
    {
        if (pinAttempt == accountPin)
        {
            System.out.println("\nValid Pin Entry");
            return true;
        }
        else
        {
            System.out.println("Invalid Pin Entry");
            return false;
        }
    }

    //generate a 4 digit pin
    public void generatePin()
    {
        accountPin = (int)(new Generate().random(4));
        System.out.println("New Generated Pin: " + accountPin);
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(accountPin);
        return sb.toString();
    }

    public void showInfo()
    {
        System.out.println("Pin: " + accountPin);
    }
}
