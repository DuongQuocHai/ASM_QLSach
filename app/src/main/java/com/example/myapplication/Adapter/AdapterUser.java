package com.example.myapplication.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.example.myapplication.ActivityUpdateUser;
import com.example.myapplication.Database.Database;
import com.example.myapplication.Modal.ModalUser;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AdapterUser extends BaseAdapter {
    final String DATABASE_NAME = "IronBookStoreSQLite.sqlite";
    Activity context;
    ArrayList<ModalUser> list;

    public AdapterUser(Activity context, ArrayList<ModalUser> list) {
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
        View row =inflater.inflate(R.layout.item_user,null);

        ImageView imgUserList = row.findViewById(R.id.img_itemuser);
        final TextView txtMaUserList = row.findViewById(R.id.txt_itemuser_manv);
        final TextView txtTenUserList = row.findViewById(R.id.txt_itemuser_tennv);
        TextView txtViTriUserList = row.findViewById(R.id.txt_itemuser_vitri);
//        ImageButton btnGoiUserList = row.findViewById(R.id.btn_itemuser_goi);
//        ImageButton btnNhanTinUserList = row.findViewById(R.id.btn_itemuser_nhantin);
        ImageButton btnEditUserList = row.findViewById(R.id.btn_itemuser_edit);
        ImageButton btnDeleteUserList = row.findViewById(R.id.btn_itemuser_delete);

        final ModalUser modalUser = list.get(i);

        txtMaUserList.setText(modalUser.getMaUser());
        txtTenUserList.setText(modalUser.getTenUser());
        txtViTriUserList.setText(modalUser.getViTriUser());
        Bitmap bmImg = BitmapFactory.decodeByteArray(modalUser.getHinhUser(),0,modalUser.getHinhUser().length);
        imgUserList.setImageBitmap(bmImg);

        btnEditUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ActivityUpdateUser.class);
                i.putExtra("mauser",modalUser.getMaUser());
                context.startActivity(i);
                context.finish();
            }
        });


        btnDeleteUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.delete);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn muốn xóa '"+ txtTenUserList.getText().toString()+"' ra khỏi danh sách nhân viên không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteUser(modalUser.getMaUser());
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

    private void deleteUser(String MaSv) {
        SQLiteDatabase database = Database.initDatabase(context,DATABASE_NAME);
        database.delete("tbNguoiDung","col_ma_tbND=?",new String[]{MaSv});
        Cursor cs = database.rawQuery("select * from tbNguoiDung",null);
        list.clear();
        while (cs.moveToNext()){
            String ma = cs.getString(0);
            String ten = cs.getString(1);
            String vitri = cs.getString(2);
            String user = cs.getString(3);
            String pass = cs.getString(4);
            String diachi = cs.getString(5);
            String email = cs.getString(6);
            String sdt = cs.getString(7);
            byte[] hinh = cs.getBlob(8);

//            Bitmap bitmap = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);
            list.add(new ModalUser(ma,ten,vitri,user,pass,diachi,email,sdt,hinh));
        }
        notifyDataSetChanged();
    }
}
