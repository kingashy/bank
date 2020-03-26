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

    //check if the pin attempt is valid
    public boolean verifyPin(int pinAttempt) {
        return pin.verifyPin(pinAttempt);
    }

    //check if the pin entered is 4 digits
    public boolean isValidPinWidth(int pinAttempt) {
        return pin.isValidPinWidth(pinAttempt);
    }

    //update the current pin
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

    //verify the current account number
    public boolean verifyAccountNumber(long number){
        return accountNumber.verifyAccountNumber(number);
    }

    //deposit the specified amount of money  into the specified account
    public void deposit(double amount) {
        balance += amount;
        showBalance();
    }

    //attempt to withdraw the specified amount of money from the source account
    public void withdraw(double amount) {
        if (!isValidWithdrawal(amount)) return;

        balance -= amount;
        showBalance();
    }

    //attempt to transfer the specified amount of money to the target account
    public void transfer(Account targetAccount, double amount) {
        if (!isValidWithdrawal(amount)) return;

        withdraw(amount);
        targetAccount.deposit(amount);
        showBalance();
    }

    private void showBalance(){
        System.out.println("Current Balance: $" + balance);
    }

    public void showInfo() {
        System.out.println();
        showBalance();
        accountNumber.showInfo();
        pin.showInfo();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(balance).append(",").append(pin.toString()).append(",").append(accountNumber.toString());
        return sb.toString();
    }
}
