package bank;

import utilities.Generate;
import utilities.Watch;

public abstract class Account implements Rate
{
	protected String name;
	protected int ssn;
	protected String dateOfCreation;
	protected long accountNum;
	protected double balance;
	protected double rate;
	protected int accountPin;
	
	public Account(String name, int ssn, double initialDeposit, int accountPin)
	{
		Watch watch = new Watch();
		
		this.name = name;
		this.ssn = ssn;
		this.balance = initialDeposit;
		
		this.accountPin = accountPin;
		
		dateOfCreation = watch.getDateTime();
		//System.out.println(dateOfCreation);
		
		rate = getRate();
	}
	
	//get the rate
	//implementation will be dependent on the type of account
	public abstract double getRate();
	
	//Getters
	public double getBalance()
	{
		return balance;
	}
	
	public long getAccountNum()
	{
		return accountNum;
	}
	
	public int getPin()
	{
		return accountPin;
	}
	
	public String getName()
	{
		return name;
	}
	
	//Setters
	//attempt to set a 4-digit pin to the specified account
	public void setPin(int accountPin)
	{
		if (!isValidPinWidth(accountPin)) return;
	
		this.accountPin = accountPin;
		System.out.println("New Pin Set");
	}
	
	public void setBalance(double balance)
	{
		this.balance = balance;
	}
	
	//check if the pin entered is 4 digits
	public boolean isValidPinWidth(int accountPin)
	{
		if ((accountPin > 9999) || (accountPin < 0))
		{
			System.out.println("Failure: Invalid pin.");
			return false;
		}
		else return true;
	}
	
	//check if the amount requested to withdrawn can be withdrawn from the specified account
	public boolean isValidWithdrawal(double amount)
	{
		if (balance - amount < 0) 
		{
			System.out.println("Failure: Account " + accountNum + " does not have sufficient funds.");
			return false;
		}
		else return true;
	}
	
	//check if the pin attempted is valid
	public boolean checkPinAttempt(int pinAttempt)
	{
		if (pinAttempt == accountPin)
		{
			//System.out.println("\nValid Pin Entry");
			return true;
		}
		else
		{
			System.out.println("Invalid Pin Entry");
			return false;
		}
	}
	
	//generate the 11-digit account number
	//1 or 2 depending on Savings or Checking, last two digits of SSN, unique 5-digit number
	//and random 3-digit number
	protected long generateAccountNum(int accountType)
	{
		Generate generate = new Generate();
		long tempAccountNum = (long)(accountType * Math.pow(10, 10));
		tempAccountNum += (ssn % 100) * Math.pow(10, 8);
		tempAccountNum += generate.random(5) * Math.pow(10, 3);
		tempAccountNum += generate.random(3);
		return tempAccountNum;
	}
	
	//generate a 4 digit pin
	public void generatePin()
	{
		accountPin = (int)(new Generate().random(4));
		System.out.println("New Generated Pin: " + accountPin);
	}
	
	//attempt to unlock the specified account with the specified 4-digit pin
	public boolean unlock(int pinAttempt)
	{
		
		if (!checkPinAttempt(pinAttempt))
		{
			//System.out.println("Account: Locked");
			//System.out.println("Failure: Incorrect Pin");
			return false;
		}
		else
		{
			System.out.println("Account " + getAccountNum() + ": Unlocked");
			return true;
		}
	}
	
	//lock the account
	public void lock()
	{
		System.out.println("Account " + accountNum + ": Locked");
	}
	
	//show everything about the account
	protected void showInfo()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("\nName: " + name);
		sb.append("\nSSN: " + ssn);
		sb.append("\nBalance: $" + balance);
		sb.append("\nRate: " + rate + "%");
		sb.append("\nAccount Number: " + accountNum);
		sb.append("\nAccount Pin Number: " + accountPin);
		sb.append("\nDate of Account Creation: " + dateOfCreation);
		
		System.out.println(sb.toString());
	}
}
