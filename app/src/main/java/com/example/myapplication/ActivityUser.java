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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.Database;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ActivityUser extends AppCompatActivity {
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;
    final String DATABASE_NAME = "IronBookStoreSQLite.sqlite";
    SQLiteDatabase database;

    //activity user
    ImageButton btnShowListUser, btnBack;
    Button btnChangePass, btneditAdmin;
    ImageView ivAvatarUser;
    TextView txtTenUser, txtViTriUser, txtMaUser, txtTenDnUser, txtDiaChiUser, txtEmailUser, txtSdtUser;

    //dialog edit
    ImageView imgAvatar, btnCamera, btnFolder;
    Button btnLuu, btnHuy;
    EditText edtMa, edtTen, edtVitri, edtDiachi, edtEmail, edtSdt;
    TextView txtUser;

    String user;
    String ma, ten, vitri, diachi, email, sdt;
    byte[] hinh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        addControls();
        initUI();
        addEnvents();

        if (!user.equals("admin")) {
            btnShowListUser.setVisibility(View.INVISIBLE);
        }

    }

    private void addControls() {
        btnShowListUser = findViewById(R.id.btn_showlistuser);
        btnBack = findViewById(R.id.btn_backuser);
        btnChangePass = findViewById(R.id.btn_changpass_user);
        btneditAdmin = findViewById(R.id.btneditadmin);

        txtTenUser = findViewById(R.id.txt_hoten_user);
        txtViTriUser = findViewById(R.id.txt_vitri_user);
        txtMaUser = findViewById(R.id.txt_manv_user);
        txtTenDnUser = findViewById(R.id.txt_tendn_user);
        txtDiaChiUser = findViewById(R.id.txt_diachi_user);
        txtEmailUser = findViewById(R.id.txt_email_user);
        txtSdtUser = findViewById(R.id.txt_sdt_user);
        ivAvatarUser = findViewById(R.id.img_avatar_user);
    }

    private void initUI() {
        Intent intent = getIntent();
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        user = intent.getStringExtra("user2");
        Cursor cs = database.rawQuery("select * from tbNguoiDung where col_user_tbND=?", new String[]{user});
        cs.moveToFirst();

        ma = cs.getString(0);
        ten = cs.getString(1);
        vitri = cs.getString(2);
        diachi = cs.getString(5);
        email = cs.getString(6);
        sdt = cs.getString(7);
        hinh = cs.getBlob(8);

        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);
        ivAvatarUser.setImageBitmap(bitmap);

        txtMaUser.setText(ma);
        txtTenUser.setText(ten);

        txtViTriUser.setText(vitri);
        txtDiaChiUser.setText(diachi);
        txtEmailUser.setText(email);
        txtSdtUser.setText(sdt);


    }

    private void addEnvents() {

        btnShowListUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityUser.this, ActivityListUser.class);
                startActivity(i);
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogChagePass();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btneditAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogPass();
            }
        });

    }


    public void showDialogChagePass() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_changepass);

        final EditText edtmkTruoc = dialog.findViewById(R.id.edt_mkcu_dialog_changepass);
        final EditText edtmkSau = dialog.findViewById(R.id.edt_mkmoi_dialog_changepass);
        final EditText edtreMkSau = dialog.findViewById(R.id.edt_nhaplaimkmoi_dialog_changepass);
        Button btnLuu = dialog.findViewById(R.id.btn_xacnhan_dialog_changepass);
        Button btnHuy = dialog.findViewById(R.id.btn_huy_dialog_changepass);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mksau = edtmkSau.getText().toString();
                String mkresau = edtreMkSau.getText().toString();
                if (checkChangePasss(user, edtmkTruoc.getText().toString())) {
                    if (mksau.trim().length() != 0 && mkresau.trim().length() != 0 ){
                        if(mkresau.equals(mkresau)) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("col_pass_tbND", edtmkSau.getText().toString());

                            SQLiteDatabase database = Database.initDatabase(ActivityUser.this, DATABASE_NAME);
                            int kq = database.update("tbNguoiDung", contentValues, "col_user_tbND=?", new String[]{user});
                            if (kq == 0) {
                                Toast.makeText(ActivityUser.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityUser.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        } else
                            Toast.makeText(ActivityUser.this, "Mật khẩu nhập không trùng nhau!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ActivityUser.this, "Bạn phải nhập đầy đủ cả trường", Toast.LENGTH_SHORT).show();
                    }

                } else
                    Toast.makeText(ActivityUser.this, "Mật khẩu cũ không đúng!", Toast.LENGTH_SHORT).show();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void showDialogPass() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_pass);

        final EditText edtPass = dialog.findViewById(R.id.edt_pas_dialogpass);
        Button btnXacNhan = dialog.findViewById(R.id.btn_xacnhan_dialogpass);
        Button btnHuy = dialog.findViewById(R.id.btn_huy_dialogpass);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkChangePasss(user, edtPass.getText().toString())) {
                    dialog.dismiss();
                    showDialogEditAdmin();
                } else
                    Toast.makeText(ActivityUser.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        });


        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showDialogEditAdmin() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_editadmin);


        imgAvatar = dialog.findViewById(R.id.img_editadmin);
        btnCamera = dialog.findViewById(R.id.btn_camera_editadmin);
        btnFolder = dialog.findViewById(R.id.btn_folder_editadmin);

        btnLuu = dialog.findViewById(R.id.btn_luu_editadmin);
        btnHuy = dialog.findViewById(R.id.btn_huy_editadmin);


        edtMa = dialog.findViewById(R.id.edt_ma_editadmin);

        edtTen = dialog.findViewById(R.id.edt_ten_editadmin);
        edtVitri = dialog.findViewById(R.id.edt_vitri_editadmin);
        edtDiachi = dialog.findViewById(R.id.edt_diachi_editadmin);
        edtEmail = dialog.findViewById(R.id.edt_email_editadmin);
        edtSdt = dialog.findViewById(R.id.edt_sdt_editadmin);
        txtUser = dialog.findViewById(R.id.txt_user_editadmin);

        edtMa.setText(ma);
        edtTen.setText(ten);
        edtVitri.setText(vitri);
        edtDiachi.setText(diachi);
        edtSdt.setText(sdt);
        edtEmail.setText(email);
        txtUser.setText(user);
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);
        imgAvatar.setImageBitmap(bitmap);

        if (!user.equals("admin")){
            edtMa.setEnabled(false);
            edtVitri.setEnabled(false);
        }

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updateAdmin()) {
                    dialog.dismiss();
                    initUI();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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

    private boolean updateAdmin() {
        try {
            String maad = edtMa.getText().toString();
            String tenad = edtTen.getText().toString();
            String vitriad = edtVitri.getText().toString();
            String userad = txtUser.getText().toString();
            String diachiad = edtDiachi.getText().toString();
            String emailad = edtEmail.getText().toString();
            String sdtad = edtSdt.getText().toString();

            byte[] anhad = getByteArrayFromImageView(imgAvatar);

            ContentValues contentValues = new ContentValues();
            contentValues.put("col_hoten_tbND", tenad);
            contentValues.put("col_vitri_tbND", vitriad);
            contentValues.put("col_ma_tbND", maad);
            contentValues.put("col_email_ND", emailad);
            contentValues.put("col_diachi_tbND", diachiad);
            contentValues.put("col_sdt_tbND", sdtad);
            contentValues.put("col_anh_tbND", anhad);

            SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
            int kq = database.update("tbNguoiDung", contentValues, "col_user_tbND=?", new String[]{userad});
            if (kq == 0) {
                Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                return true;
            }

        } catch (Exception e) {

        }
        return true;

    }

    private boolean checkChangePasss(String user, String passold) {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("select * from tbNguoiDung", null);
        if (cursor.moveToFirst()) {
            do {
                String userdb = cursor.getString(3);
                if (userdb.equals(user)) {
                    String passdb = cursor.getString(4);
                    if (passdb.equals(passold)) {
                        return true;
                    } else return false;
                }
            } while (cursor.moveToNext());
        }
        return false;

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
