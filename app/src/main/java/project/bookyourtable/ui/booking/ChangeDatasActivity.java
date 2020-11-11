package project.bookyourtable.ui.booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import project.bookyourtable.R;
import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.util.OnAsyncEventListener;
import project.bookyourtable.viewmodel.booking.BookingViewModel;

import static java.lang.Integer.parseInt;


public class ChangeDatasActivity extends AppCompatActivity {


    private BookingEntity entity;
    private EditText customerName;
    private EditText numberPersons;
    private CalendarView calendar;
    private ChipGroup timeSlot;

    private Chip midday;
    private Chip evening;
    private Chip slot1;
    private Chip slot2;
    private Chip slot3;
    private Chip slot4;
    private Chip slot5;

    private BookingViewModel viewModel;
    private BookingViewModel.Factory factory;
    Date bookingdate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_datas);


        Intent intent = getIntent();
        this.entity = (BookingEntity) intent.getSerializableExtra(ReservationsListActivity.MODIFY_ENTITY);

        getElements();
        setValues();
        calendar.setOnDateChangeListener((arg0, year, month, date) -> bookingdate = new Date(year-1900, month, date));

        factory = new BookingViewModel.Factory(getApplication(), entity.getId());
        viewModel = new ViewModelProvider(this,factory).get(BookingViewModel.class);

    }

    private void getElements() {
        customerName = findViewById(R.id.nameHint);
        numberPersons = findViewById(R.id.editTextNumber);
        calendar = findViewById(R.id.calendarView);
        timeSlot = findViewById(R.id.timeSlots);
        midday = findViewById(R.id.midday);
        evening = findViewById(R.id.evening);
        slot1 = findViewById(R.id.slot1);
        slot2 = findViewById(R.id.slot2);
        slot3 = findViewById(R.id.slot3);
        slot4 = findViewById(R.id.slot4);
        slot5 = findViewById(R.id.slot5);

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
    }

    private void setValues(){
        customerName.setText(entity.getName());
        numberPersons.setText(""+entity.getNumberPersons());
        calendar.setDate(entity.getDate().getTime());
        bookingdate = entity.getDate();

        System.out.println();
        setPeriod();
    }

    private void setPeriod(){
        int hour = Integer.parseInt(entity.getTime().split(":")[0]);

        if(hour<15){
            midday.setChecked(true);
        }
        else{
            evening.setChecked(true);
        }
        setTime();
    }

    private void setTime(){
        for(int i = 0; i<timeSlot.getChildCount();i++){
            Chip slot = (Chip)timeSlot.getChildAt(i);
            if(slot.getText().equals(entity.getTime())){
                slot.setChecked(true);
                return;
            }
        }
    }


    public void validateBooking(View view){
        if(checkInformations()) {
            viewModel.updateBooking(entity, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    System.out.println("Customer's booking correctly updated");
                }

                @Override
                public void onFailure(Exception e) {
                    System.out.println("ERROR ON UPDATE");
                }
            });
            Intent intent = new Intent(this, ConfirmationActivity.class);
            startActivity(intent);
        }
    }

    private boolean checkInformations(){
        String name = customerName.getText().toString();
        int number = parseInt(numberPersons.getText().toString());
        int time = timeSlot.getCheckedChipId();
        Date today = new Date();

        if(!name.equals("")&&number>0&&bookingdate.after(today)&&time!=-1) {
            entity.setName(name);
            entity.setDate(bookingdate);
            Chip chip = findViewById(time);
            entity.setTime(chip.getText().toString());
            return true;
        }
        else{
            Toast.makeText(this,"Please enter valid datas", Toast.LENGTH_LONG);
            return false;
        }
    }

    public void addPerson(View view){

        int number = parseInt(numberPersons.getText().toString().trim());
        number = number +1;
        displayNumbrePersons(number);
    }

    private void displayNumbrePersons(int i) {
        numberPersons.setText("" + i);
    }

    public void decreasePerson(View view){

        int number = parseInt(numberPersons.getText().toString().trim());

        if(number<=1){
            displayNumbrePersons(1);
        }
        else
            displayNumbrePersons(number-1);

    }
}