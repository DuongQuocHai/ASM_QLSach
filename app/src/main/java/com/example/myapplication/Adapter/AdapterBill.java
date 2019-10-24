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

import com.example.myapplication.Modal.ModalBill;
import com.example.myapplication.Modal.ModalBook;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AdapterBill extends BaseAdapter {
    final String DATABASE_NAME = "IronBookStoreSQLite.sqlite";
    Activity context;
    ArrayList<ModalBill> list;

    public AdapterBill(Activity context, ArrayList<ModalBill> list) {
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
        View row = inflater.inflate(R.layout.item_bill, null);

        TextView txtMa = row.findViewById(R.id.txt_ma_bill);
        TextView txtNgay = row.findViewById(R.id.txt_ngay_bill);
        TextView txtThanhTien = row.findViewById(R.id.txt_thanhtien_bill);
        TextView txtStt = row.findViewById(R.id.txt_stt_bill);


        final ModalBill modalBill = list.get(i);

        txtStt.setText(1+i+"");
        txtMa.setText(modalBill.getMaBill());
        txtNgay.setText(modalBill.getNgayBill());
        txtThanhTien.setText((int) modalBill.getThanhTienBill()+" VND");

        return row;
    }
}
