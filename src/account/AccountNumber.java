package account;

import random.Generate;

public class AccountNumber {
    private long accountNum;

    public AccountNumber(long accountNum) {
        this.accountNum = accountNum;
    }

    public AccountNumber(int accountType, int accountOwnerSSN) {
        accountNum = generateAccountNum(accountType, accountOwnerSSN);
    }

    //generate the 11-digit account number
    //1 or 2 depending on Savings or Checking, last two digits of SSN, unique 5-digit number
    //and random 3-digit number
    private long generateAccountNum(int accountType, int accountOwnerSSN) {
        Generate generate = new Generate();
        long tempAccountNum = (long) (accountType * Math.pow(10, 10));
        tempAccountNum += (accountOwnerSSN % 100) * Math.pow(10, 8);
        tempAccountNum += generate.random(5) * Math.pow(10, 3);
        tempAccountNum += generate.random(3);
        return tempAccountNum;
    }

    public long getAccountNum() {
        return accountNum;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(accountNum);
        return sb.toString();
    }

    public void showInfo() {
        System.out.println("Account Number: " + accountNum);
    }
}
