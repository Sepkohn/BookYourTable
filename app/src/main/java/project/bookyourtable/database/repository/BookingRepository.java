package project.bookyourtable.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import project.bookyourtable.BaseApp;
import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.database.entity.TableEntity;

public class BookingRepository {
    private static BookingRepository instance;

    public BookingRepository(){

    }

    public static BookingRepository getInstance() {
        if (instance == null) {
            synchronized (BookingRepository.class) {
                if (instance == null) {
                    instance = new BookingRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<BookingEntity> getBookingById(final int accountId, Application application) {
        return ((BaseApp) application).getDatabase().bookingDao().getBookingsById(accountId);
    }

    public LiveData<List<BookingEntity>> getBookings(Application application) {
        return ((BaseApp) application).getDatabase().bookingDao().getAllBookings();
    }

    public LiveData<BookingEntity> getBookingByDate(final Date date, Application application) {
        return ((BaseApp) application).getDatabase().bookingDao().getBookingsByDate(date);
    }



}
