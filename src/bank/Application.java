package bank;

import java.util.LinkedList;
import java.util.Scanner;

import file.FileManager;
import input.Input;
import account.*;
import profile.*;
import person.*;

public class Application {
    public static void main(String[] args) {
        final String accountsFileName = "accounts.txt";
        final String profilesFileName = "profiles.txt";
        final String bankName = "Big Bank";
        final int quitVal = 0;

        LinkedList<Profile> profileList = new LinkedList<Profile>();
        LinkedList<Account> accountList = new LinkedList<Account>();

        Input input = new Input(new Scanner(System.in));
        FileManager fileManager = new FileManager();
        AccountManager accountManager = new AccountManager();
        AccountListManager accountListManager = new AccountListManager(accountList);
        AccountFileManager accountFileManager = new AccountFileManager(fileManager, accountsFileName);
        ProfileManager profileManager = new ProfileManager();
        ProfileListManager profileListManager = new ProfileListManager(profileList);
        ProfileFileManager profileFileManager = new ProfileFileManager(fileManager, profilesFileName);

        new AccountLoader(accountManager, accountListManager, accountFileManager);
        new ProfileLoader(profileManager, profileListManager, profileFileManager);

        Account account = null;
        String generalInput = "";
        boolean printMenu = true;

        System.out.println("\nWelcome to " + bankName + "!\n");
        System.out.print("Enter your name: ");
        String name = input.getString().trim();
        Profile profile = profileListManager.findProfile(name);

        applicationLoop:
        do {
            int pinAttempts = 0;

            if (profile == null) {
                System.out.println("\nOptions: ");
                System.out.println("0. Quit");
                System.out.println("1. Create an profile");
                System.out.print("Input: ");
                generalInput = input.getString();

                switch (Integer.parseInt(generalInput)) {
                    case quitVal: //Quit
                        System.out.println("\nGoodbye!");
                        break applicationLoop;
                    case 1: //Create Bank Profile
                        System.out.print("\nSocial Security Number: ");
                        int tempSSN = input.getInt();

                        System.out.print("Pin: ");
                        Pin pin = new Pin(input.getInt());
                        if (pin == null) continue;

                        profile = profileManager.createProfile(new Person(name, tempSSN), new LinkedList<Long>(), pin);
                        profileListManager.addProfile(profile);
                        profileFileManager.addProfile(profile);

                        System.out.println("\nProfile created!");
                        System.out.println("This application will now restart. You will be prompted to re-enter your pin.");
                        continue applicationLoop;
                    default: //Invalid Option
                        System.out.println("Invalid Option. Try again.");
                        break;
                }
            }

            System.out.println("\nYou will have 3 attempts to enter your pin.");

            pinAttemptLoop:
            do {
                System.out.print("Pin: ");

                if (profile.isValidPin(input.getInt())) break pinAttemptLoop;
                else pinAttempts++;

                if (pinAttempts == 3) {
                    System.out.println("\nYou ran out of attempts. Please try again later. Goodbye!");
                    break applicationLoop;
                }
            } while (pinAttempts <= 2);

            profileActionsLoop:
            do {
                if (printMenu) {
                    System.out.println("\nProfile Actions Menu");
                    System.out.println("---------");
                    System.out.println("1. Enter a New Pin");
                    System.out.println("2. Add an account");
                    System.out.println("3. Delete an account");
                    System.out.println("4. Manage an account");
                    printMenu = false;
                }

                System.out.println("\nWhat would you like to do?");
                System.out.print("Input: ");
                generalInput = input.getString();

                switch (Integer.parseInt(generalInput)) {
                    case 1: //Enter a New Pin
                        System.out.print("\nNew Pin: ");
                        int updatedPin = input.getInt();

                        if (profile.isValidPinWidth(updatedPin)) {
                            profileFileManager.removeProfile(profile);
                            profile.updatePin(updatedPin);
                            profileFileManager.addProfile(profile);

                            if (profile.hasAccounts()) {
                                profile.updateAccountPins(updatedPin, accountListManager, accountFileManager);
                            }
                        }

                        break;
                    case 2: //Create an account
                        System.out.print("\nInitial Deposit: $");
                        double deposit = input.getDouble();

                        System.out.print("Account Type (Enter 1 for Checking, 2 for Savings): ");
                        int accountType = input.getInt();

                        System.out.print("Social Security Number (must match your profile's SSN): ");
                        int ssn = input.getInt();

                        System.out.print("Pin (must match your profile's pin): ");
                        Pin pin = new Pin(input.getInt());

                        AccountNumber accountNumber = null;

                        switch (accountType) {
                            case 1:
                                accountNumber = new AccountNumber(1, ssn);
                                account = accountManager.createChecking(deposit, pin, accountNumber, new DebitCard());
                                break;
                            case 2:
                                accountNumber = new AccountNumber(2, ssn);
                                account = accountManager.createSavings(deposit, pin, accountNumber, new SecurityBox());
                                break;
                            default:
                                System.out.println("Invalid Account Type. Please try again.");
                                continue;
                        }

                        accountListManager.addAccount(account);
                        accountFileManager.add(account);
                        profileFileManager.removeProfile(profile);
                        profile.addAccountNumber(accountNumber.getAccountNum());
                        profileFileManager.addProfile(profile);

                        System.out.println("Account created!");
                        break;
                    case 3: //Delete an account
                        System.out.print("Account Number: ");

                        account = accountListManager.findAccount(input.getLong());

                        if (account == null) {
                            System.out.println("Invalid Account Number.");
                            continue;
                        }

                        accountFileManager.remove(account);
                        accountListManager.removeAccount(account);
                        accountManager.delete(account);

                        System.out.println("\nAccount deleted!\nPlease restart the application.");
                        break;
                    case 4: //Manage an account
                        if (profile.hasAccounts()) {
                            System.out.println("\nWhich account are you working on?");
                            profile.showAccounts();

                            System.out.print("\nAccount Number: ");
                            String accountNumberStr = input.getString().trim();

                            account = accountListManager.findAccount(Long.parseLong(accountNumberStr));

                            if (account == null) {
                                System.out.println("Invalid Account Number. Try again.");
                                continue;
                            }
                        } else {
                            System.out.println("This profile has no accounts.");
                            continue;
                        }
                        break;
                    default: //Invalid Option
                        System.out.println("Invalid Option. Try again.");
                        break;
                }
            } while (account == null);

            printMenu = true;

            accountActionsLoop:
            do {
                if (printMenu) {
                    System.out.println("\nAccount Actions Menu");
                    System.out.println("---------");
                    System.out.println("0. Quit");
                    System.out.println("1. Deposit Money");
                    System.out.println("2. Withdraw Money");
                    System.out.println("3. Transfer Money");
                    System.out.println("4. Show Account Information");
                    printMenu = false;
                }

                System.out.println("\nWhat would you like to do?");
                System.out.print("Input: ");
                generalInput = input.getString();

                switch (Integer.parseInt(generalInput)) {
                    case quitVal: //Quit
                        System.out.println("\nGoodbye!");
                        break applicationLoop;
                    case 1: //Deposit Money
                        System.out.print("\nDeposit Amount: $");
                        accountFileManager.remove(account);
                        account.deposit(input.getDouble());
                        accountFileManager.add(account);
                        break;
                    case 2: //Withdraw Money
                        System.out.print("\nWithdrawal Amount: $");
                        accountFileManager.remove(account);
                        account.withdraw(input.getDouble());
                        accountFileManager.add(account);
                        break;
                    case 3: //Transfer Money
                        System.out.print("\nAccount Number of the Target Account: ");
                        Account tempTargetAccount = accountListManager.findAccount(input.getLong());
                        if (tempTargetAccount == null) break;
                        System.out.print("Transfer Amount: $");
                        accountFileManager.remove(account);
                        accountFileManager.remove(tempTargetAccount);
                        account.transfer(tempTargetAccount, input.getDouble());
                        accountFileManager.add(account);
                        accountFileManager.add(tempTargetAccount);
                        break;
                    case 4: //Show Account Information
                        account.showInfo();
                        break;
                    default: //Invalid Option
                        System.out.println("Invalid Option. Try again.");
                        break;
                }
            } while (Integer.parseInt(generalInput) != quitVal);
        } while (Integer.parseInt(generalInput) != quitVal);

        input.close();
    }
}