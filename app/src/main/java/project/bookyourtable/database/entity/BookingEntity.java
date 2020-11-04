package project.bookyourtable.database.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


//@Entity(tableName = "bookings",
////        foreignKeys =
////        @ForeignKey(
////                entity = TableEntity.class,
////                parentColumns = "tableId",
////                childColumns = "",
////                onDelete = ForeignKey.NO_ACTION),
////        indices = {
////                @Index(
////                        value = {"tableId"} //Avant tu avais mis tableNumber je met en attendant pour run le code
////                )
////        }
////)

@Entity(tableName = "bookings")
public class BookingEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bookingId")
    private Long id;

    @ColumnInfo(name = "date")
    @TypeConverters(DataTypeConverter.class)
    private Date date;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "telephoneNumber")
    private String telephoneNumber;

    @ColumnInfo(name = "message")
    private String message;

    @ColumnInfo(name = "tableNumber")
    private int tableNumber;

    @Ignore
    private int numberPersons;
    @Ignore
    private GregorianCalendar calendar;


    @Ignore
    public BookingEntity(){}

    public BookingEntity(Date date, String name, String telephoneNumber, String message, int tableNumber){
        this.date = date;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.message = message;
        this.tableNumber = tableNumber;
    }



    public Long getId(){return id;}
    public void setId(Long id){this.id = id; }


    public String getName(){return name; }
    public void setName(String personNumber){ this.name=personNumber; }

    public String getTelephoneNumber(){return telephoneNumber; }
    public void setTelephoneNumber(String telephoneNumber){ this.telephoneNumber=telephoneNumber; }

    public String getMessage(){return message; }
    public void setMessage(String message){ this.message=message; }

    public int getTableNumber(){return tableNumber; }
    public void setTableNumber(int tableNumber){ this.tableNumber=tableNumber; }

    public void setDate(GregorianCalendar calendar){this.date = new java.sql.Date(calendar.YEAR, calendar.MONTH, calendar.DATE);}

    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate(){return date;}

    public int getNumberPersons() {
        return numberPersons;
    }

    public void setNumberPersons(int numberPersons) {
        this.numberPersons = numberPersons;
    }

    public GregorianCalendar getCalendar(){return calendar;}
    public void setCalendar(GregorianCalendar date){this.calendar = date; }

}
