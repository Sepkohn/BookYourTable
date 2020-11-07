package project.bookyourtable.database.repository;


import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import project.bookyourtable.BaseApp;
import project.bookyourtable.database.async.booking.CreateBooking;
import project.bookyourtable.database.async.booking.DeleteBooking;
import project.bookyourtable.database.async.booking.UpdateBooking;
import project.bookyourtable.util.OnAsyncEventListener;
import project.bookyourtable.database.entity.BookingEntity;

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

    public LiveData<BookingEntity> getBookingById(final long accountId, Context context) {
        return ((BaseApp) context).getDatabase().bookingDao().getBookingsById(accountId);
    }

    public LiveData<List<BookingEntity>> getBookings(Context context) {
        return ((BaseApp) context).getDatabase().bookingDao().getAllBookings();
    }

    public LiveData<List<BookingEntity>> getBookingsByDate(final Date date, Context context) {
        return ((BaseApp) context).getDatabase().bookingDao().getBookingsByDate(date);
    }

    public void insert(final BookingEntity bookingEntity, OnAsyncEventListener callBack, Context context){
        new CreateBooking(context, callBack).execute(bookingEntity);
    }

    public void update(final BookingEntity bookingEntity, OnAsyncEventListener callBack, Context context) {
        new UpdateBooking(context, callBack).execute(bookingEntity);
    }

    public void delete(final BookingEntity bookingEntity, OnAsyncEventListener callBack, Context context) {
        new DeleteBooking(context, callBack).execute(bookingEntity);
    }



}
