package project.bookyourtable.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import project.bookyourtable.database.entity.TableEntity;

public class TableLiveData extends LiveData<TableEntity> {
    private static final String TAG = "AccountLiveData";

    private final DatabaseReference reference;
    private final TableLiveData.MyValueEventListener listener = new TableLiveData.MyValueEventListener();

    public TableLiveData(DatabaseReference ref) {
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
        //DataSnapshot , qui est un instantané des données. Un instantané est une image des données
        // à une référence de base de données particulière à un moment donné.


        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                TableEntity entity = dataSnapshot.getValue(TableEntity.class); //getValue() sur un instantané renvoie la représentation objet
                //Si aucune donnée n'existe la valeur de l'instantané est null
                entity.setId(dataSnapshot.getKey());
                setValue(entity);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
