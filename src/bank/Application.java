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

		Account account;
		Profile profile;
		String generalInput = "";
		String name;

		System.out.println("\nWelcome to " + bankName + "!\n");
		System.out.print("Enter your name: ");
		name = input.getString().trim();

		profile = profileListManager.findProfile(name);

		applicationLoop: do
		{
			int pinAttempts = 0;

			if (profile != null)
			{
				System.out.println("\nYou will have 3 attempts to enter your pin.");

				pinAttemptLoop: do
				{
					System.out.print("Pin: ");

					if (profile.verifyPin(input.getInt()))
					{
						if (!profile.hasAccounts())
						{
						//if (!profile.hasAccounts()) System.out.println("\nYou have no existing accounts.");

							accountCreationLoop: do
							{
								System.out.println("\nOptions: ");
								System.out.println("0. Quit");
								System.out.println("1. Create an account");
								System.out.print("Input: ");
								generalInput = input.getString();

								switch (Integer.parseInt(generalInput))
								{
									case 1:
										System.out.print("\nInitial Deposit: $");
										double deposit = input.getDouble();

										System.out.print("Account Type: ");
										String accountType = input.getString();

										System.out.print("Social Security Number (must match your profile's SSN): ");
										int ssn = input.getInt();

										System.out.print("Pin (must match your profile's pin): ");
										Pin pin = new Pin(input.getInt());

										AccountNumber accountNumber;

										if (accountType == "Checking")
										{
											accountNumber = new AccountNumber(1, ssn);
											account = accountManager.createChecking(deposit, pin, accountNumber, new DebitCard());
										}
										else
										{
											accountNumber = new AccountNumber(2, ssn);
											account = accountManager.createSavings(deposit, pin, accountNumber, new SecurityBox());
										}

										accountListManager.addAccount(account);
										accountFileManager.addAccount(account);
										profileFileManager.removeProfile(profile);
										profile.addAccountNumber(accountNumber.getAccountNum());
										profileFileManager.addProfile(profile);

										System.out.println("\n" + accountType + " Account created!");
										break;
									case 2:
										System.out.println("Goodbye!");
										break applicationLoop;
									default:
										System.out.println("Invalid option. Try again.");
										break;
								}
							} while (Integer.parseInt(generalInput) != 2);

							break pinAttemptLoop;

						}

						accountLoop: do
						{
							String accountNumber;

							System.out.println("Which account are you working on?");
							profile.showAccounts();

							System.out.print("Account Number: ");
							accountNumber = input.getString();

							account = accountListManager.findAccount(Long.parseLong(accountNumber));

							if (account == null) continue;

							System.out.println("\nWhat would you like to do?");
							System.out.println("(Enter 8 to go to the Main Menu)");
							System.out.print("Input: ");
							generalInput = input.getString();

							switch (Integer.parseInt(generalInput))
							{
								case quitVal: //Quit
									System.out.println("\nGoodbye!");
									break applicationLoop;
								case 1: //Deposit Money
									System.out.print("\nDeposit Amount: $");
									account.deposit(input.getDouble());
									break;
								case 2: //Withdraw Money
									System.out.print("\nWithdrawal Amount: $");
									account.withdraw(input.getDouble());
									break;
								case 3: //Transfer Money
									System.out.print("\nAccount Number of the Target Account: ");
									Account tempTargetAccount = accountListManager.findAccount(input.getLong());
									if (tempTargetAccount == null) break;
									System.out.print("Transfer Amount: $");
									account.transfer(tempTargetAccount, input.getDouble());
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
									accountFileManager.removeAccount(account);
									accountListManager.removeAccount(account);
									account = null;

									System.out.println("\nAccount deleted!\nPlease restart the application.");
									break;
								case 8: //Print Main Menu Options
									System.out.println("\nMain Menu");
									System.out.println("---------");
									System.out.println("0. Quit");
									System.out.println("1. Deposit Money");
									System.out.println("2. Withdraw Money");
									System.out.println("3. Transfer Money");
									System.out.println("4. Generate a New Pin");
									System.out.println("5. Enter a New Pin");
									System.out.println("6. Show Your Account Information");
									System.out.println("7. Delete your account");
									break;
								default: //Invalid Option
									System.out.println("Invalid Option. Try again.");
									break;
							}
						} while (Integer.parseInt(generalInput) != quitVal);
					} else pinAttempts++;
					if (pinAttempts == 3)
						System.out.println("\nYou ran out of attempts. Please try again later. Goodbye!");
				} while (pinAttempts <= 2);
			}
			else
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
		} while (Integer.parseInt(generalInput) != quitVal);

		input.close();
	}
}
