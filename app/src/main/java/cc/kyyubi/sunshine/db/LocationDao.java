package cc.kyyubi.sunshine.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by U0162467 on 12/30/2017.
 */
@Dao
public interface LocationDao {

    @Insert(onConflict = IGNORE)
    void insertLocation(Location location);

    @Query("DELETE from Location")
    void deleteALL();
}
