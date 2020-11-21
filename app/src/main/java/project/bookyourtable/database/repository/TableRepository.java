package project.bookyourtable.database.repository;


import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import project.bookyourtable.database.async.table.CreateTable;
import project.bookyourtable.database.async.table.DeleteTable;
import project.bookyourtable.database.async.table.UpdateTable;
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

//    public LiveData<TableEntity> getTableById(final Long tableId, Context context) {
//        return ((BaseApp) context).getDatabase().tableDao().getTableById(tableId);
//    }

    public LiveData<TableEntity> getTableById(final Long tableId, Context context){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("tables")
                .child(tableId.toString());
        return new TableLiveData(reference);
    }
//    public LiveData<List<TableEntity>> getTables(Context context) {
//        return ((BaseApp) context).getDatabase().tableDao().getAll();
//    }

    public LiveData<List<TableEntity>> getByAvailability(boolean state,int nbrePersons, Context context) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("tables")
                .child("" + nbrePersons)
                .child("" + state);
        return new TableListLiveData(reference, state, nbrePersons);
    }
//    public LiveData<List<TableEntity>> getByAvailability(boolean state,int nbrePersons, Context context) {
//        return ((BaseApp) context).getDatabase().tableDao().getByAvailability (state, nbrePersons);
//    }

//    public LiveData<List<TableEntity>> getBypersonNumber(int number, Context context) {
//        return ((BaseApp) context).getDatabase().tableDao().getBypersonNumber(number);
//    }


    public LiveData<List<TableEntity>> getByOwner(Context context) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("clients")
                .child()
                .child("accounts");
        return new AccountListLiveData(reference, owner);
    }


//    public LiveData<List<TableEntity>> getByOwner(Context context) {
//        return ((BaseApp) context).getDatabase().tableDao().getAll();
//    }

    public void insert(final TableEntity table, OnAsyncEventListener callback,
                       Context context) {
        new CreateTable(context, callback).execute(table);
    }

    public void update(final TableEntity table, OnAsyncEventListener callback,
                       Context context) {
        new UpdateTable((Application) context, callback).execute(table);
    }

    public void delete(final TableEntity table, OnAsyncEventListener callback,
                       Context context) {
        new DeleteTable(context, callback).execute(table);
    }
}