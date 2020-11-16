package project.bookyourtable.viewmodel.booking;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.database.repository.BookingRepository;
import project.bookyourtable.util.OnAsyncEventListener;

public class BookingViewModel extends AndroidViewModel {

    private BookingRepository repository;
    private final MediatorLiveData<BookingEntity>  observableBooking;
    private Context applicationContext;

    /**
     * Store a BookingEntity that we get from the database due to @param bookingId
     * @param application
     * @param bookingId
     * @param repository
     */
    public BookingViewModel(
            @NonNull Application application,
            final long bookingId,
            BookingRepository repository){
        super(application);

        this.repository = repository;

        applicationContext = application.getApplicationContext();

        observableBooking = new MediatorLiveData<>();

        observableBooking.setValue(null);

        LiveData<BookingEntity> booking = repository.getBookingById(bookingId, applicationContext);

        observableBooking.addSource(booking, observableBooking::setValue);

    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final long bookingId;

        private final BookingRepository repository;

        public Factory(@NonNull Application application, long bookingId) {
            this.application = application;
            this.bookingId = bookingId;
            repository = BookingRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new BookingViewModel(application, bookingId, repository);
        }
    }


    public LiveData<BookingEntity> getBooking() {
        return observableBooking;
    }

    public void updateBooking(BookingEntity booking, OnAsyncEventListener callback) {
        repository.update(booking, callback, applicationContext);
    }

    public void deleteBooking(BookingEntity booking, OnAsyncEventListener callback) {
        repository.delete(booking, callback, applicationContext);
    }
}
