package project.bookyourtable.database;

import android.os.AsyncTask;
import android.util.Log;

import project.bookyourtable.database.entity.TableEntity;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addTable(final AppDatabase db, final int personNumber, final boolean availability,
                                  final int location) {
        TableEntity table = new TableEntity(personNumber, availability, location);
        db.tableDao().insert(table);
    }

    private static void populateWithTestData(AppDatabase db) {

    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }
}
