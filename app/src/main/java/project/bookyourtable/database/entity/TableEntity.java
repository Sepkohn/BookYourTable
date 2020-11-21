package project.bookyourtable.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "Btables")
public class TableEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tableId")
    private Long id;

    @ColumnInfo(name = "personNumber")
    private int personNumber;

    @ColumnInfo(name = "availability")
    private boolean availability;

    @ColumnInfo(name = "location")
    private int location;

    public TableEntity(){}

    public TableEntity(int personNumber, boolean availability, int location){
        this.personNumber=personNumber;
        this.availability=availability;
        this.location=location;
    }
    @Exclude
    public Long getId(){return id;}
    public void setId(Long id){this.id = id; }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("personNumber", personNumber);
        result.put("availability", availability);
        return result;
    }
    public int getPersonNumber(){return personNumber; }
    public void setPersonNumber(int personNumber){ this.personNumber=personNumber; }

    public boolean getAvailability(){return availability; }
    public void setAvailability(boolean availability){ this.availability=availability; }

    @Exclude
    public int getLocation(){return location; }
    public void setLocation(int location){ this.location=location; }

}
