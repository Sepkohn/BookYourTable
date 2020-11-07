package project.bookyourtable.ui.booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import project.bookyourtable.R;

public class BookingsDateActivity extends AppCompatActivity {

    Date bookingdate;
    public static final String MY_DATE = ".project.bookyourtable.ui.booking.Date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings_date);

        CalendarView view = findViewById(R.id.calendarView);

        view.setOnDateChangeListener((arg0, year, month, date) -> bookingdate = new Date(year-1900, month, date));

    }

    public void getReservationsList(View view){
        Intent intent = new Intent(this, ReservationsListActivity.class);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        intent.putExtra(MY_DATE, df.format(bookingdate));


        startActivity(intent);
    }
}