package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Adapter.AdapterUser;
import com.example.myapplication.Database.Database;
import com.example.myapplication.Modal.ModalUser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ActivityListUser extends AppCompatActivity {
    final String DATABASE_NAME = "IronBookStoreSQLite.sqlite";
    SQLiteDatabase database;
    ArrayList<ModalUser> list;
    AdapterUser adapterUser;
    ListView lvUser;
    ImageButton btnAddUser, btnback;

    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;

    ImageView imgAvatar, btnCamera, btnFolder;
    EditText edtMa, edtTen, edtViTri, edtTenDn, edtMkDn, edtReMkDn, edtDiaChi, edtSdt, edtEmail;
    Button btnThem, btnHuy,btnLamMoi;

    String ma, ten, vitri, tendn, matkhau, nhaplaimk, diachi, email, sdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        addControls();
        addEnvents();
        readData();
    }

    private void addControls() {
        lvUser = findViewById(R.id.lv_user);
        btnAddUser = findViewById(R.id.btn_adduser);
        btnback = findViewById(R.id.btn_backlistuser);

        list = new ArrayList<>();
        adapterUser = new AdapterUser(this, list);
        lvUser.setAdapter(adapterUser);
    }

    private void addEnvents() {
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void getString() {
        ma = edtMa.getText().toString();
        ten = edtTen.getText().toString();
        vitri = edtViTri.getText().toString();
        tendn = edtTenDn.getText().toString();
        matkhau = edtMkDn.getText().toString();
        diachi = edtDiaChi.getText().toString();
        email = edtEmail.getText().toString();
        sdt = edtSdt.getText().toString();

        nhaplaimk = edtReMkDn.getText().toString();
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_user);

        imgAvatar = dialog.findViewById(R.id.img_adduser);
        btnCamera = dialog.findViewById(R.id.btnchuphinh_adduser);
        btnFolder = dialog.findViewById(R.id.btnchonhinh_adduser);
        edtMa = dialog.findViewById(R.id.edtmand_adduser);
        edtTen = dialog.findViewById(R.id.edttennd_adduser);
        edtViTri = dialog.findViewById(R.id.edtvitri_adduser);
        edtTenDn = dialog.findViewById(R.id.edttendnnd_adduser);
        edtMkDn = dialog.findViewById(R.id.edtmkdnnd_adduser);
        edtReMkDn = dialog.findViewById(R.id.edtremkdnnd_adduser);
        edtDiaChi = dialog.findViewById(R.id.edtdiachi_adduser);
        edtEmail = dialog.findViewById(R.id.edtemail_adduser);
        edtSdt = dialog.findViewById(R.id.edtsdt_adduser);
        btnThem = dialog.findViewById(R.id.btnthem_adduser);
        btnHuy = dialog.findViewById(R.id.btnhuy_adduser);
        btnLamMoi = dialog.findViewById(R.id.btnlammoi_adduser);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getString();
                if (catchFormEmptyAddSv() && catchSameInfotyAddSv(ma, tendn)) {
                    insertUser();

                }
            }
        });
        btnLamMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtMa.setText("");
                edtTen.setText("");
                edtViTri.setText("");
                edtTenDn.setText("");
                edtMkDn.setText("");
                edtReMkDn.setText("");
                edtDiaChi.setText("");
                edtEmail.setText("");
                edtSdt.setText("");
                imgAvatar.setImageResource(R.drawable.avatar);
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                readData();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeImg();

            }
        });

        btnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImg();

            }
        });
        dialog.show();
    }


    public void readData() {
        database = (SQLiteDatabase) Database.initDatabase(this, DATABASE_NAME);
        Cursor cs = database.rawQuery("SELECT * FROM tbNguoiDung", null);
        list.clear();
        for (int i = 1; i < cs.getCount(); i++) {
            cs.moveToPosition(i);
            String ma = cs.getString(0);
            String ten = cs.getString(1);
            String vitri = cs.getString(2);
            String user = cs.getString(3);
            String pass = cs.getString(4);
            String diachi = cs.getString(5);
            String email = cs.getString(6);
            String sdt = cs.getString(7);
            byte[] hinh = cs.getBlob(8);
            list.add(new ModalUser(ma, ten, vitri, user, pass, diachi, email, sdt, hinh));
        }
        adapterUser.notifyDataSetChanged();

    }

    private void insertUser() {

        byte[] avatar = getByteArrayFromImageView(imgAvatar);

        ContentValues contentValues = new ContentValues();
        contentValues.put("col_ma_tbND", ma);
        contentValues.put("col_hoten_tbND", ten);
        contentValues.put("col_vitri_tbND", vitri);
        contentValues.put("col_user_tbND", tendn);
        contentValues.put("col_pass_tbND", matkhau);
        contentValues.put("col_diachi_tbND", diachi);
        contentValues.put("col_email_ND", email);
        contentValues.put("col_sdt_tbND", sdt);
        contentValues.put("col_anh_tbND", avatar);

        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        int kq = (int) database.insert("tbNguoiDung", null, contentValues);
        if (kq == 0) {
            Toast.makeText(ActivityListUser.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ActivityListUser.this, "Thêm Thành công", Toast.LENGTH_SHORT).show();
        }
        readData();

    }

    private boolean catchFormEmptyAddSv() {
        if (ma.length() <= 0 || ten.length() <= 0 || vitri.length() <= 0 ||
                tendn.length() <= 0 || matkhau.length() <= 0 || nhaplaimk.length() <= 0) {
            Toast.makeText(this, "Bạn chưa nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!matkhau.equals(nhaplaimk)) {
            Toast.makeText(this, "Mật khẩu không trùng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (sdt.length() > 0 && !Patterns.PHONE.matcher(sdt).matches()) {
            Toast.makeText(this, "SĐT không đúng định dạng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.length() > 0 && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private boolean catchSameInfotyAddSv(String ma, String ten) {
        Cursor cursor = database.rawQuery("select * from tbNguoiDung", null);
        if (cursor.moveToFirst()) {
            do {
                String madb = cursor.getString(0);
                String tendndb = cursor.getString(3);
                if (madb.equals(ma)) {
                    Toast.makeText(this, "Mã người dùng đã được tạo!", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (tendndb.equals(ten)) {
                    Toast.makeText(this, "Tên đăng nhập đã được tạo!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } while (cursor.moveToNext());

        }
        return true;
    }

    private void takeImg() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    private void chooseImg() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAvatar.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CHOOSE_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] getByteArrayFromImageView(ImageView imgv) {
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }
}
