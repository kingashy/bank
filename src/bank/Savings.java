package bank;

import utilities.Generate;

public class Savings extends Account
{
	private int securityBoxNum;
	
	public Savings(String name, int ssn, double initialDeposit, int accountPin)
	{
		super(name, ssn, initialDeposit, accountPin);
		accountNum = super.generateAccountNum(2);
		securityBoxNum = generateSecurityBoxNum();
	}
	
	public double getRate()
	{
		return(getBaseRate() - 0.25);
	}
	
	//generate a random security box number
	private int generateSecurityBoxNum()
	{
		return (int)(new Generate().random(3));
	}
	
	//override the showInfo() method from the super class
	//add specific information for the Checking class
	@Override
	protected void showInfo() 
	{
		StringBuilder sb = new StringBuilder();
		
		super.showInfo();
		sb.append("Security Box Number: " + securityBoxNum);
		System.out.println(sb.toString());
	}
}
