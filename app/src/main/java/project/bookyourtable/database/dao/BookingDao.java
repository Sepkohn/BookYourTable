package project.bookyourtable.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import project.bookyourtable.database.entity.BookingEntity;

@Dao
public interface BookingDao {

    @Query("SELECT * FROM bookings")
    LiveData<List<BookingEntity>> getAllBookings();

    @Query("SELECT * FROM bookings WHERE date = :date")
    LiveData<BookingEntity> getBookingsByDate(Date date);

    @Query("SELECT * FROM bookings WHERE bookingId = :id")
    LiveData<BookingEntity> getBookingsById(int id);

    @Insert
    void insert(BookingEntity bookingEntity)throws SQLiteConstraintException;

    @Update
    void update(BookingEntity bookingEntity);

    @Delete
    void delete(BookingEntity bookingEntity);

}
