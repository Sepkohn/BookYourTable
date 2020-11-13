package project.bookyourtable.ui.table;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import java.text.NumberFormat;

import project.bookyourtable.R;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.util.OnAsyncEventListener;
import project.bookyourtable.viewmodel.table.TableViewModel;

public class EditTableActivity extends AppCompatActivity {

    private static final String TAG = "EditTableActivity";

    private TableEntity tableEntity;
    private NumberFormat defaultFormat;
    private boolean isEditMode;
    private Toast toast;
    private EditText etnumtable;
    private EditText etnumberperson;

    private TableViewModel tableViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getLayoutInflater().inflate(R.layout.activity_edit_account, frameLayout);
        setContentView(R.layout.activity_table_edit);
//
//        navigationView.setCheckedItem(position);
//
//        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
//        owner = settings.getString(BaseActivity.PREFS_USER, null);

        etnumtable= findViewById(R.id.inputnumtable);
        etnumtable.requestFocus();
        etnumberperson= findViewById(R.id.intputnumberperson);


        Button saveBtn = findViewById(R.id.btn_okDialogue);
        saveBtn.setOnClickListener(view -> {
            saveChanges(etnumtable.getText().toString(),etnumberperson.getText().toString());
            Log.d(TAG, etnumtable.getText().toString());
            onBackPressed();
            toast.show();
        });

        Button cancelBtn = findViewById(R.id.btn_cancelDialogue);
        cancelBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, TableActivity.class);
            startActivity(intent);
        });


        Long tableId = getIntent().getLongExtra("tableId", 0L);
        if (tableId == 0L) {
            setTitle("Create Table");
            toast = Toast.makeText(this, "Table created", Toast.LENGTH_LONG);
            isEditMode = false;
        } else {
            setTitle("Edit Table");
            saveBtn.setText("Add Changes");
            cancelBtn.setText("Cancel");
            toast = Toast.makeText(this, "Table edited", Toast.LENGTH_LONG);
            isEditMode = true;
        }

        TableViewModel.Factory factory = new TableViewModel.Factory(
                getApplication(), tableId);
        tableViewModel = ViewModelProviders.of(this, factory).get(TableViewModel.class);

        if (isEditMode) {
            tableViewModel.getTable().observe(this, tableE -> {
                if (tableE != null) {
                    tableEntity = tableE;
                    etnumtable.setText("" + tableEntity.getLocation());
                    etnumberperson.setText("" + tableEntity.getPersonNumber());
                }
            });
        }
    }



    private void saveChanges(String location, String personNumber) {
        if (isEditMode) {

            if(!"".equals(location) && !"".equals(personNumber)) {
                tableEntity.setPersonNumber(Integer.parseInt(personNumber));
                tableEntity.setLocation(Integer.parseInt(location));
                
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
            newTableEntity.setPersonNumber(Integer.parseInt(personNumber));
            newTableEntity.setAvailability(true);
            newTableEntity.setLocation(Integer.parseInt(location));

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
