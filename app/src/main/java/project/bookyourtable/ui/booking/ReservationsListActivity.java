package project.bookyourtable.ui.booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import project.bookyourtable.R;
import project.bookyourtable.ui.MainActivity;

public class ReservationsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations_list);
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