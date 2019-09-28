package account;

public class Checking extends Account
{
	private DebitCard debitCard;

	public Checking(double initialDeposit, Pin pin, AccountNumber accountNumber, DebitCard debitCard)
	{
		super(initialDeposit, pin, accountNumber);
		this.debitCard = debitCard;
		accountType = "Checking";
	}

	@Override
	public void showInfo()
	{
		super.showInfo();
		debitCard.showInfo();
	}

	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();	
		sb.append(",").append(debitCard.getCardNumber()).append(",").append(accountType);
		return super.toString() + sb.toString();
	}
}