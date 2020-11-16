package project.bookyourtable.ui.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
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

    private Date bookingdate;
    private ChipGroup timeSlot;
    private Chip midday;
    private Chip evening;
    private Chip slot1;
    private Chip slot2;
    private Chip slot3;
    private Chip slot4;
    private Chip slot5;
    private EditText numberPersons;

    public static final String MY_ENTITY = ".project.bookyourtable.ui.booking.ENTITY";

    /**
     * Creation of the MainBookingActivity
     * We link the screen's fields and initialize the Calender Listener
     * @param savedInstanceState
     */
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

        numberPersons  = findViewById(R.id.editTextNumber);

        setDefaultValues();

    }

    /**
     * initialise the period's check listener and set a default value
     */
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

    /**
     * Method to continue the booking. We still have to check the informations before continuing.
     * @param view
     */
    public void continueBooking(View view){
        BookingEntity entity;
        if((entity=verifyInformations()) !=null) {
            Intent intent = new Intent(this, BookingDatasActivity.class);
            intent.putExtra(MY_ENTITY, entity);
            startActivity(intent);
        }
        else{
            Toast toast = Toast.makeText(this, R.string.selectAll,Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * Increase the number of perons in the editText
     * @param view
     */
    public void addPerson(View view){

        EditText numberPersons = findViewById(R.id.editTextNumber);
        int number = parseInt(numberPersons.getText().toString().trim());
        number = number +1;
        displayNumberPersons(numberPersons, number);
    }

    /**
     * Set the value in the editText
     * @param i = number
     */
    private void displayNumberPersons(EditText numberPersons, int i) {
        numberPersons.setText("" + i);
    }

    /**
     * Decrease the number of perons in the editText
     * @param view
     */
    public void decreasePerson(View view){

        EditText numberPersons = findViewById(R.id.editTextNumber);
        int number = parseInt(numberPersons.getText().toString().trim());

        if(number<=1){
            displayNumberPersons(numberPersons, 1);
        }
        else
            displayNumberPersons(numberPersons,number-1);

    }

    /**
     * Check all the input informations
     * @return true if inputs are correct
     */
    private BookingEntity verifyInformations() {


        int number = parseInt(numberPersons.getText().toString().trim());

        Date yesterday = new Date();
        yesterday.setDate(yesterday.getDate()-1);



        boolean isATimeSlot = verifyTimeSlot();
        if(bookingdate==null)
            bookingdate=new Date();

        if(number>0&&isATimeSlot&&!bookingdate.before(yesterday)){
           return createEntity(number);
        }

        return null;
    }

    /**
     * Check if a timeSlot is checked
     * @return true if a timeSlot is checked
     */
    private boolean verifyTimeSlot() {
        int c = timeSlot.getCheckedChipId();

        return c != -1;
    }

    /**
     * Create a BookingEntity with the basics informations
     * @param number
     * @return a new BookingEntity
     */
    private BookingEntity createEntity(int number){
        Chip chip = findViewById(timeSlot.getCheckedChipId());

        String time = chip.getText().toString();

        BookingEntity entity = new BookingEntity();
        entity.setNumberPersons(number);
        if(bookingdate==null)
            bookingdate=new Date();
        entity.setDate(bookingdate);
        entity.setTime(time);

        return entity;


    }


}