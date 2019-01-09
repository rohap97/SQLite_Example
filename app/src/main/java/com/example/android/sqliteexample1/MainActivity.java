package com.example.android.sqliteexample1;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    EditText editTextId, editName, editSurname, editMarks;
    Button btnaddData, btngetData, btnviewAll, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editTextId = (EditText) findViewById(R.id.editText_id);
        editName = (EditText) findViewById(R.id.editText_name);
        editSurname = (EditText) findViewById(R.id.editText_surname);
        editMarks = (EditText) findViewById(R.id.editText_marks);
        btnaddData = (Button) findViewById(R.id.button_add);
        btngetData = (Button) findViewById(R.id.button_view);
        btnDelete = (Button) findViewById(R.id.button_delete);
        btnUpdate = (Button) findViewById(R.id.button_update);
        btnviewAll = (Button) findViewById(R.id.button_viewALl);
        AddData();
        getData();
        updateData();
        deleteData();
        viewAll();
    }

    public void AddData() {
        btnaddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean isInserted = myDb.insertData(editName.getText().toString(), editSurname.getText().toString(), editMarks.getText().toString());
                if(isInserted == true)
                    Toast.makeText(MainActivity.this, "Data Inserted ", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data could not be Inserted", Toast.LENGTH_LONG).show();
                clear();
            }
        });
    }

    public void getData(){
        btngetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editTextId.getText().toString();

                if(id.equals(String.valueOf(""))){
                    editTextId.setError("Enter id to get data");
                    return;
                }

                Cursor res = myDb.getData(id);
                String data = null;
                if (res.moveToFirst()){
                    data = "Id:"+ res.getString(0) + "\n"+
                            "Name :"+ res.getString(1)+ "\n\n"+
                            "Surname :"+ res.getString(2)+"\n\n"+
                            "Marks :"+ res.getString(3)+"\n\n";
                }
                showMessage("Data", data);
            }
        });
    }

    public void viewAll(){
        btnviewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0){
                    showMessage("Error", "Nothing Found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id:" +res.getString(0)+"\n");
                    buffer.append("Name:" +res.getString(1)+"\n");
                    buffer.append("Surname:" +res.getString(2)+"\n");
                    buffer.append("Marks:" +res.getString(3)+"\n");

                }
                showMessage("Data", buffer.toString());
            }
        });
    }

    public void updateData(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = myDb.updateData(editTextId.getText().toString(),editName.getText().toString(), editSurname.getText().toString(), editMarks.getText().toString());
                if(isUpdate == true)
                    Toast.makeText(MainActivity.this,"Data Update", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data could not be Updated", Toast.LENGTH_LONG ).show();
            }
        });
    }

    public void deleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                if(deletedRows > 0)
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data could not be deleted", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clear(){
        editTextId.setText("");
        editMarks.setText("");
        editSurname.setText("");
        editName.setText("");
    }





}
