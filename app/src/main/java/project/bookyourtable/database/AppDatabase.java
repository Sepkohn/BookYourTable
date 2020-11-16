package project.bookyourtable.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

import project.bookyourtable.database.dao.BookingDao;
import project.bookyourtable.database.dao.TableDao;

import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.database.entity.TableEntity;

@Database(entities = {BookingEntity.class, TableEntity.class}, version = 7)
public abstract class AppDatabase extends RoomDatabase  {
    private static final String TAG = "AppDatabase";

    private static AppDatabase instance;

    private static final String DATABASE_NAME = "database";

    public abstract BookingDao bookingDao();
    public abstract TableDao tableDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = buildDatabase(context.getApplicationContext());
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    /**
     * Build the database. {@link RoomDatabase.Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext) {
        Log.i(TAG, "Database will be initialized.");

        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            AppDatabase database = AppDatabase.getInstance(appContext);
                            initializeDemoData(database);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                })
                .fallbackToDestructiveMigration()
                .build();
    }


    /**
     * Delete all datas in the database
     * @param database = Actual Database
     */
    public static void initializeDemoData(final AppDatabase database) {
        Executors.newSingleThreadExecutor().execute(() -> {
            database.runInTransaction(() -> {
                Log.i(TAG, "Wipe database.");
                database.bookingDao().deleteAll();
                database.tableDao().deleteAll();

                DatabaseInitializer.populateDatabase(database);
            });
        });
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            Log.i(TAG, "Database initialized.");
            setDatabaseCreated();
        }

    }


    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}