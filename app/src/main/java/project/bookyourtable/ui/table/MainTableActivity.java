//package project.bookyourtable.ui.table;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.text.InputType;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.List;
//
//import project.bookyourtable.R;
//
//public class MainTableActivity extends AppCompatActivity {
//    private Button btnAdd;
//    private Button btnDel;
//    private Button btnDis;
//    private List<Integer> tables;
//    final Context context = this;
//    private EditText editText;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_table_management);
//
//        btnAdd = (Button) findViewById(R.id.buttonAdd);
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               showAddItemDialog(MainTableActivity.this, R.style.AddingTableDialog);
//
//                final Dialog dialog = new Dialog(context);
//                dialog.setContentView(R.layout.activity_tableadd_dialog);
//                dialog.setTitle("Add Table");
//
//                final EditText numero = (EditText) dialog.findViewById(R.id.inputnumtable);
//                final EditText nombre = (EditText) dialog.findViewById(R.id.intputnumberperson);
//
//                Button  okButton = (Button) dialog.findViewById(R.id.btn_okDialogue);
//                okButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String task1 = String.valueOf(numero.getText());
//                        String task2 = String.valueOf(nombre.getText());
//                        Toast toast = Toast.makeText(getApplicationContext(),"Numéro de table " +task1 + ",Nombre de personne " + task2, Toast.LENGTH_SHORT);
//                        toast.show();
//                    }
//                });
//
//                Button  cancelButton = (Button) dialog.findViewById(R.id.btn_cancelDialogue);
//                cancelButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
////
////
////
////
////            }
////        });
////
////
////        final ChipGroup chipGroup = findViewById(R.id.chipGroupTables);
////
////        tables = chipGroup.getCheckedChipIds();
////        for(Integer id: tables){
////            Chip chip = chipGroup.findViewById(id);
////        }
////
////        btnAdd = (Button) findViewById(R.id.buttonAdd);
////        btnAdd.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                showAddItemDialog(MainTableActivity.this, R.style.AddingTableDialog);
////            }
////        });
////
////        btnDel = (Button) findViewById(R.id.buttonDelete);
////        btnDel.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                showAddItemDialog(MainTableActivity.this, R.style.AddingTableDialog);
////            }
////        });
//        btnDis = (Button) findViewById(R.id.buttonDesactivate);
//        btnDis.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View v) {
//               int idTable = chipGroup.getCheckedChipId();
//
//                Chip chip = findViewById(idTable);
//
//
//                if(chip.getChipBackgroundColor()  == (ColorStateList.valueOf(R.color.colorPrimary))){
//
//
//                }else{
//                    chip.setChipBackgroundColor(ColorStateList.valueOf(R.color.colorTest));
//                    System.out.println("TOUDOM");
//                }
//
//
//            }
//        });
//
//    }
//
//
//    private void showAddItemDialog(Context c, int addingTableDialog) {
//
//        final LinearLayout linearLayout = new LinearLayout(c);
//
//        final EditText numberTable = new EditText(c);
//        final EditText numberPerson = new EditText(c);
//
//        numberTable.setInputType(InputType.TYPE_CLASS_NUMBER);
//        numberPerson.setInputType(InputType.TYPE_CLASS_NUMBER);
//
//        final TextView text1 = new TextView(c);
//        final TextView text2 = new TextView(c);
//
//        text1.setText("Numero de table");
//        linearLayout.addView(text1);
//        linearLayout.addView(numberTable);
//
//        text2.setText("Nombre de personnes");
//        linearLayout.addView(text2);
//        linearLayout.addView(numberPerson);
//
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//
//        AlertDialog dialog = new AlertDialog.Builder(c)
//                .setView(linearLayout)
//                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String task1 = String.valueOf(numberTable.getText());
//                        String task2 = String.valueOf(numberPerson.getText());
//
//                        Toast toast = Toast.makeText(getApplicationContext(),"Numéro de table " +task1 + ",Nombre de personne " + task2, Toast.LENGTH_SHORT);
//                        toast.show();
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .create();
//        dialog.show();
//
//
//    }
//}