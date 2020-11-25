package project.bookyourtable.ui.booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import project.bookyourtable.R;
import project.bookyourtable.adapter.RecyclerAdapter;
import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.database.repository.BookingRepository;
import project.bookyourtable.util.OnAsyncEventListener;
import project.bookyourtable.util.RecyclerViewItemClickListener;
import project.bookyourtable.viewmodel.booking.BookingViewModel;
import project.bookyourtable.viewmodel.table.AvailableTableListViewModel;

public class ChangeTableActivity extends AppCompatActivity {

    private Button validate;
    private static final String TAG = "ChangeTableActivity";

    private BookingViewModel bookingViewModel;
    private BookingViewModel.Factory factory;

    private AvailableTableListViewModel tableViewModel;
    private List<TableEntity> tables;
    private RecyclerAdapter<TableEntity> adapter;
    private RecyclerView recyclerView;

    private BookingEntity bookingEntity;
    private long tableNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_table);
        validate = findViewById(R.id.buttonAdd);

        validate.setText(R.string.validate);
        validate.setOnClickListener(v -> validateTable());

        this.bookingEntity = (BookingEntity) getIntent().getSerializableExtra(ChangeDatasActivity.GET_ENTITY_ID);

        factory = new BookingViewModel.Factory(getApplication(), bookingEntity.getId());
        bookingViewModel = new ViewModelProvider(this,factory).get(BookingViewModel.class);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView= findViewById(R.id.tableRecyclerView);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        tables = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {

            public void onItemClick(View v, int position) {

            Toast.makeText(ChangeTableActivity.this,getString(R.string.selectedTable,""+tables.get(position).getId()), Toast.LENGTH_LONG).show();
            tableNo = Long.parseLong(tables.get(position).getId());

            }

            @Override
            public void onItemLongClick(View v, int position) {
                //no action
            }
        });

        AvailableTableListViewModel.Factory factory2 = new AvailableTableListViewModel.Factory(
                getApplication(),bookingEntity.getNumberPersons());

        tableViewModel = new ViewModelProvider(this, factory2).get(AvailableTableListViewModel.class);
        tableViewModel.getOwnTables().observe(this, tableEntities -> {
            if (tableEntities != null) {
                List<TableEntity> newTables = new ArrayList<>();
                BookingRepository.getInstance().getBookingsByDateTime(bookingEntity.getDate(), bookingEntity.getTime()).observe(this, bookingEntities -> {
                    if(bookingEntities.size()>0) {
                        for(TableEntity tableEntity : tableEntities){
                            for (BookingEntity entity : bookingEntities) {
                                if(!(entity.getTableNumber().equals(tableEntity.getId()))&&newTables.indexOf(tableEntity)!=-1){
                                    newTables.add(tableEntity);
                                }
                            }
                        }
                        tables = newTables;
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

    private void validateTable() {
        if(tableNo!=0){
            bookingEntity.setTableNumber(String.valueOf(tableNo));
            bookingViewModel.updateBooking(bookingEntity, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "Update executed correctly");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "There was an error, the update was not registered");
                }
            });

            Intent intent = new Intent(this, ConfirmationActivity.class);
            startActivity(intent);

        }
    }

    private void displayTables() {
        adapter.setData(tables);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}