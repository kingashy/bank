package bank;

import utilities.Generate;

public class Checking extends Account
{
	private long debitCardNum;
	
	public Checking(String name, int ssn, double initialDeposit, int accountPin)
	{
		super(name, ssn, initialDeposit, accountPin);
		accountNum = super.generateAccountNum(1);
		debitCardNum = generateDebitCardNum();
	}
	
	//Getter
	public double getRate()
	{
		return(getBaseRate() * .15);
	}
	
	//generate a random 12-digit account number
	private long generateDebitCardNum()
	{
		return new Generate().random(12);
	}

	//override the showInfo() method from the super class
	//add specific information for the Checking class
	@Override
	protected void showInfo() 
	{
		StringBuilder sb = new StringBuilder();
		
		super.showInfo();
		sb.append("Debit Card Number: " + debitCardNum);
		System.out.println(sb.toString());
	}
	
}
