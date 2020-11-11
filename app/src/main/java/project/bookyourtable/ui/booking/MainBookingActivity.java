package project.bookyourtable.ui.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Date;

import project.bookyourtable.R;
import project.bookyourtable.database.entity.BookingEntity;

import static java.lang.Integer.parseInt;

public class MainBookingActivity extends AppCompatActivity {

    Date bookingdate;
    ChipGroup timeSlot;
    Chip midday;
    Chip evening;
    Chip slot1;
    Chip slot2;
    Chip slot3;
    Chip slot4;
    Chip slot5;

    public static final String MY_ENTITY = ".project.bookyourtable.ui.booking.ENTITY";



    public MainBookingActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_booking);

        CalendarView view = findViewById(R.id.calendarView);

        view.setOnDateChangeListener((arg0, year, month, date) -> bookingdate = new Date(year-1900, month, date));

        timeSlot = findViewById(R.id.timeSlots);
        midday = findViewById(R.id.midday);
        evening = findViewById(R.id.evening);
        slot1 = findViewById(R.id.slot1);
        slot2 = findViewById(R.id.slot2);
        slot3 = findViewById(R.id.slot3);
        slot4 = findViewById(R.id.slot4);
        slot5 = findViewById(R.id.slot5);

        setDefaultValues();

    }

    public void setDefaultValues(){
        midday.setOnCheckedChangeListener((buttonView, isChecked) -> {
           if(isChecked) {
               slot1.setText(R.string.midday1);
               slot2.setText(R.string.midday2);
               slot3.setText(R.string.midday3);
               slot4.setText(R.string.midday4);
               slot5.setText(R.string.midday5);
           }
        });

        evening.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                slot1.setText(R.string.evening1);
                slot2.setText(R.string.evening2);
                slot3.setText(R.string.evening3);
                slot4.setText(R.string.evening4);
                slot5.setText(R.string.evening5);
            }
        });

        midday.setChecked(true);
    }

    public void continueBooking(View view){
        BookingEntity entity;
        if((entity=verifyInformations()) !=null) {
            Intent intent = new Intent(this, BookingDatasActivity.class);
            intent.putExtra(MY_ENTITY, entity);
            startActivity(intent);
        }
        else{
            Toast toast = Toast.makeText(this,"Please select all values to continue",Toast.LENGTH_LONG);
            toast.show();
        }
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

    private BookingEntity verifyInformations() {

        EditText numberPersons = findViewById(R.id.editTextNumber);
        int number = parseInt(numberPersons.getText().toString().trim());

        Date today = new Date();



        boolean isATimeSlot = verifyTimeSlot();

        if(number>0&&isATimeSlot&&bookingdate.compareTo(today)>=0){
           return createEntity(number);
        }

        return null;
    }

    private boolean verifyTimeSlot() {


        int c = timeSlot.getCheckedChipId();

        return c != -1;
    }

    private BookingEntity createEntity(int number){
        Chip chip = findViewById(timeSlot.getCheckedChipId());

        String time = chip.getText().toString();

        BookingEntity entity = new BookingEntity();
        entity.setNumberPersons(number);
        entity.setDate(bookingdate);
        entity.setTime(time);

        return entity;


    }


}