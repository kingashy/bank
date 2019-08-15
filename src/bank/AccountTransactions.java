package bank;

import bank.Account;

public class AccountTransactions
{
	
	public AccountTransactions()
	{

	}
	
	//print the type of transaction being made
	private void printTranscation(Account account, double amount, String transaction)
	{
		System.out.println("Account Number: " + account.getAccountNum());
		System.out.println(transaction + ": $" + amount);
		//System.out.println("New Balance: $" + account.getBalance());
	}
	
	//print the type of request being made
	private void printRequest(String request)
	{
		System.out.println("\n" + request + " Request Recieved");
	}
	
	//deposit the specified amount of money  into the specified account
	public void deposit(Account targetAccount, double amount)
	{
		printRequest("Deposit");
		targetAccount.setBalance(targetAccount.getBalance() + amount);
		printTranscation(targetAccount, amount, "Deposited");
	}
	
	//attempt to withdraw the specified amount of money from the source account
	public void withdraw(Account sourceAccount, double amount)
	{	
		printRequest("Withdraw");
		if (!sourceAccount.isValidWithdrawal(amount)) return;
		
		sourceAccount.setBalance(sourceAccount.getBalance() - amount);
		printTranscation(sourceAccount, amount, "Withdrawn");
	}
	
	//attempt to transfer the specified amount of money from the source account
	//to the target account
	public void transfer(Account sourceAccount, Account targetAccount, double amount)
	{
		printRequest("Transfer");
		if (!sourceAccount.isValidWithdrawal(amount)) return;
		
		withdraw(sourceAccount, amount);
		deposit(targetAccount, amount);
	}
	
	//calculate the accrued interest and add it to the account's balance
	public void accrueInterest(Account targetAccount)
	{
		printRequest("Accrue Interest");
		double accruedInterest = (targetAccount.getBalance() * (targetAccount.getRate()/100.0));
		targetAccount.setBalance(targetAccount.getBalance() + accruedInterest);
		printTranscation(targetAccount, accruedInterest, "Accrued Interest");
	}
}
