import java.util.Calendar;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;

public class DateTime {
    
    public enum Month { 
        January, February, March, April, 
        May, June, July, August, September, 
        October, November, December
    }
    
    public static Month getMonth() {
        int i = Calendar.getInstance().get(Calendar.MONTH);
        return Month.values()[i];
    }

    public static Date getDate() {
        java.util.Date myDate;
        try {
            myDate = DateFormat.getInstance().parse("10/10/2009");
            return new java.sql.Date(myDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        

    }

}
