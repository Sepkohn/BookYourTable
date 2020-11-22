package project.bookyourtable.ui.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.time.LocalDate;
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
    private LocalDate bookingDate;

    private boolean haveToChangedTable = false;

    public static final String GET_ENTITY_ID = ".project.bookyourtable.ui.booking.GETENTITYID";

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_datas);


        Intent intent = getIntent();
        this.entity = (BookingEntity) intent.getSerializableExtra(ReservationsListActivity.MODIFY_ENTITY);

        getElements();
        setValues();
        calendar.setOnDateChangeListener((arg0, year, month, date) -> bookingDate = LocalDate.of(year, month-1, date));

        factory = new BookingViewModel.Factory(getApplication(), entity.getId());
        viewModel = new ViewModelProvider(this,factory).get(BookingViewModel.class);

    }

    /**
     * Get the different fields and initialize the ChipGroup action Listener
     */
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

    /**
     * Get values from database and put in the fields
     */
    private void setValues(){
        customerName.setText(entity.getName());
        numberPersons.setText(""+entity.getNumberPersons());
        LocalDate myDate = entity.getDate();
        calendar.setDate(Long.parseLong(new Date(myDate.getYear(), myDate.getMonthValue(), myDate.getDayOfYear()).toString()));
        bookingDate = entity.getDate();

        System.out.println();
        setPeriod();
    }

    /**
     *Check the corresponding period slot
     */
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

    /**
     * Check the corresponding time slot
     */
    private void setTime(){
        for(int i = 0; i<timeSlot.getChildCount();i++){
            Chip slot = (Chip)timeSlot.getChildAt(i);
            if(slot.getText().equals(entity.getTime())){
                slot.setChecked(true);
                return;
            }
        }
    }


    /**
     * Method to validate the modifying. We still have to check the informations before continuing.
     * @param view
     */
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
            if(haveToChangedTable){
                entity.setTableNumber(null);
                Intent intent = new Intent(this, ChangeTableActivity.class);
                intent.putExtra(GET_ENTITY_ID,entity);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(this, ConfirmationActivity.class);
                startActivity(intent);
            }
        }
    }

    /**
     * Check all the input informations
     * @return true if inputs are correct
     */
    private boolean checkInformations(){
        String name = customerName.getText().toString();
        int number = parseInt(numberPersons.getText().toString());
        int time = timeSlot.getCheckedChipId();
        LocalDate today = LocalDate.now();

        if(!name.equals("")&&number>0&&!bookingDate.isBefore(today)&&time!=-1) {
            if(number!=entity.getNumberPersons()){
                haveToChangedTable = true;
            }
            entity.setName(name);
            entity.setDate(bookingDate);
            entity.setNumberPersons(number);
            Chip chip = findViewById(time);
            entity.setTime(chip.getText().toString());

            return true;
        }
        else{
            Toast.makeText(this,"Please enter valid datas", Toast.LENGTH_LONG);
            return false;
        }
    }

    /**
     * Increase the number of perons in the editText
     * @param view
     */
    public void addPerson(View view){

        int number = parseInt(numberPersons.getText().toString().trim());
        number = number +1;
        displayNumbrePersons(number);
    }

    /**
     * Set the value in the editText
     * @param i = number
     */
    private void displayNumbrePersons(int i) {
        numberPersons.setText("" + i);
    }

    /**
     * Decrease the number of perons in the editText
     * @param view
     */
    public void decreasePerson(View view){

        int number = parseInt(numberPersons.getText().toString().trim());

        if(number<=1){
            displayNumbrePersons(1);
        }
        else
            displayNumbrePersons(number-1);

    }
}