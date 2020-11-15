package project.bookyourtable.viewmodel.table;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.database.repository.TableRepository;
import project.bookyourtable.util.OnAsyncEventListener;

//ViewModel for EditTableActivity
public class TableViewModel extends AndroidViewModel {

    private TableRepository repository;

    private Application application;

    /**
     * MediatorLiveData observe other LiveData objects and react on their emissions.
     * */
    private final MediatorLiveData<TableEntity> observableTable;

    public TableViewModel(@NonNull Application application,
                           final long tableId, TableRepository tableRepository) {
        super(application);
        this.application = application;
        repository = tableRepository;

        observableTable = new MediatorLiveData<>();
        observableTable.setValue(null);

        LiveData<TableEntity> table = repository.getTableById(tableId, application);

        observableTable.addSource(table, observableTable::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final long id;

        private final TableRepository repository;

        public Factory(@NonNull Application application, long tableId) {
            this.application = application;
            this.id = tableId;
            repository = TableRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new TableViewModel(application, id, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<TableEntity> getTable() {
        return observableTable;
    }

    public void createTable(TableEntity table, OnAsyncEventListener callback) {
        repository.insert(table, callback, application);
    }

    public void updateTable(TableEntity table, OnAsyncEventListener callback) {
        repository.update(table, callback, application);
    }

    public void deleteTable(TableEntity table, OnAsyncEventListener callback) {
        repository.delete(table, callback, application);

    }
}
