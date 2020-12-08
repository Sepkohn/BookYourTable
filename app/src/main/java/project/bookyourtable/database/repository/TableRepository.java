package project.bookyourtable.database.repository;


import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import project.bookyourtable.database.firebase.TableListAvailableLiveData;
import project.bookyourtable.database.firebase.TableListLiveData;
import project.bookyourtable.database.firebase.TableLiveData;
import project.bookyourtable.util.OnAsyncEventListener;
import project.bookyourtable.database.entity.TableEntity;

public class TableRepository {
    private static TableRepository instance;

    private TableRepository() {
    }

    public static TableRepository getInstance() {
        if (instance == null) {
            synchronized (TableRepository.class) {
                if (instance == null) {
                    instance = new TableRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<TableEntity> getTableById(final String tableId){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("tables")
                .child(tableId);

        return new TableLiveData(reference);
    }

    public LiveData<List<TableEntity>> getByAvailability(boolean state,int nbrePersons) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("tables");
        return new TableListAvailableLiveData(reference, String.valueOf(state), String.valueOf(nbrePersons));
    }

    public LiveData<List<TableEntity>> getByOwner() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("tables");
        return new TableListLiveData(reference);
    }

    public void insert(final TableEntity table, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("tables")
                .child(String.valueOf(table.getLocation()))
                .setValue(table, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });

    }

    public void update(final TableEntity table, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("tables")
                .child(String.valueOf(table.getId()))
                .updateChildren(table.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }


    public void updateNewNumber(final TableEntity table,int newNumber, OnAsyncEventListener callback){
        table.setId(String.valueOf(newNumber));
        table.setLocation(newNumber);
        insert(table, callback);
    }

    public void delete(final TableEntity table, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("tables")
                .child(table.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}