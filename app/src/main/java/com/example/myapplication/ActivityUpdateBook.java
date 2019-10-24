package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.Database;
import com.example.myapplication.Modal.ModalBook;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ActivityUpdateBook extends AppCompatActivity {
    final String DATABASE_NAME = "IronBookStoreSQLite.sqlite";
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;
    ImageView imgAvatar, btnCamera, btnFolder;
    EditText edtMaSach, edtTenSach, edtSoLuong, edtGiaNhap, edtGiaBan, edtTacGia, edtNxb, edtMota;
    Spinner spnTheLoai;
    Button btnLuu,btnHuy;
    TextView txtStt;
    ImageButton btnPlus,btnMinus;
    ModalBook modalBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        addControls();
        initUI();
        addEvents();
    }

    private void addControls() {
        btnPlus = findViewById(R.id.btn_plus_editbook);
        btnMinus = findViewById(R.id.btn_minus_editbook);


        imgAvatar = findViewById(R.id.img_edit_book);
        btnCamera = findViewById(R.id.btn_camera_editbook);
        btnFolder = findViewById(R.id.btn_folder_editbook);
        btnLuu = findViewById(R.id.btn_luu_editbook);
        btnHuy = findViewById(R.id.btn_huy_editbook);

        edtMaSach = findViewById(R.id.edt_ma_editbook);
        edtTenSach = findViewById(R.id.edt_ten_editbook);
        edtSoLuong = findViewById(R.id.edt_soluong_editbook);
        edtGiaNhap = findViewById(R.id.edt_gianhap_editbook);
        edtGiaBan = findViewById(R.id.edt_giaban_editbook);
        edtTacGia = findViewById(R.id.edt_tacgia_editbook);
        edtMota = findViewById(R.id.edt_mota_editbook);
        edtNxb = findViewById(R.id.edt_nxb_editbook);
        txtStt = findViewById(R.id.txt_stt_editbook);

        spnTheLoai = findViewById(R.id.spn_editbook);
    }

    private void addEvents() {
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sl =Integer.parseInt(edtSoLuong.getText().toString()) ;
                sl++;
                edtSoLuong.setText(sl+"");
            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sl =Integer.parseInt(edtSoLuong.getText().toString()) ;
                sl--;
                edtSoLuong.setText(sl+"");
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(ActivityUpdateBook.this,ActivityBook.class);
                startActivity(i);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBook();
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
    }

    private void initUI() {
        modalBook = new ModalBook();


        Intent intent = getIntent();
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        String sttSach = intent.getStringExtra("sttbook");
        Cursor cs = database.rawQuery("select * from tbSach where col_stt_tbSach=?", new String[]{sttSach});
        cs.moveToFirst();

        String stt = cs.getString(0);
        byte[] hinh = cs.getBlob(1);
        String ma = cs.getString(2);
        String ten = cs.getString(3);
        String theloai = cs.getString(4);
        String tacgia = cs.getString(5);
        String nxb = cs.getString(6);
        String gianhap =cs.getString(7);
        String giaban = cs.getString(8);
        String soluong = cs.getString(9);
        String mota = cs.getString(10);

        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);
        imgAvatar.setImageBitmap(bitmap);

        txtStt.setText(stt);
        edtMaSach.setText(ma);
        edtTenSach.setText(ten);
        edtTacGia.setText(tacgia);
        edtNxb.setText(nxb);
        edtGiaNhap.setText(gianhap);
        edtGiaBan.setText(giaban+"");
        edtSoLuong.setText(soluong+"");
        edtMota.setText(mota);
        ganMaTheLoaiVaoSpiner(theloai);

    }

    private void updateBook() {
        try {
            String stt = txtStt.getText().toString();
            byte[] anh = getByteArrayFromImageView(imgAvatar);
            String ma = edtMaSach.getText().toString();
            String ten = edtTenSach.getText().toString();
            String theloai = spnTheLoai.getSelectedItem().toString();
            String  tacgia = edtTacGia.getText().toString();
            String nxb = edtNxb.getText().toString();
            String gianhap = edtGiaNhap.getText().toString();
            String giaban = edtGiaBan.getText().toString();
            String soluong = edtSoLuong.getText().toString();
            String mota = edtMota.getText().toString();



            ContentValues contentValues = new ContentValues();
            contentValues.put("col_stt_tbSach", stt);
            contentValues.put("col_hinh_tbSach", anh);
            contentValues.put("col_ma_tbSach", ma);
            contentValues.put("col_ten_tbSach", ten);
            contentValues.put("col_matheloai_tbSach", theloai);
            contentValues.put("col_tacgia_tbSach", tacgia);
            contentValues.put("col_nxb_tbSach", nxb);
            contentValues.put("col_gianhap_tbSach", gianhap);
            contentValues.put("col_giaban_tbSach", giaban);
            contentValues.put("col_soluong_tbSach", soluong);
            contentValues.put("col_mota_tbSach", mota);

            SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
            int kq = database.update("tbSach",contentValues, "col_stt_tbSach=?", new String[]{stt});
            if (kq == 0) {
                Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                finish();
                Intent i = new Intent(ActivityUpdateBook.this,ActivityBook.class);
                startActivity(i);

            }

        }catch (Exception e){
            Toast.makeText(this, "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void ganMaTheLoaiVaoSpiner(String value) {
        ArrayList<String> arrMaTheLoai;
        arrMaTheLoai = layMaTheLoai();
        ArrayAdapter<String> adapterTheLoai = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrMaTheLoai);
        adapterTheLoai.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnTheLoai.setAdapter(adapterTheLoai);

        for(int i=0;i< spnTheLoai.getAdapter().getCount();i++) {
            if(adapterTheLoai.getItem(i).equals(value)) {
                spnTheLoai.setSelection(i);
            }
        }


    }

    private ArrayList<String> layMaTheLoai() {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = db.rawQuery("select * from tbTheLoai", null);
        while (cursor.moveToNext()) {
            String matl = cursor.getString(1);
            String tentl = cursor.getString(2);
            arr.add(matl + " - " + tentl );
        }
        cursor.close();
        return arr;
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
