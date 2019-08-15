package utilities;

import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar;  

public class Watch 
{
	private Date date;
	private DateFormat dateFormat;
	private SimpleDateFormat formatter;
	private String strDate;
	
	//return the date in a string
	public String getDate()
	{
		date = new Date();  
	    formatter = new SimpleDateFormat("MM/dd/yyyy");  
		strDate = formatter.format(date);  
		//System.out.println("Date: "+strDate);  
		return strDate;
	}
	
	//return the date and time in a string
	public String getDateTime()
	{
		date = Calendar.getInstance().getTime();  
        dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");  
        strDate = dateFormat.format(date);  
        //System.out.println("Date: " + strDate);
        return strDate;
	}
}
