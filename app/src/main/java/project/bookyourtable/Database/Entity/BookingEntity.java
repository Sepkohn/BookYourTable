package project.bookyourtable.Database.Entity;

import java.util.Date;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


        /*@Entity(tableName = "bookings",
            foreignKeys =
            @ForeignKey(
                entity = null.class,
                parentColumns = "",
                childColumns = "",
                onDelete = ForeignKey.NO_ACTION),
            indices = {
                @Index(
                        value = {"tableNumber"}
                )
            }
)*/
public class BookingEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Date date;

    private String name;

    private String telephoneNumber;

    private String message;

    private int tableNumber;

    public BookingEntity(Date date, String name, String telephoneNumber, String message, int tableNumber){
        this.date = date;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.message = message;
        this.tableNumber = tableNumber;
    }

}
