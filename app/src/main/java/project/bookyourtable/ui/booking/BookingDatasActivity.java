package project.bookyourtable.ui.booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import project.bookyourtable.R;
import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.util.OnAsyncEventListener;
import project.bookyourtable.viewmodel.booking.BookingViewModel;


public class BookingDatasActivity extends AppCompatActivity {

    BookingEntity entity;
    BookingViewModel bookingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_datas);

        Intent intent = getIntent();
        this.entity = (BookingEntity) intent.getSerializableExtra(MainBookingActivity.MY_ENTITY);

        //BookingViewModel.Factory factory = new BookingViewModel.Factory(getApplication(),entity.getId());

    }

    public void validateBooking(View view){
        if(verifyFormations()) {
            bookingViewModel.createBooking(entity, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(Exception e) {
                }
            });

            Intent intent = new Intent(this, ConfirmationActivity.class);
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
            entity.setDate(entity.getCalendar());
            return true;
        }
        return false;
    }
}