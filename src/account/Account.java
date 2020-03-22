package account;

public abstract class Account implements Transactions {
    protected String accountType = "";
    protected AccountNumber accountNumber;
    protected Pin pin;
    protected double balance;

    public Account(double balance, Pin pin, AccountNumber accountNumber) {
        this.balance = balance;
        this.pin = pin;
        this.accountNumber = accountNumber;
    }

    public boolean verifyPin(int pinAttempt) {
        return pin.verifyPin(pinAttempt);
    }

    public boolean isValidPinWidth(int pinAttempt) {
        return pin.isValidPinWidth(pinAttempt);
    }

    public void updatePin(int newPin) {
        pin.updatePin(newPin);
    }

    //check if the amount requested to withdrawn can be withdrawn from the specified account
    private boolean isValidWithdrawal(double amount) {
        if (balance - amount < 0) {
            System.out.println("Failure: Account does not have sufficient funds.");
            return false;
        } else return true;
    }

    public boolean verifyAccountNumber(long number){
        return accountNumber.verifyAccountNumber(number);
    }

    //deposit the specified amount of money  into the specified account
    public void deposit(double amount) {
        balance += amount;
    }

    //attempt to withdraw the specified amount of money from the source account
    public void withdraw(double amount) {
        if (!isValidWithdrawal(amount)) return;

        balance -= amount;
    }

    //attempt to transfer the specified amount of money to the target account
    public void transfer(Account targetAccount, double amount) {
        if (!isValidWithdrawal(amount)) return;

        withdraw(amount);
        targetAccount.deposit(amount);
    }

    //show everything about the account
    public void showInfo() {
        System.out.println();
        System.out.println("Balance: $" + balance);
        accountNumber.showInfo();
        pin.showInfo();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(balance).append(",").append(pin.toString()).append(",").append(accountNumber.toString());
        return sb.toString();
    }
}
