package bank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class AccountSetup 
{
	private String delimeter = ",";
	
	public AccountSetup()
	{
		
	}
	
	/*CSV File Layout
	 *Line: Name, SSN, Type of Account, Initial Deposit, Old Pin*/
	
	public void parseAccountsFile(String fileName, LinkedList<Account> list)
	{
		//search for fileName and import it into a file
		File file = new File(fileName);
		
		//exception handler
		try 
		{
			FileReader fileR = new FileReader(file);
			BufferedReader buffR = new BufferedReader(fileR); //needed to read line by line
			String line;
			
			//keep reading each line until the line is null (EOF)
			while ((line = buffR.readLine()) != null)
			{
				//System.out.println(line);
				
				//split each line into 4 parts
				String splitLine[] = line.split(delimeter);
				
				/*System.out.print("\n");
				for (int i = 0; i < splitLine.length; i++)
				{
					System.out.println("Split " + i + ": " + splitLine[i]);
				}*/

				//based of the type of account, create a Savings or Checking account and add it to the list
				switch(splitLine[2])
				{
				case "Savings":
					list.add(new Savings(splitLine[0], Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[3]), Integer.parseInt(splitLine[4])));
					break;
				case "Checking":
					list.add(new Checking(splitLine[0], Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[3]), Integer.parseInt(splitLine[4])));
					break;
				default:
					System.out.println("Invalid Account Type");
					break;
				}
			}
			
			//close all file streams
			buffR.close();
			fileR.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File not found: " + file.toString());
		} 
		catch (IOException e) 
		{
			System.out.println("Unable to read file: " + file.toString());
		}
	}
}
