package account;

public class AccountSecurity
{
    //attempt to unlock the specified account with the specified 4-digit pin
    public boolean access(Account account, int pinAttempt)
    {
        if (!account.isValidPin(pinAttempt)) return false;
        else
        {
            System.out.println("Account " + account.getAccountNum() + ": Unlocked");
            return true;
        }
    }
}
