package account;

import java.util.Iterator;
import java.util.LinkedList;

public class AccountListManager {
    private LinkedList<Account> accountList;

    public AccountListManager(LinkedList<Account> accountList) {
        this.accountList = accountList;
    }

    //add an account to the account list
    public void addAccount(Account account) {
        accountList.add(account);
    }

    //remove an account from the account list
    public void removeAccount(Account account) {
        accountList.remove(account);
    }

    //find an account in the linked list based off a pin
    public Account findAccount(int pinAttempt) {
        Account tempAccount;

        for (Iterator<Account> i = accountList.iterator(); i.hasNext(); ) {
            tempAccount = i.next();

            if (tempAccount.isValidPin(pinAttempt)) {
                return tempAccount;
            }
        }

        System.out.println("Account Not Found");
        return null;
    }

    //find an account in the linked list based off an account number
    public Account findAccount(long accountNumber) {
        Account tempAccount;

        for (Iterator<Account> i = accountList.iterator(); i.hasNext(); ) {
            tempAccount = i.next();

            if (accountNumber == tempAccount.getAccountNum()) {
                return tempAccount;
            }
        }

        System.out.println("Account Not Found");
        return null;
    }
}
