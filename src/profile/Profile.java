package profile;

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

    public boolean verifyName(String nameAttempt) {
        return nameAttempt.equals(name);
    }

    public boolean verifySSN(int ssn) {
        return this.ssn == ssn;
    }

    //check if the profile has accounts
    public boolean hasAccounts() {
        if (accountNumberList.isEmpty()) return false;
        else return true;
    }

    //add an account
    public void addAccount(long accountNumber) {
        accountNumberList.add(accountNumber);
    }

    //remove an account
    public void removeAccount(long accountNumber){
        accountNumberList.remove(accountNumber);
    }

    //check if the account number attempt belongs to the profile
    public boolean validAccount(long number){
        for (Iterator<Long> i = accountNumberList.iterator(); i.hasNext(); ) {
            Long accountNumber = i.next();
            if (accountNumber == number) return true;
        }
        return false;
    }

    public void showAccounts() {
        Long accountNumber;
        int count = 1;

        System.out.println("\nAccounts: ");
        for (Iterator<Long> i = accountNumberList.iterator(); i.hasNext(); ) {
            accountNumber = i.next();
            System.out.println(count + ": " + accountNumber);
            count++;
        }
    }

    public void showInfo() {
        Long accountNumber;

        //System.out.println();
        System.out.println("Name: " + name);
        System.out.print("Account Numbers: ");
        for (Iterator<Long> i = accountNumberList.iterator(); i.hasNext(); ) {
            accountNumber = i.next();
            System.out.print(accountNumber + ",");
        }
        System.out.println();
    }

    @Override
    public String toString() {
        Long accountNumber;
        StringBuilder sb = new StringBuilder();

        sb.append(name).append(",").append(ssn);
        for (Iterator<Long> i = accountNumberList.iterator(); i.hasNext(); ) {
            accountNumber = i.next();
            sb.append(",").append(accountNumber);
        }
        return sb.toString();
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
