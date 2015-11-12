package videolibrary.street.quality.qualityshow.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Pierre on 12/11/2015.
 */
public class CalendarUtils {

    public static int getDayDiff(String strThatDay) {
        strThatDay = strThatDay.replace("Z", "+01:00");
        Calendar thatDay = Calendar.getInstance(TimeZone.getDefault(), Locale.FRANCE);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.FRANCE);
        try {
            Date date = dateformat.parse(strThatDay);
            thatDay.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Log.d("DayDiff", thatDay.toString());

        Calendar today = Calendar.getInstance();

        long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
        return (int) (diff / (24 * 60 * 60 * 1000));
    }
}
