package bank;

public interface Rate 
{
	//return the base rate to classes implementing the Rate interface
	default double getBaseRate()
	{
		return 2.5;
	}
}
