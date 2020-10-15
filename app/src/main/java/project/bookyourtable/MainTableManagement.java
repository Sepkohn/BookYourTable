package project.bookyourtable;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainTableManagement extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_table_management);

        button = (Button) findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog(MainTableManagement.this);
            }
        });
    }


    private void showAddItemDialog(Context c) {
        final LinearLayout linearLayout = new LinearLayout(c);

        final EditText numberTable = new EditText(c);
        final EditText numberPerson = new EditText(c);

        numberTable.setInputType(InputType.TYPE_CLASS_NUMBER);
        numberPerson.setInputType(InputType.TYPE_CLASS_NUMBER);

        final TextView text1 = new TextView(c);
        final TextView text2 = new TextView(c);

        text1.setText("Numero de table");
        linearLayout.addView(text1);
        linearLayout.addView(numberTable);

        text2.setText("Nombre de personnes");
        linearLayout.addView(text2);
        linearLayout.addView(numberPerson);

        linearLayout.setOrientation(LinearLayout.VERTICAL);

        AlertDialog dialog = new AlertDialog.Builder(c)
                .setView(linearLayout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task1 = String.valueOf(numberTable.getText());
                        String task2 = String.valueOf(numberPerson.getText());
                        Toast toast = Toast.makeText(getApplicationContext(),"Numéro de table " +task1 + ",Nombre de personne " + task2, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }
}