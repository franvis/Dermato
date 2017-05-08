package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Francisco Visintini
 */
public class DateTimeUtils {

    public static String convertDateToString(Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        return df.format(date);
    }

}
