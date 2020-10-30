package project.bookyourtable.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table", primaryKeys = {"id"})
public class TableEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "personNumber")
    private int personNumber;

    @ColumnInfo(name = "availability")
    private boolean availability;

    @ColumnInfo(name = "location")
    private int location;

    public TableEntity(){}

    public TableEntity(int personNumber, boolean availability, int location)
    {
        this.personNumber=personNumber;
        this.availability=availability;
        this.location=location;
    }

    public Long getId(){return id;}
    public void setId(Long id){this.id = id; }

    public int getPersonNumber(){return personNumber; }
    public void setPersonNumber(int personNumber){ this.personNumber=personNumber; }

    public boolean getAvailability(){return availability; }
    public void setAvailability(boolean availability){ this.availability=availability; }

    public int getlocation(){return location; }
    public void setlocation(int location){ this.location=location; }


}
