package account;

import java.util.HashMap;

public class AccountListManager {
    private HashMap<Long, Account> accountList;

    public AccountListManager(HashMap<Long, Account> accountList) {
        this.accountList = accountList;
    }

    //add an account to the account list
    public void addAccount(Account account) {
        accountList.put(account.getAccountNum(), account);
    }

    //remove an account from the account list
    public void removeAccount(Account account) {
        accountList.remove(account.getAccountNum());
    }

    //find an account in the linked list based off an account number
    public Account findAccount(long accountNumber) {
        Account account;

        account = accountList.get(accountNumber);
        if (account != null) return account;

        System.out.println("Account Not Found");
        return null;
    }
}
