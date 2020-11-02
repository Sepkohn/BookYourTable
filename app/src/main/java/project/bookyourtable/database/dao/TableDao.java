package project.bookyourtable.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.database.entity.TableEntity;

public interface TableDao {


    @Query("SELECT * FROM Btables WHERE tableId = :id")
    LiveData<TableEntity> getTableById(Long id);


    @Query("SELECT * FROM Btables")
    LiveData<List<TableEntity>> getAll();

    @Query("SELECT * FROM Btables WHERE availability = :state")
    LiveData<List<TableEntity>> getByAvailabilitx(boolean state);

    @Query("SELECT * FROM Btables WHERE personNumber = :number")
    LiveData<List<TableEntity>> getBypersonNumber(int number);


    @Insert
    long insert(TableEntity tables) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TableEntity> tables);

    @Update
    void update(TableEntity tables);

    @Delete
    void delete(TableEntity tables);

    @Query("DELETE FROM Btables")
    void deleteAll();
}