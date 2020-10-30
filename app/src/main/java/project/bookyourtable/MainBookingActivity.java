package project.bookyourtable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static java.sql.Types.INTEGER;

public class MainBookingActivity extends AppCompatActivity {



    public MainBookingActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_booking);
    }

    public void continueBooking(View view){
        Intent intent = new Intent(this, BookingDatasActivity.class);
        startActivity(intent);
    }


    public void addPerson(View view){

        EditText numberPersons = (EditText)findViewById(R.id.editTextNumber);
        int number = Integer.parseInt(numberPersons.getText().toString().trim());
        number = number +1;
        displayNumbrePersons(numberPersons, number);
    }

    private void displayNumbrePersons(EditText numberPersons, int i) {
        numberPersons.setText("" + i);
    }

    public void decreasePerson(View view){

        EditText numberPersons = findViewById(R.id.editTextNumber);
        int number = Integer.parseInt(numberPersons.getText().toString().trim());

        if(number<=0){
            displayNumbrePersons(numberPersons, 0);
        }
        else
            displayNumbrePersons(numberPersons,number-1);

    }

    
}