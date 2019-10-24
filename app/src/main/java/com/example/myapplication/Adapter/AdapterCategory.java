package com.example.myapplication.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.Database;
import com.example.myapplication.Modal.ModalBook;
import com.example.myapplication.Modal.ModalCategory;
import com.example.myapplication.Modal.ModalUser;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AdapterCategory extends BaseAdapter {
    final String DATABASE_NAME = "IronBookStoreSQLite.sqlite";
    Activity context;
    ArrayList<ModalCategory> list;
    SQLiteDatabase database;

    public AdapterCategory(Activity context, ArrayList<ModalCategory> list) {
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
        final LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row =inflater.inflate(R.layout.item_category,null);

        TextView txtMaTl = row.findViewById(R.id.txt_ma_category);
        TextView txtTenTl = row.findViewById(R.id.txt_ten_category);
        TextView txtSttTl = row.findViewById(R.id.txt_stt_category);
        ImageView btnDeleteTl = row.findViewById(R.id.btn_delete_category);

        final ModalCategory modalCategory = list.get(i);

        txtSttTl.setText(1+i+"");
        txtMaTl.setText(modalCategory.getMaTheLoai());
        txtTenTl.setText(modalCategory.getTenTheLoai());


        btnDeleteTl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (catchSameMaTl(modalCategory.getMaTheLoai())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setIcon(R.drawable.delete);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn muốn xóa '"+ modalCategory.getTenTheLoai() +"' ?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            xoaTl(modalCategory);
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
            }
        });


        return row;
    }
    public void xoaTl(ModalCategory modalCategory){

        SQLiteDatabase database = Database.initDatabase(context,DATABASE_NAME);

        int kq = database.delete("tbTheLoai","col_stt_tbTheLoai=?",new String[]{modalCategory.getSttTl()});
        if (kq==0){
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
        }
        Cursor cursor = database.rawQuery("select * from tbTheLoai",null);
        list.clear();
        while (cursor.moveToNext()){
            String ma = cursor.getString(1);
            String ten = cursor.getString(2);
            String vitri = cursor.getString(3);
            String stt = cursor.getString(0);

            list.add(new ModalCategory(ma, ten,vitri,stt));
        }
        notifyDataSetChanged();

    }
    private boolean catchSameMaTl(String maTl) {
        database = (SQLiteDatabase) Database.initDatabase(context, DATABASE_NAME);
        Cursor cursor = database.rawQuery("select * from tbSach", null);
        if (cursor.moveToFirst()) {
            do {
                String maTlSach = cursor.getString(4);
                if (maTlSach.equals(maTl)) {
                    Toast.makeText(context, "Xóa sách trước khi xóa thể loại.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } while (cursor.moveToNext());
        }
        return true;
    }
}
