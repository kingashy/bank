package utilities;

import java.util.Scanner;

public class Input 
{
	public static final Scanner sc = new Scanner(System.in);
	
	public Input()
	{
		
	}
	
	/*Note: Every input is a string converted to the specified type to avoid \n (new line) issues*/
	
	//get an Integer input
	public int getInt()
	{
		return (Integer.parseInt(sc.nextLine()));
	}
	
	//get a Long input
	public long getLong()
	{
		return (Long.parseLong(sc.nextLine()));
	}
	
	//get a Floating point input
	public double getFloat()
	{
		return(Float.parseFloat(sc.nextLine()));
	}
	
	//get a Double input
	public double getDouble()
	{
		return (Double.parseDouble(sc.nextLine()));
	}
	
	//get a String input
	public String getString()
	{
		return (sc.nextLine());
	}
	
	//close the stream
	public void close()
	{
		sc.close();
	}

}
