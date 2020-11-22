package project.bookyourtable.database.firebase;

import android.icu.util.LocaleData;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.database.entity.TableEntity;

public class BookingListLiveData extends LiveData<List<BookingEntity>> {
    private static final String TAG = "BookingListLiveData";

    private final DatabaseReference reference;


    private final BookingListLiveData.MyValueEventListener listener = new BookingListLiveData.MyValueEventListener();


    public BookingListLiveData(DatabaseReference ref, LocalDate date) {
        reference = ref;
    }

    public BookingListLiveData(DatabaseReference ref, LocalDate date, String time) {
        reference = ref;
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
            entity.setId(childSnapshot.getKey());
            tables.add(entity);
        }
        return tables;
    }
}