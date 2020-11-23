package project.bookyourtable.ui.booking;

import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import project.bookyourtable.R;
import project.bookyourtable.adapter.RecyclerAdapter;
import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.database.repository.BookingRepository;
import project.bookyourtable.util.OnAsyncEventListener;
import project.bookyourtable.util.RecyclerViewItemClickListener;
import project.bookyourtable.viewmodel.booking.CreateBookingViewModel;
import project.bookyourtable.viewmodel.table.AvailableTableListViewModel;

public class BookingDatasActivity extends AppCompatActivity {

    private static final String TAG = "BookingDatasActivity";
    private BookingEntity entity;
    private CreateBookingViewModel createBookingViewModel;
    private String tableNo;

    private AvailableTableListViewModel viewModel;
    private List<TableEntity> tables;
    private RecyclerAdapter<TableEntity> adapter;
    private RecyclerView recyclerView;

    /**
     * Creation of the BookingDatasActivity
     * We link the screen's fields and create the recycler to get the tables
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_datas);

        Intent intent = getIntent();
        entity = (BookingEntity) intent.getSerializableExtra(MainBookingActivity.MY_ENTITY);

        CreateBookingViewModel.Factory factory = new CreateBookingViewModel.Factory(getApplication());
        createBookingViewModel = new ViewModelProvider(this, factory).get(CreateBookingViewModel.class);

        recyclerView= findViewById(R.id.tableRecyclerView3);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        tables = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {

            public void onItemClick(View v, int position) {
                Toast.makeText(BookingDatasActivity.this,getString(R.string.selectedTable,""+tables.get(position).getId()), Toast.LENGTH_LONG).show();
                tableNo = tables.get(position).getId();
            }

            @Override
            public void onItemLongClick(View v, int position) {
            }
        });

        AvailableTableListViewModel.Factory factory2 = new AvailableTableListViewModel.Factory(getApplication(),entity.getNumberPersons());

        viewModel = new ViewModelProvider(this, factory2).get(AvailableTableListViewModel.class);
        viewModel.getOwnTables().observe(this, tableEntities -> {
            if (tableEntities != null) {
                BookingRepository.getInstance().getBookingsByDateTime(entity.getDate(), entity.getTime()).observe(this, bookingEntities -> {
                    if(bookingEntities.size()>0) {
                        for (BookingEntity entity : bookingEntities) {
                            for(int i = 0; i<tableEntities.size();i++){
                                if(!tableEntities.get(i).getId().equals(entity.getTableNumber())){
                                    tables.add(tableEntities.get(i));
                                }
                            }
                        }
                        displayTables();
                    }
                    else {
                        tables = tableEntities;
                        displayTables();
                    }
                });
            }
        });



    }

    private void displayTables() {
        adapter.setData(tables);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * Method to continue the booking. We still have to check the informations before continuing.
     * @param view
     */
    public void validateBooking(View view){
        if(verifyInformations()) {

            BookingEntity newEntity = entity;

            createBookingViewModel.createBooking(newEntity, new OnAsyncEventListener() {

                @Override
                public void onSuccess() {
                   Log.d(TAG, "Create booking ok");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "Create booking NOT NOT NOT okay", e);

                }
            });

            Intent intent = new Intent(BookingDatasActivity.this, ConfirmationActivity.class);
            startActivity(intent);

        }
        else{
            Toast.makeText(this, R.string.emptyTable,Toast.LENGTH_LONG).show();

        }
    }

    /**
     * Check all the input informations
     * @return true if inputs are correct
     */
    private boolean verifyInformations() {
        EditText name = findViewById(R.id.nameHint);
        String clientName = validateName(name);

        EditText phoneNumber = findViewById(R.id.editNumber);
        String clientPhoneNumber = validateNumber(phoneNumber);

        EditText message = findViewById(R.id.commentHint);
        String clientMessage = message.getText().toString();

        if(!clientName.equals("")&&!clientPhoneNumber.equals("")&&!tableNo.equals("")){
            entity.setTableNumber(tableNo);
            entity.setName(clientName);
            entity.setTelephoneNumber(clientPhoneNumber);
            entity.setMessage(clientMessage);
            return true;
        }
        return false;
    }

    /**
     * check the input telephoneNumber and put error message if is empty
     * @param phoneNumber = editText field
     * @return the input if not empty
     */
    private String validateNumber(EditText phoneNumber) {
        String stringNumber = phoneNumber.getText().toString();
        if (!stringNumber.isEmpty()) {

            if(stringNumber.length()<11)
            {
                phoneNumber.setError("The format is incorrect, please try again");
                phoneNumber.requestFocus();
                return "";
            }

            return stringNumber;
        }
        else{
            phoneNumber.setError(getString(R.string.emptyTelephone));
            phoneNumber.requestFocus();
            return "";
        }
    }

    /**
     * check the input name and put error message if is empty
     * @param name  = editText field
     * @return the input if not empty
     */
    private String validateName(EditText name) {
        String stringName = name.getText().toString();
        if (!stringName.isEmpty()) {
            return stringName;
        } else {
            name.setError(getString(R.string.emptyName));
            name.requestFocus();
            return "";
        }
    }
}