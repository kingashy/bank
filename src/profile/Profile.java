package profile;

import account.Account;
import account.AccountFileManager;
import account.AccountListManager;
import account.Pin;
import person.Person;

import java.util.Iterator;
import java.util.LinkedList;

public class Profile {
    private Person person;
    private LinkedList<Long> accountNumberList;
    private Pin pin;

    public Profile(Person person, LinkedList<Long> accountNumberList, Pin pin) {
        this.person = person;
        this.accountNumberList = accountNumberList;
        this.pin = pin;
    }

    public boolean verifyName(String nameAttempt) {
        return (nameAttempt.equals(person.getName()));
    }

    public boolean isValidPinWidth(int pinAttempt) {
        return pin.isValidPinWidth(pinAttempt);
    }

    public boolean isValidPin(int pinAttempt) {
        return pin.isValidPin(pinAttempt);
    }

    public void updatePin(int newPin) {
        pin.updatePin(newPin);
    }

    public boolean hasAccounts() {
        if (accountNumberList.isEmpty()) return false;
        else return true;
    }

    public void addAccountNumber(long accountNumber) {
        accountNumberList.add(accountNumber);
    }

    public void updateAccountPins(int newPin, AccountListManager accountListManager, AccountFileManager accountFileManager) {
        for (Iterator<Long> i = accountNumberList.iterator(); i.hasNext(); ) {
            Long tempAccountNumber = i.next();
            Account account = accountListManager.findAccount(tempAccountNumber);
            if (account != null) {
                accountFileManager.remove(account);
                account.updatePin(newPin);
                accountFileManager.add(account);
            }
        }
    }

    public void showAccounts() {
        Long tempAccountNumber;
        int count = 1;

        System.out.println("\nAccounts: ");
        for (Iterator<Long> i = accountNumberList.iterator(); i.hasNext(); ) {
            tempAccountNumber = i.next();
            System.out.println(count + ": " + tempAccountNumber);
            count++;
        }
    }

    @Override
    public String toString() {
        Long tempAccountNumber;
        StringBuilder sb = new StringBuilder();

        sb.append(person.toString());
        for (Iterator<Long> i = accountNumberList.iterator(); i.hasNext(); ) {
            tempAccountNumber = i.next();
            sb.append(",").append(tempAccountNumber);
        }
        sb.append(",").append(pin.toString());
        return sb.toString();
    }

    public void showInfo() {
        Long tempAccountNumber;

        System.out.println();
        person.showInfo();
        System.out.print("Account Numbers: ");
        for (Iterator<Long> i = accountNumberList.iterator(); i.hasNext(); ) {
            tempAccountNumber = i.next();
            System.out.print(tempAccountNumber + ",");
        }
        System.out.println();
        pin.showInfo();
    }
}
