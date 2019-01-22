package company.com.myapplication.database.dao;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import company.com.myapplication.database.model.HistoryEntity;

@Dao
public interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(HistoryEntity historyEntity);

    @Update(onConflict = OnConflictStrategy.ABORT)
    void update(HistoryEntity historyEntity);

    @Delete
    void delete(HistoryEntity historyEntity);

    @Query("SELECT * FROM History_T WHERE username=:username ORDER BY restaurantName ASC")
    LiveData<List<HistoryEntity>> getAll(String username);

    @Query("SELECT * FROM History_T  WHERE id=:id")
    LiveData<HistoryEntity> getOne(Long id);

}
