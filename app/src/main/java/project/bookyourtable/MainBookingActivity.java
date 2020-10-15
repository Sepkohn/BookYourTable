package project.bookyourtable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainBookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_booking);
    }

    public void continueBooking(View view){
        Intent intent = new Intent(this, BookingDatasActivity.class);
        startActivity(intent);
    }
}