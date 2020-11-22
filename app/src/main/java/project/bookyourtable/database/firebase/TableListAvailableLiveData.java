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

import project.bookyourtable.database.entity.TableEntity;

public class TableListAvailableLiveData extends LiveData<List<TableEntity>> {
    private static final String TAG = "TableListLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();
    private final String state;
    private final String nbrPersons;

    public TableListAvailableLiveData(DatabaseReference ref, String state, String nbrPersons) {
        reference = ref;
        this.state = state;
        this.nbrPersons = nbrPersons;
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
            Iterable<DataSnapshot> mData = dataSnapshot.getChildren();
                setValue(toTablesList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<TableEntity> toTablesList(DataSnapshot snapshot) {
        List<TableEntity> tables = new ArrayList<>();

        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            TableEntity entity = childSnapshot.getValue(TableEntity.class);
            if(String.valueOf(entity.getPersonNumber()).equals(nbrPersons) && String.valueOf(entity.getAvailability()).equals(state)) {
                entity.setId(childSnapshot.getKey());
                tables.add(entity);
            }
        }
        return tables;
    }
}