package profile;

import account.Pin;
import person.Person;

import java.util.Iterator;
import java.util.LinkedList;

public class Profile
{
    private Person person;
    private LinkedList<Long> accountNumberList;
    private Pin pin;

    public Profile(Person person, LinkedList<Long> accountNumberList, Pin pin)
    {
        this.person = person;
        this.accountNumberList = accountNumberList;
        this.pin = pin;
    }

    public boolean verifyName(String nameAttempt)
    {
        return (nameAttempt.equals(person.getName()));
    }

    public boolean verifyPin(int pinAttempt)
    {
       return pin.isValidPin(pinAttempt);
    }

    public boolean hasAccounts()
    {
        if (accountNumberList.isEmpty()) return false;
        else return true;
    }

    public void addAccountNumber(long accountNumber)
    {
        accountNumberList.add(accountNumber);
    }

    @Override
    public String toString()
    {
        Long tempAccountNumber;
        StringBuilder sb = new StringBuilder();

        sb.append(person.toString());
        for (Iterator<Long> i = accountNumberList.iterator(); i.hasNext();)
        {
            tempAccountNumber = i.next();
            sb.append(",").append(tempAccountNumber);
        }
        sb.append(",").append(pin.toString());
        return sb.toString();
    }

    public void showInfo()
    {
        Long tempAccountNumber;

        System.out.println();
        person.showInfo();
        System.out.print("Account Numbers: ");
        for (Iterator<Long> i = accountNumberList.iterator(); i.hasNext();)
        {
            tempAccountNumber = i.next();
            System.out.print(tempAccountNumber + ",");
        }
        System.out.println();
        pin.showInfo();
    }
}
