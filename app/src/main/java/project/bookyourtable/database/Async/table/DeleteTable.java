package project.bookyourtable.database.async.table;

import android.content.Context;
import android.os.AsyncTask;

import project.bookyourtable.database.AppDatabase;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.util.OnAsyncEventListener;

public class DeleteTable extends AsyncTask<TableEntity, Void, Void> {

    private AppDatabase database;
    private Exception exception;
    private OnAsyncEventListener callBack;

    public DeleteTable(Context context, OnAsyncEventListener callBack) {
        database = AppDatabase.getInstance(context);
        this.callBack = callBack;
    }

    public void execute(TableEntity tableEntity) {

    }

    @Override
    protected Void doInBackground(TableEntity... tableEntities) {
        try{
            for(TableEntity table : tableEntities)
                database.tableDao().delete(table);
        }
        catch (Exception e){
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(exception ==null){
            callBack.onSuccess();
        }
        else{
            callBack.onFailure(exception);
        }
    }
}
