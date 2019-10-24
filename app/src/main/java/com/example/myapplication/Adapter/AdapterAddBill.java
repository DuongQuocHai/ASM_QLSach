package com.example.myapplication.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.ActivityAddBill;
import com.example.myapplication.Modal.ModalAddBill;
import com.example.myapplication.Modal.ModalBill;
import com.example.myapplication.Modal.ModalBook;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AdapterAddBill extends BaseAdapter {
    final String DATABASE_NAME = "IronBookStoreSQLite.sqlite";
    Activity context;
    ArrayList<ModalAddBill> list;

    public AdapterAddBill(Activity context, ArrayList<ModalAddBill> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.item_bill_detail, null);

        TextView txtMaSach = row.findViewById(R.id.txt_masach_itembilldetail);
        TextView txtDonGia = row.findViewById(R.id.txt_dongia_itembilldetail);
        TextView txtThanhTien = row.findViewById(R.id.txt_thanhtien_itembilldetail);
        TextView txtSoLuong = row.findViewById(R.id.txt_soluong_itembilldetail);
        TextView txtStt = row.findViewById(R.id.txt_stt_itembilldetail);
        ImageView imgAv = row.findViewById(R.id.img_itembilldetail);

        ImageButton btnDelete = row.findViewById(R.id.btn_delete_itembilldetail);
        ActivityAddBill activityAddBill = new ActivityAddBill();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        final ModalAddBill modal = list.get(i);

        txtStt.setText(1+i+"");
        txtMaSach.setText(modal.getMasach());
        txtDonGia.setText(modal.getDongia()+"");
        txtThanhTien.setText(modal.getThanhtien()+"");
        txtSoLuong.setText(modal.getSoluong()+"");

//        Bitmap bmImg = BitmapFactory.decodeByteArray(modalUser.getHinhUser(),0,modalUser.getHinhUser().length);
//        imgUserList.setImageBitmap(bmImg);
        imgAv.setImageResource(R.drawable.avatar);

        return row;
    }
}
