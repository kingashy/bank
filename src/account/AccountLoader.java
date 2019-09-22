package account;

import java.io.*;

public class AccountLoader
{
    private AccountManager accountManager;
    private AccountListManager accountListManager;
    private AccountFileManager accountFileManager;

    public AccountLoader(AccountManager accountManager, AccountListManager accountListManager, AccountFileManager accountFileManager)
    {
        this.accountManager = accountManager;
        this.accountListManager = accountListManager;
        this.accountFileManager = accountFileManager;

        setup();
    }

    private void setup()
    {
        try
        {
            String tempParse[];
            Account tempAccount;

            BufferedReader buffR = new BufferedReader(new FileReader(accountFileManager.getFileName()));

            do
            {
                tempParse = accountFileManager.parseLine(buffR);
                if (tempParse == null) break;
                tempAccount = accountManager.createAccountFromFile(tempParse);
                tempAccount.showInfo();
                accountListManager.addAccount(tempAccount);
            }  while (tempParse != null);

            buffR.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
