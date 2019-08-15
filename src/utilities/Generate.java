package utilities;

import java.util.Random;

public class Generate 
{
	//generate a random number of n length
	public long random(int length)
	{
		Random random = new Random();
	    char[] digits = new char[length];
	    digits[0] = (char) (random.nextInt(9) + '1');
	    for (int i = 1; i < length; i++) {
	        digits[i] = (char) (random.nextInt(10) + '0');
	    }
	    return Long.parseLong(new String(digits));
	}
	
	//generate a random number of n length, starting with a specific digit
	public long random(int length, int startNum)
	{
		Random random = new Random();
	    char[] digits = new char[length];
	    digits[0] = (char) (startNum);
	    for (int i = 1; i < length; i++) {
	        digits[i] = (char) (random.nextInt(10) + '0');
	    }
	    return Long.parseLong(new String(digits));
	}
	
}
