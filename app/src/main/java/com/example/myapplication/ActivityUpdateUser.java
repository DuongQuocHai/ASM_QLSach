package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class ActivityUpdateUser extends AppCompatActivity {
    final String DATABASE_NAME = "IronBookStoreSQLite.sqlite";
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;

    Button btnLuu, btnHuy;
    ImageView ivAvatar, btnCamera, btnFolder;
    EditText  edtTenNd, edtViTriNd, edtDiaChiNd, edtEmailNd, edtSdtNd;
    TextView txtTenDnNd,txtMaNd;

    String maUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        addControls();
        initUI();
        addEvents();
    }

    private void addControls() {
        btnHuy = findViewById(R.id.btnhuy_editnd);
        btnLuu = findViewById(R.id.btnluu_editnd);

        ivAvatar = findViewById(R.id.imgnd_editnd);

        btnCamera = findViewById(R.id.btnchuphinh_editnd);
        btnFolder = findViewById(R.id.btnchonhinh_editnd);

        txtMaNd = findViewById(R.id.txtmand_editnd);
        edtTenNd = findViewById(R.id.edttennd_editnd);
        edtViTriNd = findViewById(R.id.edtvitri_editnd);
        edtDiaChiNd = findViewById(R.id.edtdiachi_editnd);
        edtEmailNd = findViewById(R.id.edtemail_editnd);
        edtSdtNd = findViewById(R.id.edtsdt_editnd);

        txtTenDnNd = findViewById(R.id.txtusernd_editnd);

    }

    private void addEvents() {
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

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityUpdateUser.this,ActivityListUser.class);
                startActivity(i);
                finish();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();

            }
        });
    }

    private void initUI() {
        Intent intent = getIntent();
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        maUser = intent.getStringExtra("mauser");
        Cursor cs = database.rawQuery("select * from tbNguoiDung where col_ma_tbND=?", new String[]{maUser});
        cs.moveToFirst();

        String ma = cs.getString(0);
        String ten = cs.getString(1);
        String vitri = cs.getString(2);
        String user = cs.getString(3);
        String diachi = cs.getString(5);
        String email = cs.getString(6);
        String sdt = cs.getString(7);
        byte[] hinh = cs.getBlob(8);

        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);
        ivAvatar.setImageBitmap(bitmap);

        txtMaNd.setText(ma);
        edtTenNd.setText(ten);

        edtViTriNd.setText(vitri);
        edtDiaChiNd.setText(diachi);
        edtEmailNd.setText(email);
        edtSdtNd.setText(sdt);
        txtTenDnNd.setText(user);


    }

    private void updateUser() {
        try {
            String maNd = txtMaNd.getText().toString();
            String tenNd = edtTenNd.getText().toString();
            String viTriNd = edtViTriNd.getText().toString();
            String userNd = txtTenDnNd.getText().toString();
            String diaChiNd = edtDiaChiNd.getText().toString();
            String emailNd = edtEmailNd.getText().toString();
            String sdtNd = edtSdtNd.getText().toString();

            byte[] anhND = getByteArrayFromImageView(ivAvatar);

            ContentValues contentValues = new ContentValues();
            contentValues.put("col_hoten_tbND", tenNd);
            contentValues.put("col_vitri_tbND", viTriNd);
            contentValues.put("col_user_tbND", userNd);
            contentValues.put("col_diachi_tbND", diaChiNd);
            contentValues.put("col_email_ND", emailNd);
            contentValues.put("col_sdt_tbND", sdtNd);
            contentValues.put("col_anh_tbND", anhND);

            SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
            int kq = database.update("tbNguoiDung",contentValues, "col_ma_tbND=?", new String[]{maNd});
            if (kq == 0) {
                Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                finish();
                Intent i = new Intent(ActivityUpdateUser.this,ActivityListUser.class);
                startActivity(i);

            }

        }catch (Exception e){
            Toast.makeText(this, "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
            finish();
        }

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
            ivAvatar.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CHOOSE_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ivAvatar.setImageBitmap(bitmap);
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
