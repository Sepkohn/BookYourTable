package project.bookyourtable.ui.booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import project.bookyourtable.BaseApp;
import project.bookyourtable.R;
import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.database.repository.BookingRepository;
import project.bookyourtable.database.repository.TableRepository;
import project.bookyourtable.ui.MainActivity;

public class ReservationsListActivity extends AppCompatActivity {

    private Date date;
    private BookingRepository repository;
    private LiveData<List<BookingEntity>> bookings;
    private ChipGroup cg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations_list);

        Intent intent = getIntent();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        try {
            date = (Date) df.parse(intent.getStringExtra(BookingsDateActivity.MY_DATE));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        getBookings();
        displayBookings();
    }

    private void getBookings() {
        repository = ((BaseApp) getApplication()).getBookingRepository();
        bookings =  repository.getBookingsByDate(date, getApplication());
    }

    private void displayBookings() {
        cg = findViewById(R.id.reservationChips);
        bookings.observe(this, bookingEntities -> {
            if (bookingEntities != null) {
                System.out.println("The  list is not null");
                for (BookingEntity entity : bookingEntities) {
                    createChip(entity);
                }
            } else
                System.out.println("The list is null");

        });
    }


    private void createChip(BookingEntity entity) {
        String name = entity.getName();
        String comment = entity.getMessage();
        String time = entity.getTime();

        String basis = name+ "/n" + "pour n personne ("+time+")";
        if(comment!=""){
            basis+="/n" + comment;
        }

        //TableEntity table = TableRepository.getInstance().getTableById(entity.getTableNumber(),this).getValue();

        Chip chip = new Chip(this);
        chip.setText(basis);
        chip.setId(entity.getId().intValue());
        chip.setCheckable(true);
        chip.setChipBackgroundColorResource(R.color.colorAccent);
        cg.addView(chip);
    }



    public void backToMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void modifyReservation(View view){
        Intent intent = new Intent(this, ChangeDatasActivity.class);
        startActivity(intent);
    }

    public void deleteReservation(View view){
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
    }


}