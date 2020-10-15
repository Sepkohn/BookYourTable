package project.bookyourtable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BookingDatasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_datas);
    }

    public void validateBooking(View view){
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
    }
}