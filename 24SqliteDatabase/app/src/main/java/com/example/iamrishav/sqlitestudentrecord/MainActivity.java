package com.example.iamrishav.sqlitestudentrecord;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDb;
    EditText id, name, email, cc;
    Button buttonAdd, buttongetdata, buttonUpdate, buttonDelete, buttonViewAll, buttonDeleteAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DataBaseHelper(this);

        id = findViewById(R.id.editText_id);
        name = findViewById(R.id.editText_name);
        email = findViewById(R.id.editText_email);
        cc = findViewById(R.id.editText_cc);

        buttonAdd = findViewById(R.id.button_add);
        buttongetdata = findViewById(R.id.button_View);
        buttonUpdate = findViewById(R.id.button_update);
        buttonDelete = findViewById(R.id.button_delete);
        buttonViewAll = findViewById(R.id.button_ViewAll);
        buttonDeleteAll = findViewById(R.id.button_DeleteAll);

        addData();
        viewData();
        updateData();
        deleteData();
        viewAllData();
        deleteAllData();
    }

    public void addData() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(name.getText().toString(), email.getText().toString(), cc.getText().toString());
                if (isInserted == true) {
                    Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                    id.setText("");
                    name.setText("");
                    email.setText("");
                    cc.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void viewData() {
        buttongetdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ids = id.getText().toString();

                if (ids.equals(String.valueOf(""))) {
                    id.setError("Enter ID");
                    return;
                }
                Cursor cursor = myDb.getData(ids);
                String data = null;
                if (cursor.moveToNext()) {
                    data = "ID: " + cursor.getString(0) + "\n" +
                            "Name: " + cursor.getString(1) + "\n" +
                            "Email: " + cursor.getString(2) + "\n" +
                            "CourseCount: " + cursor.getString(3) + "\n";
                }
                if(data==null){
                    Toast.makeText(MainActivity.this,"No data",Toast.LENGTH_SHORT).show();
                    return;
                }
                showMessage("Data: ", data);
            }
        });
    }

    public void updateData(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isupdated = myDb.updatData(id.getText().toString(), name.getText().toString(), email.getText().toString(), cc.getText().toString());
                if(isupdated==true){
                    Toast.makeText(MainActivity.this, "Updated succesfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteData(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ids = id.getText().toString();
                if(ids.equals(String.valueOf(""))){
                    id.setError("Enter ID");
                }
                Integer deleteRow = myDb.deleteData(ids);
                if(deleteRow>0){
                Toast.makeText(MainActivity.this,"Delete success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"OOPS!!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void viewAllData(){

        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDb.getAllData();
                if(cursor.getCount()==0){
                    showMessage("Error", "No Data");
                    return;
                }
                StringBuffer buffer = new StringBuffer();

                while (cursor.moveToNext()){
                    buffer.append("ID: "+cursor.getString(0)+"\n");
                    buffer.append("Name: "+cursor.getString(1)+"\n");
                    buffer.append("Email: "+cursor.getString(2)+"\n");
                    buffer.append("CourseCount: "+cursor.getString(3)+"\n\n");
                }
                showMessage("All Data", buffer.toString());
            }
        });
    }

    public void deleteAllData(){
        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.deleteAllData();
                showMessage("success","Deleted All Data Successfully");
            }
        });
    }

    public void showMessage(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
