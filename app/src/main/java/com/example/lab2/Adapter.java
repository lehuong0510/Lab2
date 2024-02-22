package com.example.lab2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {


    public OnAvatarClickListener getOnAvatarClickListener() {
        return onAvatarClickListener;
    }

    public void setOnAvatarClickListener(OnAvatarClickListener onAvatarClickListener) {
        this.onAvatarClickListener = onAvatarClickListener;
    }

    public interface OnAvatarClickListener {
        void onAvatarClick(Contact contact);
    }
    private OnAvatarClickListener onAvatarClickListener;
    private ArrayList<Contact> data;
    private Activity context;
    private  ArrayList<Contact> databackup;

    private LayoutInflater inflater;

    private TextView name;
    private  TextView phone;
    private CheckBox cb;
    private ImageView avata;
    private EditText txtName;
    public ArrayList<Contact> getData() {
        return data;
    }

    public void setData(ArrayList<Contact> data) {
        this.data = data;
    }
    public  Adapter(ArrayList<Contact> data, Activity context, OnAvatarClickListener onAvatarClickListener){
        this.data = data;
        this.context = context;
        this.onAvatarClickListener = onAvatarClickListener;
        //this.txtName = txtName;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null)
            v = inflater.inflate(R.layout.contact_activity,null);
        name = v.findViewById(R.id.editName);
        phone = v.findViewById(R.id.editSDT);
        cb = v.findViewById(R.id.check);
        avata = v.findViewById(R.id.image);
        name.setText(data.get(position).getName());
        phone.setText(data.get(position).getPhonenumber());
        cb.setChecked(data.get(position).isStatus());
        //avata.setImageURI(Uri.parse(data.get(position).getImagePath()));
        String imagePath = data.get(position).getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            Glide.with(context)
                    .load(imagePath)
                    .into(avata);
        } else {
            // Nếu đường dẫn ảnh rỗng, bạn có thể đặt ảnh mặc định cho ImageView ở đây
            // avata.setImageResource(R.drawable.default_image);
        }
        Contact ct = data.get(position);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ct.setStatus(isChecked);
            }
        });
        avata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAvatarClickListener != null) {
                    onAvatarClickListener.onAvatarClick(data.get(position));
                }
            }
        });

//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                txtName.setText(data.get(position).getName());
//            }
//        });


        return v;
    }

}
