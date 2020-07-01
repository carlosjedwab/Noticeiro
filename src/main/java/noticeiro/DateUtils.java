package noticeiro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	static SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat stformat = new SimpleDateFormat("HH:mm");
	
	static String noFilter = "0";
	
	public static Date buildDateByDate(String yyyy_MM_dd) throws ParseException {
		Date date;
		if ((date = sdformat.parse(yyyy_MM_dd)) != null)
			return date;
		else
			return null;
	}
	
	public static Date buildDateByTime(String HH_mm) throws ParseException {
		Date date;
		if ((date = stformat.parse(HH_mm)) != null)
			return date;
		else
			return null;
	}
	
	public static boolean sameDateInterval(Date referenceDate, String minDate, String maxDate) throws ParseException {
		boolean sameInterval = true;
		
		if (!minDate.equals(noFilter)) {
			if (referenceDate.compareTo(buildDateByDate(minDate)) < 0)
				sameInterval = false;
		}
		if (!maxDate.equals(noFilter)) {
			if (referenceDate.compareTo(buildDateByDate(maxDate)) > 0)
				sameInterval = false;
		}
		
		return sameInterval;
	}
	
	public static boolean sameTimeInterval(Date referenceDate, String minTime, String maxTime) throws ParseException {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(referenceDate);
	    
	    if (minTime.equals(noFilter))
	    	minTime = "00:00";
	    if (maxTime.equals(noFilter))
	    	maxTime = "23:59";
	    
	    Calendar min = getTimeCalendar(cal, minTime);
	    Calendar max = getTimeCalendar(cal, maxTime);

	    if (cal.getTimeInMillis() >= min.getTimeInMillis() && cal.getTimeInMillis() <= max.getTimeInMillis())
	    	return true;
	    return false;
	}
	
	private static Calendar getTimeCalendar(Calendar ref, String time) throws ParseException {
		Calendar cal = Calendar.getInstance();
	    cal.setTime(buildDateByTime(time));
	    cal.set(Calendar.DAY_OF_MONTH, ref.get(Calendar.DAY_OF_MONTH));
	    cal.set(Calendar.MONTH, ref.get(Calendar.MONTH));
	    cal.set(Calendar.YEAR, ref.get(Calendar.YEAR));
	    
	    return cal;
	}
}
