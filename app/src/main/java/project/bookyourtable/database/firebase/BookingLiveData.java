package project.bookyourtable.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;

import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.database.entity.TableEntity;

public class BookingLiveData extends LiveData<BookingEntity> {
    private static final String TAG = "BookingLiveData";

    private final DatabaseReference reference;
    private final String bookingId;

    private final BookingLiveData.MyValueEventListener listener = new BookingLiveData.MyValueEventListener();

    public BookingLiveData(DatabaseReference ref, String bookingId) {
        reference = ref;
        this.bookingId = bookingId;
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
            for (DataSnapshot snap:dataSnapshot.getChildren()) {
                if(snap!=null){
                   if(snap.child(bookingId).exists()){
                       BookingEntity entity = snap.child(bookingId).getValue(BookingEntity.class);
                       entity.setId(snap.child(bookingId).getKey());
                       entity.setDate(LocalDate.parse(snap.getKey()));
                       setValue(entity);
                   }
                }
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
