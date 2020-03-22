package bank;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import input.Input;
import account.*;
import profile.*;

public class Application {
    public static void main(String[] args) {
        final String accountsFileName = "accounts.txt";
        final String profilesFileName = "profiles.txt";
        final String bankName = "Big Bank";
        final int quitVal = 0;

        HashMap<Integer, Profile> profileList = new HashMap<>();
        HashMap<Long, Account> accountList = new HashMap<>();

        Input input = new Input(new Scanner(System.in));
        ProfileListManager profileListManager = new ProfileListManager(profileList);
        ProfileFileManager profileFileManager = new ProfileFileManager(profileListManager, profilesFileName);
        AccountListManager accountListManager = new AccountListManager(accountList);
        AccountFileManager accountFileManager = new AccountFileManager(accountListManager, accountsFileName);

        Account account = null;
        String generalInput = "";
        boolean printMenu = true;

        System.out.println("\nWelcome to " + bankName + "!\n");

        System.out.print("Enter your name: ");
        String name = input.getString().trim();
        System.out.print("Enter your Social Security Number (SSN): ");
        int ssn = input.getInt();
        Profile profile = profileListManager.find(ssn, name);
        String accountNumberStr = "";

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
                        profile = new Profile(name, ssn, new LinkedList<>());
                        profileListManager.add(ssn, profile);
                        profileFileManager.addProfile(profile);

                        System.out.println("\nProfile created! Your name and ssn were used.");
                        System.out.println("This application will now restart.");
                        continue applicationLoop;
                    default: //Invalid Option
                        System.out.println("Invalid Option. Try again.");
                        break;
                }
            }

            profileActionsLoop:
            do {
                if (printMenu) {
                    System.out.println("\nProfile Actions Menu");
                    System.out.println("---------");
                    System.out.println("0. Quit");
                    System.out.println("1. Remove profile");
                    System.out.println("2. Add an account");
                    System.out.println("3. Manage an account");
                    printMenu = false;
                }

                System.out.println("\nWhat would you like to do?");
                System.out.print("Input: ");
                generalInput = input.getString();

                switch (Integer.parseInt(generalInput)) {
                    case quitVal: //Quit
                        System.out.println("\nGoodbye!");
                        break applicationLoop;
                    case 1: //Remove a profile
                        accountFileManager.removeAccounts(profile);
                        accountListManager.removeAccounts(profile);

                        profileFileManager.removeProfile(profile);
                        profileListManager.remove(ssn);

                        System.out.println("Profile Removed. Goodbye!");
                        break applicationLoop;
                    case 2: //Create an account
                        System.out.print("\nInitial Deposit: $");
                        double deposit = input.getDouble();
                        if (deposit <= 0 || deposit > 10000) continue;

                        System.out.print("Account Type (Enter 1 for Checking, 2 for Savings): ");
                        int accountType = input.getInt();
                        if (accountType < 1 || accountType > 2) continue;

                        System.out.print("Re-enter your SSN: ");
                        ssn = input.getInt();
                        if (!profile.verifySSN(ssn)) continue;

                        System.out.print("Pin: ");
                        Pin pin = new Pin(input.getInt());
                        if (pin.verifyPin(0)) continue;

                        AccountNumber accountNumber = null;

                        switch (accountType) {
                            case 1:
                                accountNumber = new AccountNumber(1, ssn);
                                account = new Checking(deposit, pin, accountNumber, new DebitCard());
                                break;
                            case 2:
                                accountNumber = new AccountNumber(2, ssn);
                                account = new Savings(deposit, pin, accountNumber, new SecurityBox());
                                break;
                            default:
                                System.out.println("Invalid Account Type. Please try again.");
                                continue;
                        }

                        accountListManager.add(accountNumber.getAccountNum(), account);
                        accountFileManager.add(account);
                        profileFileManager.removeProfile(profile);
                        profile.addAccountNumber(accountNumber.getAccountNum());
                        profileFileManager.addProfile(profile);

                        System.out.println("Account created!");
                        break;
                    case 3: //Manage an account
                        if (profile.hasAccounts()) {
                            System.out.println("\nWhich account are you working on?");
                            profile.showAccounts();

                            System.out.println("Account Number: ");
                            accountNumberStr = input.getString().trim();

                            account = accountListManager.find(Long.parseLong(accountNumberStr));
                            if (account == null) continue;

                            System.out.println("\nYou will have 3 attempts to enter your pin.");

                            pinAttemptLoop:
                            do {
                                System.out.print("Pin: ");

                                if (account.verifyPin(input.getInt())) break pinAttemptLoop;
                                else pinAttempts++;

                                if (pinAttempts == 3) {
                                    System.out.println("\nYou ran out of attempts. Please try again later. Goodbye!");
                                    break applicationLoop;
                                }
                            } while (pinAttempts <= 2);
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
                    System.out.println("4. New Pin");
                    System.out.println("5. Show Account Information");
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
                        Account tempTargetAccount = accountListManager.find(input.getLong());
                        if (tempTargetAccount == null) break;
                        System.out.print("Transfer Amount: $");
                        accountFileManager.remove(account);
                        accountFileManager.remove(tempTargetAccount);
                        account.transfer(tempTargetAccount, input.getDouble());
                        accountFileManager.add(account);
                        accountFileManager.add(tempTargetAccount);
                        break;
                    case 4://Enter a New Pin
                        System.out.print("\nNew Pin: ");
                        int updatedPin = input.getInt();

                        if (account.isValidPinWidth(updatedPin)) {
                            profileFileManager.removeProfile(profile);
                            account.updatePin(updatedPin);
                            profileFileManager.addProfile(profile);
                        }
                        break;
                    case 5: //Delete this account
                        accountFileManager.remove(account);
                        accountListManager.remove(Long.parseLong(accountNumberStr));
                        account = null;

                        System.out.println("\nAccount deleted!\nPlease restart the application.");
                        break;
                    case 6: //Show Account Information
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