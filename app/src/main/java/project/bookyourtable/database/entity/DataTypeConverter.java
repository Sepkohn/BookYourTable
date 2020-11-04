package project.bookyourtable.database.entity;

import androidx.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DataTypeConverter {
    @TypeConverter
    public java.sql.Date fromTimestamp(Long value) {
        return value == null ? null : (java.sql.Date) new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(java.sql.Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
    @TypeConverter
    public Date calendarToDate(GregorianCalendar calendar){
        if(calendar==null){
            return null;
        }
        else{
            return new Date(calendar.YEAR, Calendar.MONTH, Calendar.DATE, Calendar.HOUR, calendar.MINUTE);
        }
    }

    public GregorianCalendar dateToCalendar(Date date){
        if (date==null){
            return null;
        }
        else{
            return new GregorianCalendar(date.getYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes());
    }
    }
}