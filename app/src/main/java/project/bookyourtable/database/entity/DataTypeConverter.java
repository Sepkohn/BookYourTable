package project.bookyourtable.database.entity;

import androidx.room.TypeConverter;


import java.util.Date;


public class DataTypeConverter {
    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : (Date) new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
}