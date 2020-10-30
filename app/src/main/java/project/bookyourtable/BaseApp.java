package project.bookyourtable;

import android.app.Application;

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

    public AccountRepository getAccountRepository() {
        return AccountRepository.getInstance();
    }

    public ClientRepository getClientRepository() {
        return ClientRepository.getInstance();
    }
}