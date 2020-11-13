package project.bookyourtable.ui.table;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import project.bookyourtable.R;
import project.bookyourtable.adapter.RecyclerAdapter;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.util.OnAsyncEventListener;
import project.bookyourtable.util.RecyclerViewItemClickListener;
import project.bookyourtable.viewmodel.table.TableListViewModel;

public class TableActivity  extends AppCompatActivity {

    private static final String TAG = "TableActivity";
    private List<TableEntity> tables;
    private RecyclerAdapter<TableEntity> adapter;
    private TableListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_table_management);

        // BUTTON ADD
        Button addingTable = findViewById(R.id.buttonAdd);
        addingTable.setOnClickListener(view -> {
                    Intent intent = new Intent(TableActivity.this, EditTableActivity.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                    Intent.FLAG_ACTIVITY_NO_HISTORY
                    );
                    startActivity(intent);
                }
        );

        Button btnDel = (Button) findViewById(R.id.buttonDelete);
        Button btnDis = (Button) findViewById(R.id.buttonDesactivate);


        RecyclerView recyclerView = findViewById(R.id.tableRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//                LinearLayoutManager.HORIZONTAL);
       // recyclerView.addItemDecoration(dividerItemDecoration);

        tables = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {

            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + tables.get(position).getId());

                Intent intent = new Intent(TableActivity.this, EditTableActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("tableId", tables.get(position).getId());
                startActivity(intent);
            }


            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + tables.get(position).getId());

                createDeleteDialog(position);
            }
        });



        TableListViewModel.Factory factory = new TableListViewModel.Factory(
                getApplication());

        viewModel = ViewModelProviders.of(this, factory).get(TableListViewModel.class);
        viewModel.getOwnTables().observe(this, tableEntities -> {
            if (tableEntities != null) {
                tables = tableEntities;
                adapter.setData(tables);
            }
        });

        recyclerView.setAdapter(adapter); // refresh l'adaptater, toutes les vues dans le recycler sont rafraichies
        adapter.notifyDataSetChanged(); //ajouté par Quentin
    }

    private void createDeleteDialog(final int position) {


        final TableEntity table = tables.get(position);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.row_delete_item, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Delete Table");
        alertDialog.setCancelable(false);

        final TextView deleteMessage = view.findViewById(R.id.tv_delete_item);

        deleteMessage.setText(String.format("Do you want delete Table ?", table.getLocation()));

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Execute", (dialog, which) -> {
            Toast toast = Toast.makeText(this, "Table deleted", Toast.LENGTH_SHORT);

            viewModel.deleteTable(table, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "deleteTable: success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "deleteTable: failure", e);
                    }
                });
                toast.show();
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> alertDialog.dismiss());
        alertDialog.setView(view);
        alertDialog.show();
    }
}
