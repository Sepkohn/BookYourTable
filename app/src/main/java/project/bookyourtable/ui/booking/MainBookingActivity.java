package project.bookyourtable.ui.booking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import project.bookyourtable.R;
import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.ui.booking.BookingDatasActivity;

import java.time.LocalDateTime;

import static java.lang.Integer.parseInt;

public class MainBookingActivity extends AppCompatActivity {

    Date bookingdate;
    ChipGroup cg;
    public static final String MY_ENTITY = ".project.bookyourtable.ui.booking.ENTITY";

    public MainBookingActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_booking);

        CalendarView view = findViewById(R.id.calendarView);

        view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            DateFormat df = new SimpleDateFormat("dd:MM:yyyy");
            @SuppressLint("WrongConstant")
            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month,
                                            int date) {

                bookingdate = new Date(year-1900, month, date);
            }
        });
    }

    public void continueBooking(View view){
        BookingEntity entity;
        if((entity=verifyInformations()) !=null) {
            Intent intent = new Intent(this, BookingDatasActivity.class);
            intent.putExtra(MY_ENTITY, (Serializable) entity);
            startActivity(intent);
        }
        //else message d'erreur
    }


    public void addPerson(View view){

        EditText numberPersons = (EditText)findViewById(R.id.editTextNumber);
        int number = parseInt(numberPersons.getText().toString().trim());
        number = number +1;
        displayNumbrePersons(numberPersons, number);
    }

    private void displayNumbrePersons(EditText numberPersons, int i) {
        numberPersons.setText("" + i);
    }

    public void decreasePerson(View view){

        EditText numberPersons = findViewById(R.id.editTextNumber);
        int number = parseInt(numberPersons.getText().toString().trim());

        if(number<=1){
            displayNumbrePersons(numberPersons, 1);
        }
        else
            displayNumbrePersons(numberPersons,number-1);

    }

    public BookingEntity verifyInformations() {

        EditText numberPersons = findViewById(R.id.editTextNumber);
        int number = parseInt(numberPersons.getText().toString().trim());

        Date today = new Date();


        boolean isATimeSlot = verifyTimeSlot();

        if(number>0&&isATimeSlot&&!(bookingdate.compareTo(today) <0)){
           return createEntity(number);
        }

        return null;
    }

    private boolean verifyTimeSlot() {

        this.cg = findViewById(R.id.timeSlots);
        int c = cg.getCheckedChipId();

        return c != -1;
    }

    private BookingEntity createEntity(int number){
        Chip chip = findViewById(cg.getCheckedChipId());

        String[] value = ((String) chip.getText()).split(":");

        int hours = parseInt(value[0]);
        int minutes = parseInt(value[1]);

        bookingdate.setHours(hours);
        bookingdate.setMinutes(minutes);

        BookingEntity entity = new BookingEntity();
        entity.setNumberPersons(number);
        entity.setDate(bookingdate);

        return entity;


    }


}