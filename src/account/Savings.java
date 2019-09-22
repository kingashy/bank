package account;

public class Savings extends Account
{
	private SecurityBox securityBox;

	public Savings(double initialDeposit, Pin pin, AccountNumber accountNumber, SecurityBox securityBox)
	{
		super(initialDeposit, pin, accountNumber);
		this.securityBox = securityBox;
	}
	
	//override the showInfo() method from the super class
	@Override
	protected void showInfo() 
	{
		super.showInfo();
		securityBox.showInfo();
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append(",").append(securityBox.getBoxNumber()).append(",").append("Savings");
		return super.toString() + sb.toString();
	}
}
