package bank;

import java.util.LinkedList;
import java.util.Scanner;

import file.FileManager;
import input.Input;
import account.*;
import profile.*;
import person.*;

public class Application
{
	public static void main(String[] args)
	{
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
		Profile profile = null;
		String generalInput = "";
		String name;

		System.out.println("\nWelcome to " + bankName + "!\n");
		System.out.print("Enter your name: ");
		name = input.getString().trim();

		profile = profileListManager.findProfile(name);

		applicationLoop: do
		{
			int pinAttempts = 0;

			if (profile == null)
			{
				System.out.println("\nOptions: ");
				System.out.println("0. Quit");
				System.out.println("1. Create an profile");
				System.out.print("Input: ");
				generalInput = input.getString();

				switch (Integer.parseInt(generalInput))
				{
					case quitVal: //Quit
						System.out.println("\nGoodbye!");
						break applicationLoop;
					case 1: //Create Bank Profile
						System.out.print("\nSocial Security Number: ");
						int tempSSN = input.getInt();

						System.out.print("Pin: ");
						Pin pin = new Pin(input.getInt());

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

			//if (profile != null)
			//{
			System.out.println("\nYou will have 3 attempts to enter your pin.");

			pinAttemptLoop: do
			{
				System.out.print("Pin: ");

				if (profile.verifyPin(input.getInt())) break pinAttemptLoop;
				else pinAttempts++;

				if (pinAttempts == 3)
				{
					System.out.println("\nYou ran out of attempts. Please try again later. Goodbye!");
					break applicationLoop;
				}
			} while (pinAttempts <= 2);

			//if (!profile.hasAccounts())
			//{
			if (!profile.hasAccounts()) System.out.println("\nYou have no existing accounts.");

			//break pinAttemptLoop;
			//}

			accountSelectionLoop: do
			{
				if (profile.hasAccounts())
				{
					String accountNumberStr;

					System.out.println("\nWhich account are you working on?");
					profile.showAccounts();

					System.out.print("\nAccount Number: ");
					accountNumberStr = input.getString().trim();

					account = accountListManager.findAccount(Long.parseLong(accountNumberStr));

					if (account == null) continue;
				}
				else
				{
					System.out.println("\nOptions: ");
					System.out.println("0. Quit");
					System.out.println("1. Create an account");
					System.out.print("Input: ");
					generalInput = input.getString();

					switch (Integer.parseInt(generalInput))
					{
						case quitVal:
							break accountSelectionLoop;
						case 1:
							generalInput = "8";
							break accountSelectionLoop;
						default:
							System.out.println("Invalid Option. Try again.");
					}
				}
			} while (account == null);

			accountActionsLoop: do
			{
				if (profile.hasAccounts())
				{
					System.out.println("\nWhat would you like to do?");
					System.out.println("(Enter 9 to go to the Main Menu)");
					System.out.print("Input: ");
					generalInput = input.getString();
				}

				switch (Integer.parseInt(generalInput))
				{
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
					case 4: //Generate a New Pin
						account.generatePin();
						break;
					case 5: //Enter a New Pin
						System.out.print("\nNew Pin: ");
						account.updatePin(input.getInt());
						break;
					case 6: //Show Account Information
						account.showInfo();
						break;
					case 7: //Delete an account
						accountFileManager.remove(account);
						accountListManager.removeAccount(account);
						account = null;

						System.out.println("\nAccount deleted!\nPlease restart the application.");
						break;
					case 8: //Create an account
						System.out.print("\nInitial Deposit: $");
						double deposit = input.getDouble();

						System.out.print("Account Type (Enter 1 for Checking, 2 for Savings): ");
						int accountType = input.getInt();

						System.out.print("Social Security Number (must match your profile's SSN): ");
						int ssn = input.getInt();

						System.out.print("Pin (must match your profile's pin): ");
						Pin pin = new Pin(input.getInt());

						AccountNumber accountNumber = null;

						if (accountType == 1)
						{
							accountNumber = new AccountNumber(1, ssn);
							account = accountManager.createChecking(deposit, pin, accountNumber, new DebitCard());
						}
						else if (accountType == 2)
						{
							accountNumber = new AccountNumber(2, ssn);
							account = accountManager.createSavings(deposit, pin, accountNumber, new SecurityBox());
						}

						accountListManager.addAccount(account);
						accountFileManager.add(account);
						profileFileManager.removeProfile(profile);
						profile.addAccountNumber(accountNumber.getAccountNum());
						profileFileManager.addProfile(profile);

						System.out.println( "Account created!");
						break;
					case 9: //Print Main Menu Options
						System.out.println("\nMain Menu");
						System.out.println("---------");
						System.out.println("0. Quit");
						System.out.println("1. Deposit Money");
						System.out.println("2. Withdraw Money");
						System.out.println("3. Transfer Money");
						System.out.println("4. Generate a New Pin");
						System.out.println("5. Enter a New Pin");
						System.out.println("6. Show Your Account Information");
						System.out.println("7. Delete an account");
						System.out.println("8. Add an account");
						break;
					default: //Invalid Option
						System.out.println("Invalid Option. Try again.");
						break;
				}
			} while (Integer.parseInt(generalInput) != quitVal);
			//} else pinAttempts++;
			//if (pinAttempts == 3)
			//	System.out.println("\nYou ran out of attempts. Please try again later. Goodbye!");
			//} while (pinAttempts <= 2);
			//}
		} while (Integer.parseInt(generalInput) != quitVal);

		input.close();
	}
}
