package project.bookyourtable.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import project.bookyourtable.BaseApp;
import project.bookyourtable.R;
import project.bookyourtable.ui.booking.BookingsDateActivity;
import project.bookyourtable.ui.booking.MainBookingActivity;
import project.bookyourtable.ui.table.TableActivity;
import project.bookyourtable.util.LanguageManager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LanguageManager.setLocale(this,"es");
        LanguageManager.setLocale(this,"fr");
        setContentView(R.layout.activity_main);

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
        Intent intent = new Intent(this, TableActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        return;
    }

}