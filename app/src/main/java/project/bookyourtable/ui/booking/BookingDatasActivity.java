package project.bookyourtable.ui.booking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import project.bookyourtable.BaseApp;
import project.bookyourtable.R;
import project.bookyourtable.adapter.RecyclerAdapter;
import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.util.OnAsyncEventListener;
import project.bookyourtable.util.RecyclerViewItemClickListener;
import project.bookyourtable.viewmodel.booking.CreateBookingViewModel;
import project.bookyourtable.viewmodel.table.TableListViewModel;

public class BookingDatasActivity extends AppCompatActivity {

    private static final String TAG = "BookingDatasActivity";
    BookingEntity entity;
    CreateBookingViewModel createBookingViewModel;


    //><<<<<<<<<<<<<<<<<<<<<<<<<< RAJOUTE QUENTIN
    private TableListViewModel viewModel;
    private List<TableEntity> tables;
    private RecyclerAdapter<TableEntity> adapter;
    //><<<<<<<<<<<<<<<<<<<<<<<<<< RAJOUTE QUENTIN

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_datas);

        Intent intent = getIntent();
        this.entity = (BookingEntity) intent.getSerializableExtra(MainBookingActivity.MY_ENTITY);

        CreateBookingViewModel.Factory factory = new CreateBookingViewModel.Factory((BaseApp) getApplication());
        createBookingViewModel = new ViewModelProvider(this, factory).get(CreateBookingViewModel.class);

        //>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< RAJOUTE QUENTIN
        RecyclerView recyclerView = findViewById(R.id.tableRecyclerView3);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        tables = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {

            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + tables.get(position).getId());


                if(tables.get(position).getAvailability()==true){
                    tables.get(position).setAvailability(false);
                }else{
                    tables.get(position).setAvailability(true);
                }
                Log.d(TAG, "Availability:" + tables.get(position).getAvailability());
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });

        TableListViewModel.Factory factory2 = new TableListViewModel.Factory(
                getApplication());

        viewModel = ViewModelProviders.of(this, factory2).get(TableListViewModel.class);
        viewModel.getOwnTables().observe(this, tableEntities -> {
            if (tableEntities != null) {
                tables = tableEntities;
                adapter.setData(tables);
            }
        });

        recyclerView.setAdapter(adapter); // refresh l'adaptater, toutes les vues dans le recycler sont rafraichies
        adapter.notifyDataSetChanged();

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    }

    public void validateBooking(View view){
        if(verifyFormations()) {
            createBookingViewModel.createBooking(entity, new OnAsyncEventListener() {

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
            Toast toast = Toast.makeText(this,"Please select a table and fill your name and number",Toast.LENGTH_LONG);
            toast.show();
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

        if(!clientName.equals("")&&!clientPhoneNumber.equals("")){
            //entity.setTableNumber();
            entity.setName(clientName);
            entity.setTelephoneNumber(clientPhoneNumber);
            entity.setMessage(clientMessage);
            return true;
        }
        return false;
    }
}