package project.bookyourtable.ui.booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLOutput;

import project.bookyourtable.R;
import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.util.OnAsyncEventListener;
import project.bookyourtable.viewmodel.booking.BookingViewModel;
import project.bookyourtable.viewmodel.booking.CreateBookingViewModel;


public class BookingDatasActivity extends AppCompatActivity {

    BookingEntity entity;
    CreateBookingViewModel createBookingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_datas);

        Intent intent = getIntent();
        this.entity = (BookingEntity) intent.getSerializableExtra(MainBookingActivity.MY_ENTITY);

        CreateBookingViewModel.Factory factory = new CreateBookingViewModel.Factory(getApplication());
        createBookingViewModel = new ViewModelProvider(this, factory).get(CreateBookingViewModel.class);

    }

    public void validateBooking(View view){
        if(verifyFormations()) {
            createBookingViewModel.createBooking(entity, new OnAsyncEventListener() {

                Toast toast;
                @Override
                public void onSuccess() {
                    toast = Toast.makeText(getApplicationContext(),"ITS OKAY",Toast.LENGTH_LONG);
                    toast.show();
                }

                @Override
                public void onFailure(Exception e) {
                    toast = Toast.makeText(getApplicationContext(),"ITS NOT NOT NOT OKAY",Toast.LENGTH_LONG);
                    toast.show();

                }
            });

            Intent intent = new Intent(BookingDatasActivity.this, ConfirmationActivity.class);
            startActivity(intent);

        }
    }

    private boolean verifyFormations() {
        //vérfifier les tables

        EditText name = findViewById(R.id.nameHint);
        String clientName = name.getText().toString();

        EditText phoneNumber = findViewById(R.id.editNumber);
        String clientPhoneNumber = phoneNumber.getText().toString();

        EditText message = findViewById(R.id.commentHint);
        String clientMessage = message.getText().toString();

        if(clientName!=""&&clientPhoneNumber!=""){
            //entity.setTableNumber();
            entity.setName(clientName);
            entity.setTelephoneNumber(clientPhoneNumber);
            entity.setMessage(clientMessage);
            return true;
        }
        return false;
    }
}