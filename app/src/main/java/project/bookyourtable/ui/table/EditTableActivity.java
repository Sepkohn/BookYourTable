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

import java.util.List;

import project.bookyourtable.R;
import project.bookyourtable.database.entity.TableEntity;
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
            if (verifyInformations()) {
                saveChanges(Integer.parseInt(etnumtable.getText().toString()), Integer.parseInt(etnumberperson.getText().toString()), statusSwitch);
                onBackPressed();
                toast.show();
            }
        });
        Button cancelBtn = findViewById(R.id.btn_cancelDialogue);
        cancelBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, TableActivity.class);
            startActivity(intent);
        });

        //Get if table ID exist for editing or not for adding mode
        Long tableId = getIntent().getLongExtra("tableId", 0L);

        if (tableId == 0L) {
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
    private boolean verifyInformations() {
        String stringetnumtable = etnumtable.getText().toString();
        String stringetnumberperson = etnumberperson.getText().toString();

        if (stringetnumtable.isEmpty()) {
            etnumtable.setError(getString(R.string.numberTableErr));
            etnumtable.requestFocus();
            return false;
        } else if (stringetnumberperson.isEmpty()) {
            etnumberperson.setError(getString(R.string.numberPersonErr));
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method to set all information in tableEntity that offer an distinction between adding mode or edit mode*/
    private void saveChanges(int location, int personNumber, boolean state) {
        if (isEditMode) {
            tableEntity.setPersonNumber(personNumber);
            tableEntity.setLocation(location);
            tableEntity.setAvailability(state);

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
        } else {
            TableEntity newTableEntity = new TableEntity();
            newTableEntity.setPersonNumber(personNumber);
            newTableEntity.setLocation(location);
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
