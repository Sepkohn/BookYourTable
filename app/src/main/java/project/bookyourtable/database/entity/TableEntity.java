package project.bookyourtable.database.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class TableEntity {
    private String id;
    private int personNumber;
    private boolean availability;
    private int location;

    public TableEntity(){}

    public TableEntity(int personNumber, boolean availability, int location){
        this.personNumber=personNumber;
        this.availability=availability;
        this.location=location;
    }

    @Exclude
    public String getId(){return id;}
    public void setId(String id){this.id = id; }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("availability", availability);
        result.put("personNumber", personNumber);
        return result;
    }

    public int getPersonNumber(){return personNumber; }
    public void setPersonNumber(int personNumber){ this.personNumber=personNumber; }

    public boolean getAvailability(){return availability; }
    public void setAvailability(boolean availability){ this.availability=availability; }
    public int getLocation(){return location; }
    public void setLocation(int location){ this.location=location; }




}
