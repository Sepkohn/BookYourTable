package project.bookyourtable.database.entity;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class BookingEntity implements Serializable {
    private String id;
    private LocalDate date;
    private String time;
    private String name;
    private String telephoneNumber;
    private String message;
    private int numberPersons;
    private String tableNumber;


    public BookingEntity(){}

    public BookingEntity(LocalDate date, String time, String name, String telephoneNumber, String message, int numberPersons, String tableNumber){
        this.date = date;
        this.time = time;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.message = message;
        this.numberPersons = numberPersons;
        this.tableNumber = tableNumber;
    }

    @Exclude
    public String getId(){return id;}
    public void setId(String id){this.id = id; }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("time", time);
        result.put("name", name);
        result.put("telephoneNumber", telephoneNumber);
        result.put("message", message);
        result.put("numberPersons", numberPersons);
        result.put("tableNumber", tableNumber);

        return result;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalDate getDate(){return date;}

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

    public String getTableNumber(){return tableNumber; }
    public void setTableNumber(String tableNumber){ this.tableNumber=tableNumber; }

    public int getNumberPersons() {
        return numberPersons;
    }
    public void setNumberPersons(int numberPersons) {
        this.numberPersons = numberPersons;
    }


}
