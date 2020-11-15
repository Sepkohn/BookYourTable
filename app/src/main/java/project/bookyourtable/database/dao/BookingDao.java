package project.bookyourtable.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.Date;

import java.util.List;

import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.database.entity.DataTypeConverter;

@Dao
public interface BookingDao {

    @Query("SELECT * FROM bookings")
    LiveData<List<BookingEntity>> getAllBookings();

    @Query("SELECT * FROM bookings WHERE date = :date")
    @TypeConverters({DataTypeConverter.class})
    LiveData<List<BookingEntity>> getBookingsByDate(Date date);

    @Query("SELECT * FROM bookings WHERE bookingId = :id")
    LiveData<BookingEntity> getBookingsById(long id);

    @Insert
    void insert(BookingEntity bookingEntity)throws SQLiteConstraintException;

    @Update
    void update(BookingEntity bookingEntity);

    @Delete
    void delete(BookingEntity bookingEntity);

    @Query("DELETE FROM bookings")
    void deleteAll();

}
