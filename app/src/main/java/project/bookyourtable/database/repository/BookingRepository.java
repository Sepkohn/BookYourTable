package project.bookyourtable.database.repository;


import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.List;

import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.database.firebase.BookingListLiveByTable;
import project.bookyourtable.database.firebase.BookingListLiveDateBefore;
import project.bookyourtable.database.firebase.BookingListLiveDateTime;
import project.bookyourtable.database.firebase.BookingListTimeLiveData;
import project.bookyourtable.database.firebase.BookingLiveData;
import project.bookyourtable.util.OnAsyncEventListener;
import project.bookyourtable.viewmodel.table.TableViewModel;

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

    public LiveData<List<BookingEntity>> getBookingsByTable(final int location) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("bookings");
        return new BookingListLiveByTable(reference, location);
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

    public void updateBookingTable(int tableNumber, LifecycleOwner owner, TableViewModel viewModel, TableEntity tableEntity) {
        getBookingsByTable(tableNumber).observe(owner, bookingEntities -> {
            if(bookingEntities.size()>0){
                for(BookingEntity entity:bookingEntities){
                    entity.setTableNumber(String.valueOf(tableEntity.getLocation()));
                    update(entity, new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            System.out.println("Table number has changed");
                            try {
                                Thread.sleep(1000);
                                tableEntity.setId(String.valueOf(tableNumber));
                                viewModel.deleteTable(tableEntity, new OnAsyncEventListener() {
                                    @Override
                                    public void onSuccess() {
                                        System.out.println("Table deleted");
                                    }

                                    @Override
                                    public void onFailure(Exception e) {
                                        System.out.println("Error on delete the table");
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(Exception e) {
                            System.out.println("Error on table number changed");
                        }
                    });
                }
            }
        });
    }


    /**
     * Delete the actual booking on its date, and recreate on the new date
     * @param bookingEntity actual booking
     * @param date new date of registration
     * @param callback on succes or failure message
     */
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

    /**
     * Delete all historic of passed bookings
     * @param date actual date
     * @param owner Activity in which is called this function
     */
    public void deleteBookingHistory(final LocalDate date, LifecycleOwner owner){
       getBookingsByDateBefore(date).observe(owner, bookingEntities -> {
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
       });
    }
}