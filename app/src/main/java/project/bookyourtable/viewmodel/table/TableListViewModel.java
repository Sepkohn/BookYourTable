package project.bookyourtable.viewmodel.table;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import project.bookyourtable.BaseApp;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.database.repository.TableRepository;
import project.bookyourtable.util.OnAsyncEventListener;

public class TableListViewModel extends AndroidViewModel {

    private Application application;

    private TableRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    //private final MediatorLiveData<List<ClientWithAccounts>> observableClientAccounts;
    private final MediatorLiveData<List<TableEntity>> observableOwnTables;

    public TableListViewModel(@NonNull Application application,
                                TableRepository tableRepository) {
        super(application);

        this.application = application;

        repository = tableRepository;


        observableOwnTables = new MediatorLiveData<>();
        // set by default null, until we get data from the database.

        observableOwnTables.setValue(null);


        LiveData<List<TableEntity>> ownAccounts = repository.getByOwner(application);

        // observe the changes of the entities from the database and forward them

        observableOwnTables.addSource(ownAccounts, observableOwnTables::setValue);
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
            //noinspection unchecked
            return (T) new TableListViewModel(application, tableRepository);
        }
    }


    /**
     * Expose the LiveData AccountEntities query so the UI can observe it.
     */
    public LiveData<List<TableEntity>> getOwnAccounts() {
        return observableOwnTables;
    }

    public void deleteTable(TableEntity table, OnAsyncEventListener callback) {
        repository.delete(table, callback, application);
    }


}
