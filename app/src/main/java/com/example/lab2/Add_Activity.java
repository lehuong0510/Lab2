package com.example.lab2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Add_Activity extends AppCompatActivity {
    EditText id;
    EditText name ;
    EditText phone;
    Button btnOK;
    Button btnCancel;
    ArrayList<Integer> iddaco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        FindID();
        Intent intent = getIntent();
         iddaco = new ArrayList<Integer>();
        iddaco = intent.getIntegerArrayListExtra("listid");
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dg= new AlertDialog.Builder(Add_Activity.this);
                dg.setTitle("Thông báo");
                dg.setMessage("Bạn có chắc chắn hủy?");
                dg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                dg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog al = dg.create();
                al.show();



            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean k = true;
                Intent i = new Intent();
                Bundle b = new Bundle();
                if(validate()){
                    int iD = Integer.parseInt(id.getText().toString());
                    for(Integer y: iddaco){
                        if(iD== y){
                            k = false;
                            break;
                        }
                    }
                    if(k){
                        b.putInt("id", iD);
                        b.putString("name", name.getText().toString());
                        b.putString("phone", phone.getText().toString());
                        i.putExtras(b);
                        setResult(150,i);
                        finish();
                    }
                    else
                        Toast.makeText(Add_Activity.this, "ID đã tồn tại", Toast.LENGTH_LONG).show();

                }
                else {

                        Log.d("Add_Activity", "Thông tin không hợp lệ: Hãy nhập đủ thông tin");
                        Toast.makeText(getApplicationContext(), "Hãy nhập đủ thông tin", Toast.LENGTH_LONG).show();
                        Log.d("Add_Activity", "Thông tin không hợp lệ: Hãy nhập đủ thông tinA");
                }

            }
        });
    }
    public boolean validate(){
        boolean v= true;
        if(id.getText().toString().length()<1 || name.getText().toString().trim().length() <1||phone.getText().toString().trim().length() <1){
            Toast.makeText(Add_Activity.this, "Hãy nhập đủ thông tin", Toast.LENGTH_LONG).show();
            v= false;
        }


        return  v;
    }
    public void FindID(){
        id = findViewById(R.id.edit_ID);
        name = findViewById(R.id.edit_Name);
        phone = findViewById(R.id.edit_SDT);
        btnOK = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);
    }


}