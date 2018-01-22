package by.troyan.web.support;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * DateParser class. Convert dates to necessary format using method parse(String date).
 */

public class DateParser {

    /**
     * Used to convert string to necessary format.
     *
     * @param  date
     *        string data
     * @return object of java.sql.Date
     */
    public static Date parse(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return new java.sql.Date(df.parse(date).getTime());
    }
}
