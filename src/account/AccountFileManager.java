package account;

import java.io.*;

import file.FileManager;
import profile.Profile;

public class AccountFileManager extends FileManager {
    private AccountListManager accountListManager;

    public AccountFileManager(AccountListManager accountListManager, String fileName) {
        super(fileName);
        this.accountListManager = accountListManager;

        setup();
    }

    //load all accounts from the local file
    private void setup() {
        try {
            String tempParse[];
            File accountFile = new File(fileName);
            if (!accountFile.exists()) accountFile.createNewFile();
            BufferedReader buffR = new BufferedReader(new FileReader(accountFile));
            //BufferedReader buffR = new BufferedReader(new FileReader(getFileName()));

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

    //create a singular account from a line parse
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

    //remove all accounts from the file associated with the specified profile
    public void removeAccounts(Profile profile) {
        try {
            String tempParse[];
            BufferedReader buffR = new BufferedReader(new FileReader(fileName));

            do {
                tempParse = parseLine(buffR);
                if (tempParse == null) break;
                Long accountNumber = Long.parseLong(tempParse[2]);

                if (profile.validAccount(accountNumber)){
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

    //add an account to the local file
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

    //remove an account to the local file
    public void remove(Account account) {
        removeLineFromFile(account.toString());
    }
}
