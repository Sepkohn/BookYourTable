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

public class AvailableTableListViewModel extends AndroidViewModel {

    private Application application;

    private TableRepository repository;

    private final MediatorLiveData<List<TableEntity>> observableOwnTables;

    public AvailableTableListViewModel(@NonNull Application application,
                                       TableRepository tableRepository, int nbrePersons) {
        super(application);

        this.application = application;

        repository = tableRepository;


        observableOwnTables = new MediatorLiveData<>();
        // set by default null, until we get data from the database.

        observableOwnTables.setValue(null);


        LiveData<List<TableEntity>> availableTables = repository.getByAvailability(true, nbrePersons, application);

        // observe the changes of the entities from the database and forward them

        observableOwnTables.addSource(availableTables, observableOwnTables::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final TableRepository tableRepository;
        private final int nbrePersons;


        public Factory(@NonNull Application application, int nbrePersons) {
            this.application = application;
            tableRepository = ((BaseApp) application).getTableRepository();
            this.nbrePersons = nbrePersons;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new AvailableTableListViewModel(application, tableRepository, nbrePersons);
        }
    }


    /**
     * Expose the LiveData AccountEntities query so the UI can observe it.
     */
    public LiveData<List<TableEntity>> getOwnTables() {
        return observableOwnTables;
    }



}
