package account;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import file.FileManager;

public class AccountFileManager 
{
	private static String delimeter = ",";

	private FileManager fileManager;
	private String fileName;

	public AccountFileManager(FileManager fileManager, String fileName)
	{
		this.fileManager = fileManager;
		this.fileName = fileName;
	}

	public String getFileName()
	{
		return fileName;
	}

	//add an account to the accounts.txt file
	public void addAccount(Account account)
	{
		try 
		{
			//open the buffer in append mode
			BufferedWriter buffW = new BufferedWriter(new FileWriter(fileName, true));		
			
			buffW.write((account.toString()).trim());
			buffW.newLine();		
			
			buffW.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	//remove an account to the accounts.txt file
	public void removeAccount(Account account)
	{
		fileManager.removeLineFromFile(fileName, account.toString());
	}
	
	//generate accounts from the data stored in the accounts.txt file
	/*private void parseAccountsFile()
	{
		String line;
		Account tempAccount;		
		
		//keep reading each line until the line is null (EOF)
		try 
		{
			BufferedReader buffR = new BufferedReader(new FileReader(fileName));
				
			while ((line = buffR.readLine()) != null)
			{
				//skip empty lines
				if (line.length() == 0) continue;
				
				//split each line into 4 parts
				//Line Layout: Name, SSN, Type of Account, Initial Deposit, Initial Pin
				String splitLine[] = line.split(delimeter);

				//create a Savings or Checking account based on the type of Account
				switch(splitLine[4])
				{
					AccountNumber accountNumber;
					Pin pin = new Pin(Integer.parseInt(splitLine[3]));

					case "Savings":
						accountNumber = new AccountNumber(2, Integer.parseInt(splitLine[1]));
						tempAccount = accountManager.createSavingsAccount(Double.parseDouble(splitLine[2]), pin, accountNumber);
						break;
					case "Checking":
						accountNumber = new AccountNumber(1, Integer.parseInt(splitLine[1]));
						tempAccount = accountManager.createCheckingAccount(Double.parseDouble(splitLine[2]), pin, accountNumber)
						break;
					default:
						System.out.println("Invalid Account Type");
						return;
				}
			
				accountManager.addAccountToList(tempAccount);
			}
			
			buffR.close();
		} 
		catch (NumberFormatException | IOException e) 
		{
			e.printStackTrace();
		}
	}*/

	/*
	public String[] parseLine(BufferedReader buffR)
	{
		String line;
		String splitLine[] = null;

		//keep reading each line until the line is null (EOF)
		try
		{
			//BufferedReader buffR = new BufferedReader(new FileReader(fileName));

			while ((line = buffR.readLine()) != null)
			{
				//skip empty lines
				if (line.length() == 0) continue;

				//split each line into 4 parts
				//Line Layout: Name, SSN, Type of Account, Initial Deposit, Initial Pin
				splitLine = line.split(delimeter);
			}

			buffR.close();
			return splitLine;
		}
		catch (NumberFormatException | IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public String[][] parseFile()
	{
		String line;
		String splitLine[] = null;
		String splitFile[][] = null;
		int count = 0;

		//keep reading each line until the line is null (EOF)
		try
		{
			BufferedReader buffR = new BufferedReader(new FileReader(fileName));

			while ((line = buffR.readLine()) != null)
			{
				//skip empty lines
				if (line.length() == 0) continue;

				//split each line into 4 parts
				//Line Layout: Name, SSN, Type of Account, Initial Deposit, Initial Pin
				splitLine = line.split(delimeter);
				splitFile[count] = splitLine;
				count++;
			}

			buffR.close();
			return splitFile;
		}
		catch (NumberFormatException | IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}*/

	public String[] parseLine(BufferedReader buffR)
	{
		return fileManager.parseLine(buffR);
	}
}
