package project.bookyourtable.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import project.bookyourtable.R;
import project.bookyourtable.ui.booking.BookingsDateActivity;
import project.bookyourtable.ui.booking.MainBookingActivity;
import project.bookyourtable.ui.table.MainTableManagement;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createBooking(View view){
        Intent intent = new Intent(this, MainBookingActivity.class);
        startActivity(intent);
    }

    public void modifyBooking(View view){
        Intent intent = new Intent(this, BookingsDateActivity.class);
        startActivity(intent);
    }
    public void createTableManagement(View view){
        Intent intent = new Intent(this, MainTableManagement.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        return;
    }

}