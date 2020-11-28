package project.bookyourtable.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import project.bookyourtable.database.entity.BookingEntity;

public class BookingListTimeLiveData extends LiveData<List<BookingEntity>> {
    private static final String TAG = "BookingListLiveDateTime";

    private final DatabaseReference reference;
    private final String time;


    private final BookingListTimeLiveData.MyValueEventListener listener = new BookingListTimeLiveData.MyValueEventListener();

    public BookingListTimeLiveData(DatabaseReference ref, String time) {
        reference = ref;
        this.time=time;
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
        List<BookingEntity> tables = new ArrayList<>();

        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            BookingEntity entity = childSnapshot.getValue(BookingEntity.class);
            if(String.valueOf(entity.getTime()).equals(time)){
                entity.setId(childSnapshot.getKey());
                tables.add(entity);
            }
        }
        return tables;
    }
}