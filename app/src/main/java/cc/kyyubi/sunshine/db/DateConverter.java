package cc.kyyubi.sunshine.db;

import android.arch.persistence.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by U0162467 on 1/3/2018.
 */

public class DateConverter {

    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @TypeConverter
    public static Date toDate(String timestamp) {

        try {
            return timestamp == null ? null : df.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TypeConverter
    public static String toTimestamp(Date date) {
        return date == null ? null : df.format(date);
    }
}

