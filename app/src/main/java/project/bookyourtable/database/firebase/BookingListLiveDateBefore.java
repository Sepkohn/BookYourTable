package project.bookyourtable.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import project.bookyourtable.database.entity.BookingEntity;

public class BookingListLiveDateBefore extends LiveData<List<BookingEntity>> {
    private static final String TAG = "BookingListLiveDateTime";

    private final DatabaseReference reference;
    private final LocalDate date;


    private final BookingListLiveDateBefore.MyValueEventListener listener = new BookingListLiveDateBefore.MyValueEventListener();


    public BookingListLiveDateBefore(DatabaseReference ref, LocalDate date) {
        reference = ref;
        this.date = date;
    }


    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toAccounts(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<BookingEntity> toAccounts(DataSnapshot snapshot) {
        List<BookingEntity> bookings = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            if(LocalDate.parse(childSnapshot.getKey()).isBefore(date)){
                for (DataSnapshot snap:childSnapshot.getChildren()) {
                    BookingEntity entity = snap.getValue(BookingEntity.class);
                    entity.setId(snap.getKey());
                    entity.setDate(LocalDate.parse(childSnapshot.getKey()));
                    bookings.add(entity);
                }
            }
        }
        return bookings;
    }
}