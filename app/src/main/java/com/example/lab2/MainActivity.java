package com.example.lab2;

import android.Manifest;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private int selectedItemId;
    protected EditText name;
    protected ListView lstContact;
    protected Button btnAdd;
    protected Button btnXoa;
    Adapter adapter;
    ArrayList<Contact> listContact;
    ArrayList lstName;
    private Contact lastSelectedContact;
    private Menu menu;
    private Contact c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindId();
        //Cap quyen truy cap doc thu vien
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Yêu cầu quyền truy cập vào bộ nhớ
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }

        listContact = new ArrayList<Contact>();

        listContact.add(new Contact(1,"A", "081","le308934@gmail.com", false,"content://media/external/images/media/23"));
        listContact.add(new Contact(2,"B", "0819", "le308934@gamil.com",false,"https://inkythuatso.com/uploads/thumbnails/800/2022/03/anh-dai-dien-facebook-dep-cho-nam-30-28-16-26-50.jpg"));
        listContact.add(new Contact(3,"C", "08194","le308934@gamil.com", false,"https://inkythuatso.com/uploads/thumbnails/800/2022/03/anh-dai-dien-facebook-dep-cho-nam-40-28-16-27-26.jpg"));
        //adapter = new Adapter(listContact,this);
        adapter = new Adapter(listContact, this, new Adapter.OnAvatarClickListener() {
            @Override
            public void onAvatarClick(Contact contact) {
                // Xử lý sự kiện khi người dùng nhấn vào hình đại diện
                lastSelectedContact = contact;
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        }, name);

        lstContact.setAdapter(adapter);
        registerForContextMenu(lstContact);
        lstContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemId= position;
                return false;
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XoaDS();
            }
            }

        );
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Add_Activity.class);
                ArrayList<Integer> id = new ArrayList<>();
                for(Contact x:listContact){
                    id.add(x.getId());

                }
                i.putIntegerArrayListExtra("listid",id);

                startActivityForResult(i,100);
            }
        });
    }
    public void FindId(){
        name = findViewById(R.id.txt_Name);
        lstContact = findViewById(R.id.lstContact);
        btnAdd = findViewById(R.id.btnThem);
        btnXoa= findViewById(R.id.btnXoa);

    }
    public void XoaDS(){
            ArrayList<Contact> phanTuCanLoaiBo = new ArrayList<>();

            // Lặp qua danh sách ngược từ cuối danh sách
            for (int i = listContact.size() - 1; i >= 0; i--) {
                if (listContact.get(i).isStatus()) {
                    // Thêm phần tử cần loại bỏ vào danh sách tạm thời
                    phanTuCanLoaiBo.add(listContact.get(i));
                }
            }

            // Loại bỏ các phần tử đã chọn khỏi danh sách gốc
        AlertDialog.Builder dg= new AlertDialog.Builder(MainActivity.this);
        dg.setTitle("Thông báo");
        dg.setMessage("Bạn có chắc chắn muốn xóa không?");
        dg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listContact.removeAll(phanTuCanLoaiBo);
                // Cập nhật giao diện
                lstContact.setAdapter(adapter);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == 150){
            Bundle b = data.getExtras();
            int id = b.getInt("id");
            String name = b.getString("name");
            String phone = b.getString("phone");
            String image_path= b.getString("img");
            String email = b.getString("email");
            Contact ct = new Contact(id,name,phone,email,false,image_path);
                listContact.add(ct);
                lstContact.setAdapter(adapter);
                Toast.makeText(this,"Thanh cong",Toast.LENGTH_SHORT).show();
        }
//        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
//            Uri selectedImage = data.getData();
//            String picturePath = getRealPathFromURI(selectedImage); // Lấy đường dẫn thực của ảnh
//            Log.d("IMAGE_PATH", "Đường dẫn của ảnh: " + picturePath);
//
//
//            // Cập nhật đường dẫn ảnh mới cho từng contact trong danh sách
//            if (picturePath != null && lastSelectedContact!=null) {
//
//                lastSelectedContact.setImagePath(picturePath);
//                adapter.notifyDataSetChanged();
//                Toast.makeText(this, "Thay doi avt thanh cong", Toast.LENGTH_SHORT).show();
//            } else {
//
//                Toast.makeText(this, "Không thể lấy đường dẫn của ảnh", Toast.LENGTH_SHORT).show();
//            }
//        }
        if(requestCode== 300&& resultCode==170){
            Bundle bl =data.getExtras();
            int id = bl.getInt("ID_edit");
            String name = bl.getString("Name_Edit");
            String phone = bl.getString("Phone_Edit");
            String img= bl.getString("Image_Edit");
            String email= bl.getString("Email_Edit");
            c.setEmail(email);
            c.setId(id);
            c.setName(name);
            c.setPhonenumber(phone);
            c.setImagePath(img);
            adapter.notifyDataSetChanged();
        }

    }

//    private String getRealPathFromURI(Uri contentUri) {
//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
//        if (cursor == null) return null;
//        cursor.moveToFirst();
//        int columnIndex = cursor.getColumnIndex(projection[0]);
//        String picturePath = cursor.getString(columnIndex);
//        cursor.close();
//        return picturePath;
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.sort_by_name)
        {
            Toast.makeText(MainActivity.this,"Ban da cho sorrt by name",Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.menu_phone)
        {
            Toast.makeText(MainActivity.this,"Ban da chon phone",Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.menu_broadcast)
        {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, "avt");
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_context, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        c= listContact.get(selectedItemId);
        if(item.getItemId()==R.id.menu_edit)
        {
            edit_item();
        }
        else if(item.getItemId()==R.id.menu_delete){
            listContact.remove(c);
            adapter.notifyDataSetChanged();
       }
        else if(item.getItemId()== R.id.menu_call){
            Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + c.getPhonenumber()));
            startActivity(i);
//            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + c.getPhonenumber()));
//            startActivity(i);
        }
        else if(item.getItemId()== R.id.menu_msm){
            Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + c.getPhonenumber()));
            startActivity(i);
        }
        else if(item.getItemId()== R.id.menu_email){
            Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + c.getPhonenumber()));
            startActivity(i);
        }
        return super.onContextItemSelected(item);
    }
    public void edit_item(){
        Intent i= new Intent(MainActivity.this,Update_Activity.class);
        Bundle b = new Bundle();
        b.putInt("id", c.getId());
        b.putString("Image",c.getImagePath());
        b.putString("name", c.getName());
        b.putString("Phone",c.getPhonenumber());
        b.putString("Email",c.getEmail());
       i.putExtras(b);
        startActivityForResult(i,300);
    }
}