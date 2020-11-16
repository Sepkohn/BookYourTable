package project.bookyourtable.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;


@Entity(tableName = "bookings",
       foreignKeys =
       @ForeignKey(
               entity = TableEntity.class,
               parentColumns = "tableId",
               childColumns = "tableNumber",
               onDelete = ForeignKey.NO_ACTION)
)
public class BookingEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bookingId")
    private Long id;

    @ColumnInfo(name = "date")
    @TypeConverters({DataTypeConverter.class})
    private Date date;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "telephoneNumber")
    private String telephoneNumber;

    @ColumnInfo(name = "message")
    private String message;

    @ColumnInfo(name = "numberPersons")
    private int numberPersons;

    @ColumnInfo(name = "tableNumber")
    private Long tableNumber;


    @Ignore
    public BookingEntity(){}

    public BookingEntity(Date date,String time, String name, String telephoneNumber, String message,int numberPersons, Long tableNumber){
        this.date = date;
        this.time = time;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.message = message;
        this.numberPersons = numberPersons;
        this.tableNumber = tableNumber;
    }



    public Long getId(){return id;}
    public void setId(Long id){this.id = id; }

    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate(){return date;}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName(){return name; }
    public void setName(String personNumber){ this.name=personNumber; }

    public String getTelephoneNumber(){return telephoneNumber; }
    public void setTelephoneNumber(String telephoneNumber){ this.telephoneNumber=telephoneNumber; }

    public String getMessage(){return message; }
    public void setMessage(String message){ this.message=message; }

    public Long getTableNumber(){return tableNumber; }
    public void setTableNumber(Long tableNumber){ this.tableNumber=tableNumber; }

    public int getNumberPersons() {
        return numberPersons;
    }
    public void setNumberPersons(int numberPersons) {
        this.numberPersons = numberPersons;
    }

}
