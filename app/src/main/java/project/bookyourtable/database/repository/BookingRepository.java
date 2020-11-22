package project.bookyourtable.database.repository;


import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import project.bookyourtable.database.firebase.BookingListLiveData;
import project.bookyourtable.database.firebase.BookingLiveData;
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

    public LiveData<BookingEntity> getBookingById(final String accountId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("bookings")
                .child(accountId);
        return new BookingLiveData(reference);
    }

    public LiveData<List<BookingEntity>> getBookingsByDate(final LocalDate date) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("bookings");
        return new BookingListLiveData(reference, date);
    }

    public LiveData<List<BookingEntity>> getBookingsByDateTime(final LocalDate date, final String time) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("booking");
        return new BookingListLiveData(reference, date, time);

    }

    public void insert(final BookingEntity bookingEntity, OnAsyncEventListener callback) {
        DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference("bookings");
        String id = myDatabase.push().getKey();

        myDatabase
                .child(bookingEntity.getDate().toString())
                .child(bookingEntity.getId())
                .setValue(bookingEntity, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final BookingEntity bookingEntity, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("bookings")
                .child(bookingEntity.getId())
                .updateChildren(bookingEntity.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final BookingEntity bookingEntity, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("bookings")
                .child(bookingEntity.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}