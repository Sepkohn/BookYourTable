package project.bookyourtable.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import project.bookyourtable.database.entity.TableEntity;

@Dao
public interface TableDao {
    @Query("SELECT * FROM Btables WHERE tableId = :id")
    LiveData<TableEntity> getTableById(Long id);


    @Query("SELECT * FROM Btables")
    LiveData<List<TableEntity>> getAll();

    @Query("SELECT * FROM Btables WHERE availability = :state AND personNumber >= :nbrePersons")
    LiveData<List<TableEntity>> getByAvailability(boolean state, int nbrePersons);

    @Query("SELECT * FROM Btables WHERE personNumber = :number")
    LiveData<List<TableEntity>> getBypersonNumber(int number);


    @Insert
    long insert(TableEntity tables);

    @Update
    void update(TableEntity tables);

    @Delete
    void delete(TableEntity tables);

    @Query("DELETE FROM Btables")
    void deleteAll();
}