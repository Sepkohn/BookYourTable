package project.bookyourtable.database.repository;


import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import project.bookyourtable.database.firebase.BookingListLiveDateBefore;
import project.bookyourtable.database.firebase.BookingListLiveDateTime;
import project.bookyourtable.database.firebase.BookingListTimeLiveData;
import project.bookyourtable.database.firebase.BookingLiveData;
import project.bookyourtable.ui.MainActivity;
import project.bookyourtable.ui.booking.ReservationsListActivity;
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
                .getReference("bookings");
        return new BookingLiveData(reference, accountId);
    }

    public LiveData<List<BookingEntity>> getBookingsByDate(final LocalDate date) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("bookings").child(date.toString());
        return new BookingListLiveDateTime(reference);
    }
    public LiveData<List<BookingEntity>> getBookingsByDateBefore(final LocalDate date) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("bookings");
        return new BookingListLiveDateBefore(reference, date);
    }

    public LiveData<List<BookingEntity>> getBookingsByDateTime(final LocalDate date, final String time) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("bookings").child(date.toString());
        return new BookingListTimeLiveData(reference, time);

    }

    public void insert(final BookingEntity bookingEntity, OnAsyncEventListener callback) {
        DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference("bookings");
        bookingEntity.setId(myDatabase.push().getKey());

        myDatabase
                .child(bookingEntity.getDateToString())
                .child(bookingEntity.getId())
                        .setValue(bookingEntity.toMap(), (databaseError, databaseReference) -> {
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
                .child(bookingEntity.getDateToString())
                .child(bookingEntity.getId())
                .updateChildren(bookingEntity.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void updateWithDate(final BookingEntity bookingEntity, LocalDate date, OnAsyncEventListener callback) {
        delete(bookingEntity, callback);
        bookingEntity.setDate(date);
        insert(bookingEntity, callback);
    }

    public void delete(final BookingEntity bookingEntity, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("bookings")
                .child(bookingEntity.getDateToString())
                .child(bookingEntity.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void deleteBookingHistory(final LocalDate date, LifecycleOwner owner){
        BookingRepository.getInstance().getBookingsByDateBefore(date).observe(owner, new Observer<List<BookingEntity>>() {
            @Override
            public void onChanged(List<BookingEntity> bookingEntities) {
                for(BookingEntity bookingEntity:bookingEntities) {
                    delete(bookingEntity, new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            System.out.println("DELETED");
                        }

                        @Override
                        public void onFailure(Exception e) {
                            System.out.println("BOOKING NOT DELETED");
                        }
                    });
                }
            }
        });
    }
}