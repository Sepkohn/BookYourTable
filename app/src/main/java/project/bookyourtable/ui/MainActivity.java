package project.bookyourtable.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import project.bookyourtable.R;
import project.bookyourtable.ui.booking.BookingsDateActivity;
import project.bookyourtable.ui.booking.MainBookingActivity;
import project.bookyourtable.ui.table.TableActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_about:
                Toast.makeText(MainActivity.this, "action_about clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.item2:
                Toast.makeText(MainActivity.this, "action_item2 clicked", Toast.LENGTH_LONG).show();
                return true;
            case R.id.item3:
                Toast.makeText(MainActivity.this, "action_item3 clicked", Toast.LENGTH_LONG).show();
                return true;
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

    @Override
    public void onBackPressed() {
        return;
    }

}