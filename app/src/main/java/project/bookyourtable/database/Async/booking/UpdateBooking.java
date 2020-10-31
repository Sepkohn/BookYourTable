package project.bookyourtable.database.async.booking;

import android.content.Context;
import android.os.AsyncTask;

import project.bookyourtable.database.AppDatabase;
import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.util.OnAsyncEventListener;

public class UpdateBooking extends AsyncTask<BookingEntity, Void, Void> {

    private AppDatabase database;
    private Exception exception;
    private OnAsyncEventListener callBack;

    public UpdateBooking(Context context, OnAsyncEventListener callBack) {
        database = AppDatabase.getInstance(context);
        this.callBack = callBack;
    }


    public void execute(BookingEntity bookingEntity) {
    }

    @Override
    protected Void doInBackground(BookingEntity... bookingEntities) {
        try{
            for(BookingEntity booking : bookingEntities)
                database.bookingDao().delete(booking);
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
