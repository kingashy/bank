package account;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

import file.FileManager;
import profile.Profile;
import profile.ProfileListManager;

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
            BufferedReader buffR = new BufferedReader(new FileReader(getFileName()));

            do {
                tempParse = parseLine(buffR);
                if (tempParse == null) break;
                addAccountFromFile(tempParse);
            } while (tempParse != null);

            buffR.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAccountFromFile(String[] splitLine) {
        Account account;
        AccountNumber accountNumber = new AccountNumber(Long.parseLong(splitLine[2]));

        Pin pin = new Pin(Integer.parseInt(splitLine[1]));
        DebitCard debitCard;
        SecurityBox securityBox;

        //create a Savings or Checking account based on the type of Account
        switch (splitLine[4]) {
            case "Savings":
                securityBox = new SecurityBox(Integer.parseInt(splitLine[3]));
                account = new Savings(Double.parseDouble(splitLine[0]), pin, accountNumber, securityBox);
                break;
            case "Checking":
                debitCard = new DebitCard(Long.parseLong(splitLine[3]));
                account = new Checking(Double.parseDouble(splitLine[0]), pin, accountNumber, debitCard);
                break;
            default:
                System.out.println("Invalid Account Type");
                return;
        }

        accountListManager.add(Long.parseLong(splitLine[2]), account);
    }

    public void removeAccounts(Profile profile) {

        try {
            String tempParse[];
            BufferedReader buffR = new BufferedReader(new FileReader(getFileName()));

            do {
                tempParse = parseLine(buffR);
                if (tempParse == null) break;
                Long accountNumber = Long.parseLong(tempParse[2]);

                if (profile.validAccountNumber(accountNumber)){
                    Account account = accountListManager.find(accountNumber);
                    if (account == null) continue;
                    removeLineFromFile(account.toString());
                }
            } while (tempParse != null);

            buffR.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
