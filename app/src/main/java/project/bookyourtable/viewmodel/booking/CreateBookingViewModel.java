package project.bookyourtable.viewmodel.booking;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import project.bookyourtable.BaseApp;
import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.database.repository.BookingRepository;
import project.bookyourtable.util.OnAsyncEventListener;

public class CreateBookingViewModel extends AndroidViewModel {

    private BookingRepository repository;
    private Context applicationContext;

    public CreateBookingViewModel(@NonNull Application application, BookingRepository repository) {
        super(application);

        this.repository = repository;

        applicationContext = application.getApplicationContext();
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final BookingRepository repository;

        public Factory(@NonNull Application application) {
            this.application = application;
            repository = BookingRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CreateBookingViewModel(application, repository);
        }
    }

    public void createBooking(BookingEntity booking, OnAsyncEventListener callback) {
        repository.insert(booking, callback, applicationContext);
    }
}
