package project.bookyourtable.database.async.table;

import android.app.Application;
import android.os.AsyncTask;

import project.bookyourtable.BaseApp;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.util.OnAsyncEventListener;

public class CreateTable extends AsyncTask<TableEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateTable(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(TableEntity... params) {
        try {
            for (TableEntity table : params)
                ((BaseApp) application).getDatabase().TableDao()
                        .insert(table);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}

