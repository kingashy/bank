package account;

public interface AccountTransactions {
    void deposit(double amount);
    void withdraw(double amount);
    void transfer(Account targetAccount, double amount);
}
