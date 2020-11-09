package project.bookyourtable.database.async.table;

import android.content.Context;
import android.os.AsyncTask;
import project.bookyourtable.database.AppDatabase;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.util.OnAsyncEventListener;

public class CreateTable extends AsyncTask<TableEntity, Void, Void>{

    private AppDatabase database;
    private Exception exception;
    private OnAsyncEventListener callback;

    public CreateTable(Context context, OnAsyncEventListener callBack) {
        database = AppDatabase.getInstance(context);
        this.callback = callBack;
    }

    public void execute (TableEntity tableEntity) {

    }

    @Override
    protected Void doInBackground(TableEntity... bookingEntities) {
        try{
            for(TableEntity table : bookingEntities)
                database.tableDao().insert(table);
        }
        catch (Exception e){
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

