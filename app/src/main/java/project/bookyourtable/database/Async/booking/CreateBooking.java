package project.bookyourtable.database.async.booking;

import android.content.Context;
import android.os.AsyncTask;

import project.bookyourtable.BaseApp;
import project.bookyourtable.database.AppDatabase;
import project.bookyourtable.database.dao.BookingDao;
import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.util.OnAsyncEventListener;

public class CreateBooking extends AsyncTask<BookingEntity, Void, Void>{

        private AppDatabase database;
        private Exception exception;
        private OnAsyncEventListener callBack;

    /**
     * Asynchronous method called to create a BookingEntity in the database
     * @param context = Application
     * @param callBack = Transmitted message
     */
    public CreateBooking(Context context, OnAsyncEventListener callBack) {
            database = ((BaseApp) context).getDatabase().getInstance((BaseApp) context);
            this.callBack = callBack;
        }

    @Override
    protected Void doInBackground(BookingEntity... bookingEntities) {
        try{
            for(BookingEntity booking : bookingEntities)
                database.bookingDao().insert(booking);
            System.out.println("Entity inserted");
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
