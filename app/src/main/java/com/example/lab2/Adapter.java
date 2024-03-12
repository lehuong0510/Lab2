package com.example.lab2;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends BaseAdapter implements Filterable {


    public OnAvatarClickListener getOnAvatarClickListener() {
        return onAvatarClickListener;
    }

    public void setOnAvatarClickListener(OnAvatarClickListener onAvatarClickListener) {
        this.onAvatarClickListener = onAvatarClickListener;
    }

    @Override
    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults fr = new FilterResults();
                // luu tamm dât và databackup
                if(databackup==null){
                    databackup = new ArrayList<>(data);
                }
                //Neu chuoi de filter la rong thi khoi phuc du lieu
                if(constraint == null || constraint.length()==0){
                    fr.count = databackup.size();
                    fr.values = databackup;
                }
                // Neu khong rong thi thuc hien fiter

                else {
                    ArrayList<Contact> newdata = new ArrayList<>();
                    for(Contact c: databackup){
                        if(c.getName().toLowerCase().contains(constraint.toString().toLowerCase()))
                            newdata.add(c);
                        fr.count= newdata.size();
                        fr.values = newdata;
                    }

                }
                return  fr;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = new ArrayList<Contact>();
                ArrayList<Contact> tmp = (ArrayList<Contact>)results.values;
                for(Contact c:tmp){
                    data.add(c);
                    notifyDataSetChanged();
                }
            }
        };
        return  f;


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
    private  TextView email;
    public ArrayList<Contact> getData() {
        return data;
    }

    public void setData(ArrayList<Contact> data) {
        this.data = data;
    }
    public  Adapter(ArrayList<Contact> data, Activity context){
        this.data = data;
        this.context = context;//////
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
        email = v.findViewById(R.id.editEmail);
        name.setText(data.get(position).getName());
        phone.setText(data.get(position).getPhonenumber());
        cb.setChecked(data.get(position).isStatus());
        email.setText(data.get(position).getEmail());
        //avata.setImageURI(Uri.parse(data.get(position).getImagePath()));
        Log.d("a1", "getView: " + Uri.parse(data.get(position).getImagePath()));
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
