package project.bookyourtable.Database.Entity;

import java.util.Date;
import androidx.room.Entity;


@Entity(tablename = "bookings",
            foreignKeys =
            @ForeignKey{

        }
public class BookingEntity {

   @PrimaryKey(autoGenerate = true)

    private Date date;

    private String name;

    private String telephoneNumber;

    private String message;

    private int tableNumber;

}
