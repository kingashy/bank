package bank;

import java.util.Iterator;
import java.util.LinkedList;

public class AccountSearch 
{
	public AccountSearch()
	{
		
	}
	
	//find an account in the linked list based off a name
	public Account find(LinkedList<Account> list, String name)
	{
		Account tempAccount;
		
		for (Iterator<Account> i = list.iterator(); i.hasNext();)
		{
			tempAccount = i.next();
			tempAccount.showInfo();

			if (name.equals(tempAccount.getName()))
			{
				//System.out.println("Account Found");
				return tempAccount;
			}
		}
		System.out.println("Account Not Found");
		return null;
	}
	
	//find an account in the linked list based off the account number
	public Account find(LinkedList<Account> list, long accountNumber)
	{
		Account tempAccount;
		
		for (Iterator<Account> i = list.iterator(); i.hasNext();)
		{
			tempAccount = i.next();
			//tempAccount.showInfo();
			//System.out.println(tempAccount.getAccountNum());

			if (accountNumber == tempAccount.getAccountNum())
			{
				//System.out.println("Account Found");
				return tempAccount;
			}
		}
		System.out.println("Account Not Found");
		return null;
	}
}
