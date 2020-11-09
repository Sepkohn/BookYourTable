package project.bookyourtable.ui.table;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import project.bookyourtable.R;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.util.OnAsyncEventListener;
import project.bookyourtable.viewmodel.table.TableViewModel;

public class EditTableActivity extends AppCompatActivity {

    private static final String TAG = "EditTableActivity";

    private TableEntity tableEntity;

    private boolean isEditMode;
    private Toast toast;
    private EditText etnumtable;
    private EditText etnumberperson;

    private TableViewModel tableViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getLayoutInflater().inflate(R.layout.activity_edit_account, frameLayout);
        setContentView(R.layout.activity_tableadd_dialog);
//
//        navigationView.setCheckedItem(position);
//
//        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
//        owner = settings.getString(BaseActivity.PREFS_USER, null);

//        etAccountName = findViewById(R.id.accountName);
//        etAccountName.requestFocus();
        etnumtable= findViewById(R.id.inputnumtable);
        //etnumberperson.requestFocus();

        etnumtable= findViewById(R.id.intputnumberperson);
       // etnumberperson.requestFocus();

        Button okBtn = findViewById(R.id.btn_okDialogue);
        okBtn.setOnClickListener(view -> {
            saveChanges(Integer.parseInt(etnumtable.getText().toString()),Integer.parseInt(etnumberperson.getText().toString()) );
            onBackPressed();
            toast.show();
        });

        Long tableId = getIntent().getLongExtra("accountId", 0L);
        if (tableId == 0L) {
            setTitle("Create Table");
            toast = Toast.makeText(this, "Table created", Toast.LENGTH_LONG);
            isEditMode = false;
        } else {
            setTitle("Edit Table");
            okBtn.setText("Add Changes");
            toast = Toast.makeText(this, "Table edited", Toast.LENGTH_LONG);
            isEditMode = true;
        }

        TableViewModel.Factory factory = new TableViewModel.Factory(
                getApplication(), tableId);
        tableViewModel = ViewModelProviders.of(this, factory).get(TableViewModel.class);
        if (isEditMode) {
            tableViewModel.getTable().observe(this, tableEntity -> {
                if (tableEntity != null) {
                    tableEntity = tableEntity;
                    etnumberperson.setText(tableEntity.getPersonNumber());
                    etnumtable.setText(tableEntity.getLocation());
                }
            });
        }
    }



    private void saveChanges(int personNumber, int location) {
        if (isEditMode) {
            if(!"".equals(location)) {
                tableEntity.setPersonNumber(personNumber);
                tableEntity.setLocation(location);
                
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
            newTableEntity.setPersonNumber(4);
            newTableEntity.setAvailability(true);
            newTableEntity.setLocation(000);

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
