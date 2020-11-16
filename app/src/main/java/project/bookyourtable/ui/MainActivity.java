package project.bookyourtable.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import project.bookyourtable.R;
import project.bookyourtable.ui.booking.BookingsDateActivity;
import project.bookyourtable.ui.booking.MainBookingActivity;
import project.bookyourtable.ui.table.TableActivity;
import project.bookyourtable.util.SettingsManager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }

    /** Inflate the menu; this adds items to the action bar if it is present.*/
    public boolean onCreateOptionsMenu(Menu menu) {
        //
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }
/**     Handle action bar item clicks here. The action bar will
 automatically handle clicks on the Home/Up button, so long
 as you specify a parent activity in AndroidManifest.xml.*/
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.action_about:
                Toast.makeText(MainActivity.this, "action_about clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.DarkMode:
                SettingsManager.setDarkMode(MainActivity.this);
                return true;
            case R.id.frenchLanguage:
                SettingsManager.setLocale(MainActivity.this,"fr");
                MainActivity.this.onResume();
                return true;
            case R.id.englishLanguage:
                SettingsManager.setLocale(MainActivity.this,"es");
        }
        return super.onOptionsItemSelected(item);
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

    public void changeToFrench(View view){

    }




    @Override
    public void onBackPressed() {
        return;
    }

}