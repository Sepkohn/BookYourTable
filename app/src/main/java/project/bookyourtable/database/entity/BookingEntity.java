package project.bookyourtable.database.entity;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


        @Entity(tableName = "bookings",
            foreignKeys =
            @ForeignKey(
                entity = TableEntity.class,
                parentColumns = "tableId",
                childColumns = "",
                onDelete = ForeignKey.NO_ACTION),
            indices = {
                @Index(
                        value = {"tableNumber"}
                )
            }
)
public class BookingEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bookingId")
    private Long id;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "telephoneNumber")
    private String telephoneNumber;

    @ColumnInfo(name = "message")
    private String message;

    @ColumnInfo(name = "tableNumber")
    private int tableNumber;

    public BookingEntity(Date date, String name, String telephoneNumber, String message, int tableNumber){
        this.date = date;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.message = message;
        this.tableNumber = tableNumber;
    }

}
