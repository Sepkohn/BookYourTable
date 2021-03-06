package project.bookyourtable.ui.table;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import project.bookyourtable.R;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.database.repository.BookingRepository;
import project.bookyourtable.database.repository.TableRepository;
import project.bookyourtable.util.OnAsyncEventListener;
import project.bookyourtable.viewmodel.table.TableViewModel;

/**
 * Adding or Edit table screen that offer to change/create number location and number person per table*/
public class EditTableActivity extends AppCompatActivity {

    private static final String TAG = "EditTableActivity";
    private TableEntity tableEntity;
    private boolean isEditMode;
    private Toast toast;
    private EditText etnumtable;
    private EditText etnumberperson;
    private Switch swBtn;
    private boolean statusSwitch;
    private boolean uniqueTable = true;
    private TableViewModel tableViewModel;
    private int locationInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_edit);

        etnumtable = findViewById(R.id.inputnumtable);
        etnumtable.requestFocus();
        etnumberperson = findViewById(R.id.intputnumberperson);


        swBtn = findViewById(R.id.swAvailability);
        swBtn.setText("");
        swBtn.setOnClickListener(view -> {
            if (swBtn.isChecked()) {
                statusSwitch = true;
            } else {
                statusSwitch = false;
            }
        });

        Button saveBtn = findViewById(R.id.btn_okDialogue);
        saveBtn.setOnClickListener(view -> {
            verifyInformations();

        });
        Button cancelBtn = findViewById(R.id.btn_cancelDialogue);
        cancelBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, TableActivity.class);
            startActivity(intent);
        });

        //Get if table ID exist for editing or not for adding mode

        String tableId = getIntent().getStringExtra("tableId");
        if (tableId == null) {
            setTitle(getString(R.string.createTable));
            toast = Toast.makeText(this, getString(R.string.createTable2), Toast.LENGTH_LONG);
            isEditMode = false;
        } else {
            setTitle(getString(R.string.editTable));
            saveBtn.setText(getString(R.string.addChange));
            cancelBtn.setText(getString(R.string.cancel));
            toast = Toast.makeText(this, getString(R.string.editTable2), Toast.LENGTH_LONG);
            isEditMode = true;
        }

        TableViewModel.Factory factory = new TableViewModel.Factory(
                getApplication(), tableId);
        tableViewModel = new ViewModelProvider(this, factory).get(TableViewModel.class);

        if (isEditMode) {
            tableViewModel.getTable().observe(this, table -> {
                if (table != null) {
                    tableEntity = table;
                    etnumtable.setText("" + tableEntity.getLocation());
                    etnumberperson.setText("" + tableEntity.getPersonNumber());
                    statusSwitch = tableEntity.getAvailability();
                    swBtn.setChecked(tableEntity.getAvailability());
                }
            });
        }
    }

    /**
     * Method to verrify if all field are fill before adding of save a table's modification*/
    private void verifyInformations() {
        String stringetnumtable = etnumtable.getText().toString();
        String stringetnumberperson = etnumberperson.getText().toString();
        uniqueTable = true;

        if(!isEditMode) {
            tableEntity = new TableEntity();
        }

        //check if Id is already used and save the table if it isn't
        TableRepository.getInstance().getByOwner().observe(this, tableEntities -> {
            if(tableEntities!=null){
                for(TableEntity table : tableEntities){
                    if(table.getLocation() == Integer.parseInt(stringetnumtable)){
                        uniqueTable = false;
                    }
                }
                if(isEditMode)
                    if(tableEntity.getLocation() == Integer.parseInt(stringetnumtable))
                        uniqueTable = true;
            }
            if (stringetnumtable.isEmpty()) {
                etnumtable.setError(getString(R.string.numberTableErr));
                etnumtable.requestFocus();
            } else if (stringetnumberperson.isEmpty()) {
                etnumberperson.setError(getString(R.string.numberPersonErr));
            } else {
                if(uniqueTable) {
                    saveChanges(Integer.parseInt(etnumtable.getText().toString()), Integer.parseInt(etnumberperson.getText().toString()), statusSwitch);
                    onBackPressed();
                    toast.show();
                    return;
                }
                else{
                    Toast.makeText(EditTableActivity.this, R.string.IdExists, Toast.LENGTH_SHORT ).show();
                }
            }
        });


    }

    /**
     * Method to set all information in tableEntity that offer an distinction between adding mode or edit mode*/
    private void saveChanges(int location, int personNumber, boolean state) {
            if (isEditMode) {
                int oldLocation = tableEntity.getLocation();
                tableEntity.setPersonNumber(personNumber);
                tableEntity.setLocation(location);
                tableEntity.setAvailability(state);
                Log.d(TAG, "createTable: success" + locationInit + " / " + location);

                if (oldLocation != location) {
                    BookingRepository.getInstance().updateBookingTable(oldLocation,this, tableViewModel, tableEntity);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tableViewModel.updateTableNewNumber(tableEntity, location, new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "updateTable: success");

                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG, "updateTable: failure", e);
                        }
                    });

                } else {
                    tableViewModel.updateTable(tableEntity, new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "updateTable: success");
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG, "updateTable: failure", e);
                        }
                    });
                }
            } else {
                TableEntity newTableEntity = new TableEntity();
                newTableEntity.setLocation(location);
                newTableEntity.setPersonNumber(personNumber);

                newTableEntity.setAvailability(state);

                tableViewModel.createTable(newTableEntity, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "createTable: success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "createTable: failure", e);
                    }
                });
            }

    }
}
