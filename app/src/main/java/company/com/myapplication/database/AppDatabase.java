package company.com.myapplication.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import company.com.myapplication.database.dao.HistoryDao;
import company.com.myapplication.database.model.HistoryEntity;

@Database(entities = {HistoryEntity.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract HistoryDao getHistoryDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "history_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
