package ch.hpdy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hans-Peter Schmid on 10.06.2019.
        */
public class TimeUtil {
    public static String getCurrentDateTimeFileNameString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
