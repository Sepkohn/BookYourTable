package project.bookyourtable.database.async.table;

import android.app.Application;
import android.os.AsyncTask;
import project.bookyourtable.BaseApp;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.util.OnAsyncEventListener;

public class UpdateTable extends AsyncTask<TableEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateTable(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }


    @Override
    protected Void doInBackground(TableEntity... params) {
        try {
            for (TableEntity tableEntity : params)
                ((BaseApp) application).getDatabase().tableDao()
                        .update(tableEntity);
        } catch (Exception e) {
            this.exception = e;
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