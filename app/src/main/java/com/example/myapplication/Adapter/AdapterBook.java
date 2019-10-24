package com.example.myapplication.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.ActivityUpdateBook;
import com.example.myapplication.Database.Database;
import com.example.myapplication.Modal.ModalBook;
import com.example.myapplication.Modal.ModalCategory;
import com.example.myapplication.Modal.ModalUser;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AdapterBook extends BaseAdapter {
    final String DATABASE_NAME = "IronBookStoreSQLite.sqlite";
    Activity context;
    ArrayList<ModalBook> list;

    public AdapterBook(Activity context, ArrayList<ModalBook> list) {
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
        View row = inflater.inflate(R.layout.item_boook, null);

        TextView txtMa = row.findViewById(R.id.txt_ma_itembook);
        TextView txtTen = row.findViewById(R.id.txt_ten_itembook);
        TextView txtGiaBan = row.findViewById(R.id.txt_giaban_itembook);
        TextView txtGiaNhap = row.findViewById(R.id.txt_gianhap_itembook);
        TextView txtSoLuong = row.findViewById(R.id.txt_soluong_itembook);
        TextView txtStt = row.findViewById(R.id.txt_stt_itembook);

        ImageView imgBook = row.findViewById(R.id.img_itembook);

        ImageButton btnEdit = row.findViewById(R.id.btn_edit_itembook);
        ImageButton btnDelete = row.findViewById(R.id.btn_delete_itembook);

        final ModalBook modalBook = list.get(i);

        txtStt.setText(1+i+"");
        txtMa.setText(modalBook.getMaSach());
        txtTen.setText(modalBook.getTenSach());
        txtGiaBan.setText((int) modalBook.getGiaXuatSach()+"");
        txtGiaNhap.setText((int) modalBook.getGiaNhapSach()+"");
        txtSoLuong.setText((int) modalBook.getSoLuongSach()+"");

        Bitmap bmImg = BitmapFactory.decodeByteArray(modalBook.getHinhSach(), 0, modalBook.getHinhSach().length);
        imgBook.setImageBitmap(bmImg);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ActivityUpdateBook.class);
                i.putExtra("sttbook",modalBook.getSttSach());
                context.finish();
                context.startActivity(i);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.delete);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn muốn xóa '"+ modalBook.getTenSach() +"' ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteBook(modalBook.getSttSach());
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

            }
        });

        return row;
    }
    private void deleteBook(String stt) {
        SQLiteDatabase database = Database.initDatabase(context,DATABASE_NAME);
        database.delete("tbSach","col_stt_tbSach=?",new String[]{stt});
        Cursor cs = database.rawQuery("select * from tbSach",null);
        list.clear();
        while (cs.moveToNext()){
            String stta = cs.getString(0);
            byte[] hinh = cs.getBlob(1);
            String ma = cs.getString(2);
            String ten = cs.getString(3);
            String matheloai = cs.getString(4);
            String tacgia = cs.getString(5);
            String nhaxb = cs.getString(6);
            float gianhap = cs.getFloat(7);
            float giaban = cs.getFloat(8);
            float soluong = cs.getFloat(9);
            String mota = cs.getString(10);

            list.add(new ModalBook(stta, hinh, ma, ten, matheloai, tacgia, nhaxb, gianhap, giaban, soluong, mota));
        }
        notifyDataSetChanged();
    }
}
