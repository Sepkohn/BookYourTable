package project.bookyourtable.database;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import project.bookyourtable.database.dao.BookingDao;
import project.bookyourtable.database.entity.BookingEntity;


public class AppDatabase {

    private static AppDatabase instance;
    private Context context;

    public AppDatabase(Context context){
        this.context = context;
    }

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = new AppDatabase(context);
                }
            }
        }
        return instance;
    }

    public BookingDao bookingDao(){

        return new BookingDao() {
            @Override
            public LiveData<List<BookingEntity>> getAllBookings() {
                return null;
            }

            @Override
            public LiveData<BookingEntity> getBookingsByDate(Date date) {
                return null;
            }

            @Override
            public LiveData<BookingEntity> getBookingsById(int id) {
                return null;
            }

            @Override
            public void insert(BookingEntity bookingEntity) throws SQLiteConstraintException {

            }

            @Override
            public void update(BookingEntity bookingEntity) {

            }

            @Override
            public void delete(BookingEntity bookingEntity) {

            }
        };
    }


}
