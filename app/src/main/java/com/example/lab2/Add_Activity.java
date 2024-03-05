package com.example.lab2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Add_Activity extends AppCompatActivity {
    private  int PICK_IMG = 157;
    EditText id;
    EditText name ;
    EditText phone;
    EditText email ;
    Button btnOK;
    Button btnCancel;
    ImageView img;
    ArrayList<Integer> iddaco;
    String image_path;
    CheckBox status;
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
                        b.putString("img",image_path);
                        b.putString("email",email.getText().toString());
                        b.putBoolean("status", status.isChecked());
                        Log.d("Status", "onClick: " + status.isChecked());
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
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_IMG);

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
        img = findViewById(R.id.img_add);
        email = findViewById(R.id.edit_Name);
        status = findViewById(R.id.add_status);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMG && resultCode== RESULT_OK&& data!=null){
            Uri url_img = data.getData();
            Log.d("urlimg", "onActivityResult: " + url_img);
            String picturePath = getRealPathFromURI(url_img);
            image_path=picturePath;
            if(picturePath!=null){
                Glide.with(Add_Activity.this)
                        .load(picturePath)
                        .into(img);
            }
        }
    }
    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) return null;
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }


}