package project.bookyourtable.viewmodel.table;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import java.util.List;
import project.bookyourtable.BaseApp;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.database.repository.TableRepository;
import project.bookyourtable.util.OnAsyncEventListener;

//ViewModel for TableActivity
public class TableListViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<TableEntity>> observableOwnTables;
    private Application application;
    private TableRepository repository;

    public TableListViewModel(@NonNull Application application,
                                TableRepository tableRepository) {
        super(application);

        this.application = application;
        repository = tableRepository;

        observableOwnTables = new MediatorLiveData<>();
        observableOwnTables.setValue(null);

        LiveData<List<TableEntity>> ownAccounts = repository.getByOwner(application);
        observableOwnTables.addSource(ownAccounts, observableOwnTables::setValue);
    }

    /**
     * Expose the LiveData AccountEntities query so the UI can observe it.
     */
    public LiveData<List<TableEntity>> getOwnTables() {
        return observableOwnTables;
    }

    public void deleteTable(TableEntity table, OnAsyncEventListener callback) {
        repository.delete(table, callback, application);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final TableRepository tableRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            tableRepository = ((BaseApp) application).getTableRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new TableListViewModel(application, tableRepository);
        }
    }
}
