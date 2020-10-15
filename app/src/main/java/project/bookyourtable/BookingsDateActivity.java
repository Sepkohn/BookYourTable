package project.bookyourtable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BookingsDateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings_date);
    }

    public void getReservationsList(View view){
        Intent intent = new Intent(this, ReservationsListActivity.class);
        startActivity(intent);
    }
}