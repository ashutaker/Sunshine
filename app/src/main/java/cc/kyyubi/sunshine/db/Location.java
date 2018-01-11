package cc.kyyubi.sunshine.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by U0162467 on 12/30/2017.
 */
@Entity
public class Location {
    @PrimaryKey
    @NonNull
    public int id;

    public String location_setting;

    public String city_name;

    public long coord_lat;

    public long coord_long;

}
