package cc.kyyubi.sunshine.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by U0162467 on 12/30/2017.
 */

@Entity(foreignKeys = {
        @ForeignKey(entity = Location.class,
                parentColumns = "id",
                childColumns = "location_id")})
@TypeConverters(DateConverter.class)
public class Weather {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    public int location_id;

    public Date date;

    public int weather_id;

    public String description;

    public float min_temp;

    public float max_temp;

    public float humidity;

    public float pressure;

    public float wind;

    public float degrees;
}
