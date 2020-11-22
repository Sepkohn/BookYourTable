package project.bookyourtable.database.repository;


import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

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
                .getReference("tables");
//                .child(tableId);
        return new TableLiveData(reference);
    }

    public LiveData<List<TableEntity>> getByAvailability(boolean state,int nbrePersons) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("tables");
        return new TableListLiveData(reference);
    }

    public LiveData<List<TableEntity>> getByOwner() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("tables");
        return new TableListLiveData(reference);
    }

    public void insert(final TableEntity table, OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("tables").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("tables")
                .child(id)
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
                .child(table.getId())
                .updateChildren(table.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
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