package project.bookyourtable.database.repository;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import java.util.List;

import project.bookyourtable.BaseApp;
import project.bookyourtable.database.async.table.CreateTable;
import project.bookyourtable.database.async.table.OnAsyncEventListener;
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

    public LiveData<TableEntity> getAccount(final Long tableId, Application application) {
        return ((BaseApp) application).getDatabase().TableDao().getById(tableId);
    }

    public LiveData<List<TableEntity>> getAccounts(Application application) {
        return ((BaseApp) application).getDatabase().TableDao().getAll();
    }

    public LiveData<List<TableEntity>> getByOwner(final String owner, Application application) {
        return ((BaseApp) application).getDatabase().TableDao().getOwned(owner);
    }

    public void insert(final TableEntity account, OnAsyncEventListener callback,
                       Application application) {
        new CreateTable(application, callback).execute(account);
    }

    public void update(final TableEntity account, OnAsyncEventListener callback,
                       Application application) {
        new CreateTable(application, callback).execute(account);
    }

    public void delete(final TableEntity account, OnAsyncEventListener callback,
                       Application application) {
        new CreateTable(application, callback).execute(account);
    }


}
