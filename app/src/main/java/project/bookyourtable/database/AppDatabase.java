package project.bookyourtable.database;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import project.bookyourtable.database.dao.BookingDao;
import project.bookyourtable.database.dao.TableDao;
import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.database.entity.TableEntity;

@Database(entities = {BookingEntity.class, TableEntity.class}, version = 1)
public abstract class AppDatabase {
    private static final String TAG = "AppDatabase";

    private static AppDatabase instance;

    private static final String DATABASE_NAME = "bank-database";

    public abstract BookingDao bookingDao();

    public abstract TableDao TableDao();

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
        return Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();


//        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
////                .addCallback(new RoomDatabase.Callback() {
////                    @Override
////                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
////                        super.onCreate(db);
////                        Executors.newSingleThreadExecutor().execute(() -> {
////                            AppDatabase database = AppDatabase.getInstance(appContext);
////                            //initializeDemoData(database);
////                            // notify that the database was created and it's ready to be used
////                            database.setDatabaseCreated();
////                        });
////                    }
////                }).build();

    }

//    public static void initializeDemoData(final AppDatabase database) {
//        Executors.newSingleThreadExecutor().execute(() -> {
//            database.runInTransaction(() -> {
//                Log.i(TAG, "Wipe database.");
//                database.bookingDao().deleteAll();
//                database.tableDao().deleteAll();
//
//                DatabaseInitializer.populateDatabase(database);
//            });
//        });
//    }

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


//    public BookingDao bookingDao(){
//
//        return new BookingDao() {
//            @Override
//            public LiveData<List<BookingEntity>> getAllBookings() {
//                return null;
//            }
//
//            @Override
//            public LiveData<BookingEntity> getBookingsByDate(Date date) {
//                return null;
//            }
//
//            @Override
//            public LiveData<BookingEntity> getBookingsById(int id) {
//                return null;
//            }
//
//            @Override
//            public void insert(BookingEntity bookingEntity) throws SQLiteConstraintException {
//
//            }
//
//            @Override
//            public void update(BookingEntity bookingEntity) {
//
//            }
//
//            @Override
//            public void delete(BookingEntity bookingEntity) {
//
//            }
//        };
//      }
// }



