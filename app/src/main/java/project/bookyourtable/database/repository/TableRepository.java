package project.bookyourtable.database.repository;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import java.util.List;

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

    public LiveData<TableEntity> getAccount(final Long accountId, Application application) {
        return ((BaseApp) application).getDatabase().accountDao().getById(accountId);
    }

    public LiveData<List<AccountEntity>> getAccounts(Application application) {
        return ((BaseApp) application).getDatabase().accountDao().getAll();
    }

    public LiveData<List<AccountEntity>> getByOwner(final String owner, Application application) {
        return ((BaseApp) application).getDatabase().accountDao().getOwned(owner);
    }

    public void insert(final AccountEntity account, OnAsyncEventListener callback,
                       Application application) {
        new CreateAccount(application, callback).execute(account);
    }

    public void update(final AccountEntity account, OnAsyncEventListener callback,
                       Application application) {
        new UpdateAccount(application, callback).execute(account);
    }

    public void delete(final AccountEntity account, OnAsyncEventListener callback,
                       Application application) {
        new DeleteAccount(application, callback).execute(account);
    }

    @SuppressWarnings("unchecked")
    public void transaction(final AccountEntity sender, final AccountEntity recipient,
                            OnAsyncEventListener callback, Application application) {
        new Transaction(application, callback).execute(new Pair<>(sender, recipient));
    }
}
