package bank;

import utilities.Input;
import java.util.LinkedList;

public class Application 
{
	public static void main(String[] args) 
	{
		Input input = new Input();
		LinkedList<Account> accountList = new LinkedList<Account>();
		AccountTransactions accountTransactions = new AccountTransactions();
		AccountSetup accountSetup = new AccountSetup();
		AccountSearch accountSearch = new AccountSearch();
		Account tempAccount;
		
		accountSetup.parseAccountsFile("accounts.txt", accountList);
		
		System.out.print("Enter your name: ");
		tempAccount = accountSearch.find(accountList, input.getString());
		
		if (tempAccount != null)
		{
			int pinAttempts = 1;
			System.out.println("\nYou will have 3 attemps to enter your pin.");
			
			pinAttemptLoop: do
			{
				System.out.print("\nPin Entry Attempt " + pinAttempts + ": ");
				
				if (tempAccount.unlock(input.getInt()))
				{
					int choice;
					
					do
					{
						System.out.println("\nWhat would you like to do?");
						System.out.println("(Enter 0 to go to the Main Menu)");
						System.out.print("Input: ");
						choice = input.getInt();
						
						switch(choice)
						{
							case 0: //Print Main Menu Options
								System.out.println("\nMain Menu");
								System.out.println("---------");
								System.out.println("1. Deposit Money");
								System.out.println("2. Withdraw Money");
								System.out.println("3. Transfer Money");
								System.out.println("4. Generate a New Pin");
								System.out.println("5. Enter a New Pin");
								System.out.println("6. Show Your Account Information");
								System.out.println("7. Quit");
								break;
							case 1: //Deposit Money
								System.out.print("\nDeposit Amount: ");
								accountTransactions.deposit(tempAccount, input.getDouble());
								break;
							case 2: //Withdraw Money
								System.out.print("\nWithdrawal Amount: ");
								accountTransactions.withdraw(tempAccount, input.getDouble());
								break;
							case 3: //Transfer Money
								System.out.print("\nAccount Number of the Target Account: ");
								Account tempTargetAccount = accountSearch.find(accountList, input.getLong());
								if (tempTargetAccount == null) break;
								System.out.print("Transfer Amount: ");
								accountTransactions.transfer(tempAccount, tempTargetAccount, input.getDouble());
								break;
							case 4: //Generate a New Pin
								tempAccount.generatePin();
								break;
							case 5: //Enter a New Pin
								System.out.print("\nNew Pin: ");
								tempAccount.setPin(input.getInt());
								break;
							case 6: //Show Account Information
								tempAccount.showInfo();
								break;
							case 7: //Quit
								System.out.println("\nGoodbye!");
								break pinAttemptLoop;
							default: //Invalid Option
								System.out.println("Invalid Option. Try again.");
								break;
						}
					} while (choice != 7);
				}
				else pinAttempts++;
				if (pinAttempts == 4) System.out.println("\nYou ran out of attemps. Please try again later. Goodbye!");
			} while (pinAttempts <= 3);
		}
	}
}
