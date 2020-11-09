package project.bookyourtable;

import android.app.Application;
import project.bookyourtable.database.AppDatabase;
import project.bookyourtable.database.repository.BookingRepository;
import project.bookyourtable.database.repository.TableRepository;

/**
 * Android Application class. Used for accessing singletons.
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public BookingRepository getBookingRepository() {
        return BookingRepository.getInstance();
    }

    public TableRepository getTableRepository() {
        return TableRepository.getInstance();
    }
}