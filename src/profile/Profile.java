package profile;

import account.Account;
import account.AccountFileManager;
import account.AccountListManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

public class Profile {
    private String name;
    private int ssn;
    private LinkedList<Long> accountNumberList;

    public Profile(String name, int ssn, LinkedList<Long> accountNumberList) {
        this.name = name;
        this.ssn = ssn;
        this.accountNumberList = accountNumberList;
    }

    public String getName() {
        return name;
    }

    public boolean verifyName(String nameAttempt) {
        return (nameAttempt.equals(name));
    }

    public boolean verifySSN(int ssn) {
        return this.ssn == ssn;
    }

    public boolean hasAccounts() {
        if (accountNumberList.isEmpty()) return false;
        else return true;
    }

    public void addAccountNumber(long accountNumber) {
        accountNumberList.add(accountNumber);
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

        sb.append(name).append(",").append(ssn);
        for (Iterator<Long> i = accountNumberList.iterator(); i.hasNext(); ) {
            tempAccountNumber = i.next();
            sb.append(",").append(tempAccountNumber);
        }
        return sb.toString();
    }

    public void showInfo() {
        Long tempAccountNumber;

        //System.out.println();
        System.out.println("Name: " + name);
        System.out.print("Account Numbers: ");
        for (Iterator<Long> i = accountNumberList.iterator(); i.hasNext(); ) {
            tempAccountNumber = i.next();
            System.out.print(tempAccountNumber + ",");
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return ssn == profile.ssn &&
                name.equals(profile.name) &&
                accountNumberList.equals(profile.accountNumberList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ssn, accountNumberList);
    }
}
