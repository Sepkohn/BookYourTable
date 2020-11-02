package project.bookyourtable.viewModels.booking;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.database.repository.BookingRepository;
import project.bookyourtable.util.OnAsyncEventListener;

public class BookingViewModel{

    BookingRepository repository;
    MediatorLiveData observableBooking;
    Context applicationContext;

    public BookingViewModel(
            @NonNull Context context,
            final int bookingId,
            BookingRepository repository){
        super(context);
        this.repository = repository;
        observableBooking = new MediatorLiveData<>();

        observableBooking.setValue(null);

        LiveData<BookingEntity> booking = repository.getBookingById(bookingId, context);

        observableBooking.addSource(booking, observableBooking::setValue);

    }

    public MediatorLiveData getObservableBooking() {
        return observableBooking;
    }

    public void createBooking(BookingEntity booking, OnAsyncEventListener callback) {
        repository.insert(booking, callback, applicationContext);
    }

    public void updateBooking(BookingEntity booking, OnAsyncEventListener callback) {
        repository.update(booking, callback, applicationContext);
    }

    public void deleteBooking(BookingEntity booking, OnAsyncEventListener callback) {
        repository.delete(booking, callback, applicationContext);
    }
}
