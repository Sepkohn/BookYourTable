package project.bookyourtable.viewmodel.table;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import project.bookyourtable.BaseApp;
import project.bookyourtable.database.async.table.OnAsyncEventListener;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.database.repository.TableRepository;

public class TableViewModel extends AndroidViewModel {

    private TableRepository repository;

    private Context context;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<TableEntity> observableClient;

    public TableViewModel(@NonNull Context context,
                           final long tableId, TableRepository tableRepository) {
        super(context);

        this.context = context;

        repository = tableRepository;

        observableClient = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableClient.setValue(null);

        LiveData<TableEntity> client = repository.getTableById(tableId, context);

        // observe the changes of the client entity from the database and forward them
        observableClient.addSource(client, observableClient::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Context context;

        private final long tableId;

        private final TableRepository repository;

        public Factory(@NonNull Context context, long tableId) {
            this.context = context;
            this.tableId = tableId;
            repository = ((BaseApp) context).getClientRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TableViewModel(context, tableId, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<TableEntity> getClient() {
        return observableClient;
    }

    public void createClient(TableEntity table, OnAsyncEventListener callback) {
        repository.insert(table, callback, context);
    }

    public void updateClient(TableEntity table, OnAsyncEventListener callback) {
        repository.update(table, callback, context);
    }

    public void deleteClient(TableEntity table, OnAsyncEventListener callback) {
        repository.delete(table, callback, context);

    }
}
