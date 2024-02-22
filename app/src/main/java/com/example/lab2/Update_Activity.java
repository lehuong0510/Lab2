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
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Update_Activity extends AppCompatActivity {
    private  int PICK_IMAGE= 160;
    EditText id;
    EditText name ;
    EditText phone;
    Button btnEdit;
    Button btnCancel;
    ImageView img;
    String image_path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        FindID();
        values_main();


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            btnCancel_OnClik();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);

            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Phone = phone.getText().toString();
                int iid =  Integer.parseInt(id.getText().toString());
                Intent i = new Intent();
                Bundle bl = new Bundle();
                bl.putInt("ID_Edit",iid);
                bl.putString("Name_Edit",Name);
                bl.putString("Phone_Edit",Phone);
                bl.putString("Image_Edit",image_path);
                i.putExtras(bl);
                setResult(170,i);
                finish();


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE && resultCode== RESULT_OK&& data!=null){
           Uri url_img = data.getData();
            String picturePath = getRealPathFromURI(url_img);
            image_path=picturePath;
            if(picturePath!=null){
                Glide.with(Update_Activity.this)
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

    public void FindID(){
        id = findViewById(R.id.update_ID);
        name = findViewById(R.id.update_Name);
        phone = findViewById(R.id.update_SDT);
        btnEdit = findViewById(R.id.btnEdit);
        btnCancel = findViewById(R.id.btnCancel_Upadte);
        img = findViewById(R.id.img_update);
    }
    public void values_main() {
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            int ida = b.getInt("id");
            String namea = b.getString("name");
            String phonea = b.getString("Phone");
            String img_path = b.getString("Image");
            image_path = img_path;
            id.setText(String.valueOf(ida));
            name.setText(namea);
            phone.setText(phonea);
            Log.d("l", "onCreate: " + img_path);
            Glide.with(Update_Activity.this)
                    .load(img_path)
                    .into(img);
        }
    }
    public  void btnCancel_OnClik(){
        AlertDialog.Builder dg= new AlertDialog.Builder(Update_Activity.this);
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
}