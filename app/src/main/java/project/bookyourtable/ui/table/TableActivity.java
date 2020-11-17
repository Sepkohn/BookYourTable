package project.bookyourtable.ui.table;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
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

/**
 * A management table screen that offers a view of tables created, delete one item with longclick and modify it with a simple clic and give the possibility to adding a new table with button*/
public class TableActivity extends AppCompatActivity {

    private static final String TAG = "TableActivity";
    private List<TableEntity> tables;
    private RecyclerAdapter<TableEntity> adapter;
    private TableListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_table);

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
        // Implement recyclerView and set a linear layout
        RecyclerView recyclerView = findViewById(R.id.tableRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adding decoration to recycler
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        tables = new ArrayList<>();


        TableListViewModel.Factory factory = new TableListViewModel.Factory(
                getApplication());

        viewModel = new ViewModelProvider(this, factory).get(TableListViewModel.class);

        viewModel.getOwnTables().observe(this, tableEntities -> {

            if (tableEntities != null) {
                tables = tableEntities;
                adapter.setData(tables);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        adapterInstance();
        // refresh adaptater

    }
/**
 * Implement adapter and parameter OnClick, give a Extra information about id of table selected through the intent*/
    private void adapterInstance() {
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {

            public void onItemClick(View v, int position) {
                Intent intent = new Intent(TableActivity.this, EditTableActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("tableId", tables.get(position).getId());
                startActivity(intent);
            }

            public void onItemLongClick(View v, int position) {
                createDeleteDialog(position); //method to create dialog for deleting item
            }
        });
    }
        /**
         * That method create a AlertDialog to ask if user are certain that the item can be deleted */
    private void createDeleteDialog(final int position) {
        final TableEntity table = tables.get(position);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.row_delete_item, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setCancelable(false);

        final TextView deleteMessage = view.findViewById(R.id.tv_delete_item);
        deleteMessage.setText(getString(R.string.deleteMessage, ""+table.getLocation()));
        deleteMessage.setTextSize(20);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.execute), (dialog, which) -> {
            Toast toast = Toast.makeText(this, getString(R.string.tabledeleted), Toast.LENGTH_SHORT);

            viewModel.deleteTable(table, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "Table deleted");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "deleteTable: failure", e);
                }
            });
            toast.show();
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), (dialog, which) -> alertDialog.dismiss());
        alertDialog.setView(view);
        alertDialog.show();
    }
}
