package account;

import java.io.*;

import file.FileManager;

public class AccountFileManager extends FileManager {
    private AccountListManager accountListManager;

    public AccountFileManager(AccountListManager accountListManager, String fileName) {
        super(fileName);
        this.accountListManager = accountListManager;

        setup();
    }

    public String getFileName() {
        return fileName;
    }

    private void setup() {
        try {
            String tempParse[];
            Account tempAccount;

            BufferedReader buffR = new BufferedReader(new FileReader(getFileName()));

            do {
                tempParse = parseLine(buffR);
                if (tempParse == null) break;
                tempAccount = addAccountFromFile(tempParse);
                //tempAccount.showInfo();
                accountListManager.addAccount(tempAccount);
            } while (tempParse != null);

            buffR.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Account addAccountFromFile(String[] splitLine) {
        Account tempAccount;
        AccountNumber accountNumber = new AccountNumber(Long.parseLong(splitLine[2]));

        Pin pin = new Pin(Integer.parseInt(splitLine[1]));
        DebitCard debitCard;
        SecurityBox securityBox;

        //create a Savings or Checking account based on the type of Account
        switch (splitLine[4]) {
            case "Savings":
                securityBox = new SecurityBox(Integer.parseInt(splitLine[3]));
                tempAccount = new Savings(Double.parseDouble(splitLine[0]), pin, accountNumber, securityBox);
                break;
            case "Checking":
                debitCard = new DebitCard(Long.parseLong(splitLine[3]));
                tempAccount = new Checking(Double.parseDouble(splitLine[0]), pin, accountNumber, debitCard);
                break;
            default:
                System.out.println("Invalid Account Type");
                return null;
        }

        return tempAccount;
    }

    //add an account to the accounts.txt file
    public void add(Account account) {
        try {
            //open the buffer in append mode
            BufferedWriter buffW = new BufferedWriter(new FileWriter(fileName, true));

            buffW.write((account.toString()).trim());
            buffW.newLine();

            buffW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //remove an account to the accounts.txt file
    public void remove(Account account) {
        removeLineFromFile(account.toString());
    }
}
