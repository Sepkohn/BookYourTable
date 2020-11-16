package project.bookyourtable.ui.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import project.bookyourtable.BaseApp;
import project.bookyourtable.R;
import project.bookyourtable.adapter.BookingsRecyclerAdapter;
import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.database.repository.BookingRepository;
import project.bookyourtable.ui.MainActivity;
import project.bookyourtable.util.OnAsyncEventListener;
import project.bookyourtable.util.RecyclerViewItemClickListener;
import project.bookyourtable.viewmodel.booking.BookingViewModel;

public class ReservationsListActivity extends AppCompatActivity {

    private Date date;
    private BookingRepository repository;

    private List<BookingEntity> displayBookings;
    private BookingsRecyclerAdapter<BookingEntity> adapter;

    private BookingViewModel viewModel;
    private BookingViewModel.Factory factory;
    private BookingEntity selectedEntity;

    public static final String MODIFY_ENTITY = ".project.bookyourtable.ui.booking.MODIFYENTITY";

    /**
     * Creation of the Reservation list.
     * We get the date of the last activity to get all the bookings from this date
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations_list);

        Intent intent = getIntent();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        try {
            date = df.parse(intent.getStringExtra(BookingsDateActivity.MY_DATE));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = findViewById(R.id.tableRecyclerView3);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        displayBookings = new ArrayList<>();
        this.adapter = new BookingsRecyclerAdapter<>(new RecyclerViewItemClickListener() {

            public void onItemClick(View v, int position) {

                Toast.makeText(ReservationsListActivity.this, " booking No " + displayBookings.get(position).getId() + " selected", Toast.LENGTH_LONG).show();
                selectedEntity = displayBookings.get(position);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                dialogCustomerDetails(position);
            }
        });

        repository = ((BaseApp) getApplication()).getBookingRepository();
        repository.getBookingsByDate(date, getApplication()).observe(this, new Observer<List<BookingEntity>>() {
            @Override
            public void onChanged(List<BookingEntity> bookingEntities) {
                displayBookings = bookingEntities;
                adapter.setData(displayBookings);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

    }

    /**
     * Get the BookingEntity's person information to display in a dialog
     * @param position
     */
    private void dialogCustomerDetails(final int position) {
        final BookingEntity bookingEntity = displayBookings.get(position);
        LayoutInflater inflater = LayoutInflater.from(this);

        final View view = inflater.inflate(R.layout.row_delete_item, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setCancelable(false);

        final TextView customerDetails = view.findViewById(R.id.tv_delete_item);

        String text = "Name :" +  bookingEntity.getName() + "\n"
                + "Telephone number : " + bookingEntity.getTelephoneNumber();

        if(!bookingEntity.getMessage().equals(null))
            text = text + "\n" + "Comment : " + bookingEntity.getMessage();
        customerDetails.setText(String.format(text));
        customerDetails.setTextSize(15);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Close", (dialog, which) -> alertDialog.dismiss());
        alertDialog.setView(view);
        alertDialog.show();
    }

    /**
     * return to the MainActivity
     * @param view
     */
    public void backToMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void modifyReservation(View view){
        initialiseViewModel("modify");
    }

    /**
     * Start the ChangeDatasActivity with the selected BookingEntity
     */
    private void modifySelectedBooking() {
        Intent intent = new Intent(this, ChangeDatasActivity.class);
        intent.putExtra(MODIFY_ENTITY, selectedEntity);
        startActivity(intent);
    }


    public void deleteReservation(View view) {
        initialiseViewModel("delete");
    }

    /**
     * Delete the selected entity from the database
     */
    private void deleteSelectedBooking(){
        if(selectedEntity!=null) {
            viewModel.deleteBooking(selectedEntity, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    System.out.println("DELETED");
                }

                @Override
                public void onFailure(Exception e) {
                    System.out.println("NOT NOT NOT DELETED");
                }
            });

            Intent intent = new Intent(this, ConfirmationActivity.class);
            startActivity(intent);
        }
        else Toast.makeText(getApplication(),"The entity selected is not valid", Toast.LENGTH_LONG);
    }

    /**
     * Initialize the view model to modify or delete depending on the state
     * @param state = delete or modify
     */
    private synchronized void initialiseViewModel(String state){
        if(selectedEntity!=null){
            long id  = selectedEntity.getId();
            factory = new BookingViewModel.Factory(getApplication(), id);
            viewModel = new ViewModelProvider(this,factory).get(BookingViewModel.class);
            viewModel.getBooking().observe(this, bookingEntity -> {
                if(bookingEntity!=null) {
                    selectedEntity = bookingEntity;
                    switch(state) {
                        case ("delete"):
                            deleteSelectedBooking();
                        break;
                        case("modify"):
                            modifySelectedBooking();
                            break;
                        default:
                            Toast.makeText(this, "Invalid action", Toast.LENGTH_LONG);
                    }
                }
            });
        }
    }




}