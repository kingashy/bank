package account;

public class AccountManager
{
	//create a new AccountNumber class
	public AccountNumber createAccountNumber(int accountType, int accountOwnerSSN)
	{
		return new AccountNumber(accountType, accountOwnerSSN);
	}

	//create a new AccountPin class
	public Pin createAccountPin(int newPin)
	{
		return new Pin(newPin);
	}

	//create a new Checking account
	public Account createChecking(double initialDeposit, Pin pin, AccountNumber accountNumber, DebitCard debitCard)
	{
		return new Checking(initialDeposit, pin, accountNumber, debitCard);
	}

	//create a new Savings account
	public Account createSavings(double initialDeposit, Pin pin, AccountNumber accountNumber, SecurityBox securityBox)
	{
		return new Savings(initialDeposit, pin, accountNumber, securityBox);
	}

	public Account createAccountFromFile(String[] splitLine)
	{
		Account tempAccount;
		AccountNumber accountNumber = new AccountNumber(Long.parseLong(splitLine[2]));;
		Pin pin = new Pin(Integer.parseInt(splitLine[1]));
		DebitCard debitCard;
		SecurityBox securityBox;

		//create a Savings or Checking account based on the type of Account
		switch(splitLine[4])
		{
			case "Savings":
				securityBox = new SecurityBox(Integer.parseInt(splitLine[3]));
				tempAccount = createSavings(Double.parseDouble(splitLine[0]), pin, accountNumber, securityBox);
				break;
			case "Checking":
				debitCard = new DebitCard(Long.parseLong(splitLine[3]));
				tempAccount = createChecking(Double.parseDouble(splitLine[0]), pin, accountNumber, debitCard);
				break;
			default:
				System.out.println("Invalid Account Type");
				return null;
		}

		return tempAccount;
	}
}
