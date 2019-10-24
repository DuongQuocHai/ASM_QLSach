package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.AdapterBook;
import com.example.myapplication.Database.Database;
import com.example.myapplication.Modal.ModalBook;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ActivityBook extends AppCompatActivity {
    final String DATABASE_NAME = "IronBookStoreSQLite.sqlite";

    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;

    SQLiteDatabase database;
    ArrayList<ModalBook> list;
    AdapterBook adapterBook;
    ArrayList<String> arrMaTheLoai;

    ImageView img, btnCamera, btnFolder, btnShowTheLoai;
    EditText edtMa, edtTen, edtSoLuong, edtGiaNhap, edtGiaBan, edtTacGia, edtNxb, edtMoTa, edtSearch;
    Spinner spnTheLoai;
    Button btnThem, btnHuy, btnLamMoi;

    String ma, ten, tacgia, nxb, mota, matheloai;
    //    float soluong, giaban, gianhap;
    String soluong, giaban, gianhap;
    int stt;
    byte[] anh;


    ListView lvBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        addControls();
        readData();

    }

    private void addControls() {
        lvBook = findViewById(R.id.lv_book);
        edtSearch = findViewById(R.id.edt_seach_book);

        list = new ArrayList<>();
        adapterBook = new AdapterBook(ActivityBook.this, list);
        lvBook.setAdapter(adapterBook);
    }

    public void clickBook(View view) {
        switch (view.getId()) {
            case R.id.btn_back_book:
                finish();
                break;
            case R.id.btn_add_book:
                showDialogAddBook();
                break;
            case R.id.btn_search_book:
                thuchientim();
                break;
            case R.id.btn_refresh_book:
                readData();
                break;


        }

    }

    private void showDialogAddBook() {
        final Dialog dialog = new Dialog(ActivityBook.this);
        dialog.setContentView(R.layout.dialog_add_book);

        img = dialog.findViewById(R.id.img_addbook);
        btnCamera = dialog.findViewById(R.id.btn_camera_addbook);
        btnFolder = dialog.findViewById(R.id.btn_folder_addbook);
        btnShowTheLoai = dialog.findViewById(R.id.btn_showtl_addbook);

        edtMa = dialog.findViewById(R.id.edt_ma_addbook);
        edtTen = dialog.findViewById(R.id.edt_ten_addbook);
        edtSoLuong = dialog.findViewById(R.id.edt_soluong_addbook);
        edtGiaNhap = dialog.findViewById(R.id.edt_gianhap_addbook);
        edtGiaBan = dialog.findViewById(R.id.edt_giaban_addbook);
        edtTacGia = dialog.findViewById(R.id.edt_tacgia_addbook);
        edtNxb = dialog.findViewById(R.id.edt_nxb_addbook);
        edtMoTa = dialog.findViewById(R.id.edt_mota_addbook);

        spnTheLoai = dialog.findViewById(R.id.spn_theloai_addbook);

        btnThem = dialog.findViewById(R.id.btn_them_addbook);
        btnHuy = dialog.findViewById(R.id.btn_huy_addbook);
        btnLamMoi = dialog.findViewById(R.id.btn_lammoi_addbook);
        ganMaTheLoaiVaoSpiner();

        btnShowTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent i = new Intent(ActivityBook.this, ActivityCategory.class);
                startActivity(i);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getString();
                if (catchEmtyFormAddBook() && catchSameInfotyAddSv(ma, ten)) {
                    insertBook();
                }
            }
        });
        btnLamMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtMa.setText("");
                edtTen.setText("");
                edtSoLuong.setText("");
                edtGiaNhap.setText("");
                edtGiaBan.setText("");
                edtTacGia.setText("");
                edtMoTa.setText("");
                edtNxb.setText("");

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
        if (arrMaTheLoai.size() == 0) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityBook.this);
            builder.setMessage("Thể loại trống, bạn cần phải thêm thể loại");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent iii = new Intent(ActivityBook.this, ActivityCategory.class);
                    startActivity(iii);
                }
            });
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        } else
            dialog.show();


    }

    private void readData() {
        database = (SQLiteDatabase) Database.initDatabase(this, DATABASE_NAME);
        Cursor cs = database.rawQuery("SELECT * FROM tbSach", null);
        list.clear();
        for (int i = 0; i < cs.getCount(); i++) {
            cs.moveToPosition(i);
            String stt = cs.getString(0);
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

            list.add(new ModalBook(stt, hinh, ma, ten, matheloai, tacgia, nhaxb, gianhap, giaban, soluong, mota));
        }
        adapterBook.notifyDataSetChanged();

    }

    private void getString() {
        anh = getByteArrayFromImageView(img);
        ma = edtMa.getText().toString();
        ten = edtTen.getText().toString();
        matheloai = spnTheLoai.getSelectedItem().toString();
        tacgia = edtTacGia.getText().toString();
        nxb = edtNxb.getText().toString();
//        gianhap = Float.parseFloat(edtGiaNhap.getText().toString());
//        giaban = Float.parseFloat(edtGiaBan.getText().toString());
//        soluong = Float.parseFloat(edtSoLuong.getText().toString());

        gianhap = edtGiaNhap.getText().toString();
        giaban = edtGiaBan.getText().toString();
        soluong = edtSoLuong.getText().toString();

        mota = edtMoTa.getText().toString();
    }

    private void insertBook() {
        getString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("col_hinh_tbSach", anh);
        contentValues.put("col_ma_tbSach", ma);
        contentValues.put("col_ten_tbSach", ten);
        contentValues.put("col_matheloai_tbSach", matheloai);
        contentValues.put("col_tacgia_tbSach", tacgia);
        contentValues.put("col_nxb_tbSach", nxb);
        contentValues.put("col_gianhap_tbSach", gianhap);
        contentValues.put("col_giaban_tbSach", giaban);
        contentValues.put("col_soluong_tbSach", soluong);
        contentValues.put("col_mota_tbSach", mota);

        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        int kq = (int) database.insert("tbSach", null, contentValues);
        if (kq == 0) {
            Toast.makeText(ActivityBook.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ActivityBook.this, "Thêm Thành công", Toast.LENGTH_SHORT).show();
        }
        readData();
    }

    private boolean catchEmtyFormAddBook() {
        if (ma.length() == 0 || ten.length() == 0 || soluong.length() <= 0 || gianhap.length() <= 0 || giaban.length() <= 0) { //|| arrMaTheLoai.size()==0
            Toast.makeText(this, "Bạn phải nhập đủ các thuộc tính của sách", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }


    private boolean catchSameInfotyAddSv(String ma, String ten) {
        Cursor cursor = database.rawQuery("select * from tbSach", null);
        if (cursor.moveToFirst()) {
            do {
                String madb = cursor.getString(2);
                String tendndb = cursor.getString(3);
                if (madb.equals(ma)) {
                    Toast.makeText(this, "Mã sách đã được tạo!", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (tendndb.equals(ten)) {
                    Toast.makeText(this, "Tên sách đã được tạo!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } while (cursor.moveToNext());

        }
        return true;
    }

    public void thuchientim() {
        ArrayList<String> arrayList = timSach(edtSearch.getText().toString());
        adapterBook = new AdapterBook(this, list);
        lvBook.setAdapter(adapterBook);

    }

    public ArrayList<String> timSach(String chuoitim) {
        ArrayList<String> arr = new ArrayList<>();
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cs = database.rawQuery("select * from tbSach " +
                        "where col_ma_tbSach like '%" + chuoitim + "%'" +
                        "or col_ten_tbSach like '%" + chuoitim + "%'" +
                        "or col_nxb_tbSach like '%" + chuoitim + "%'" +
                        "or col_mota_tbSach like '%" + chuoitim + "%'" +
                        "or col_matheloai_tbSach like '%" + chuoitim + "%'" +
                        "or col_tacgia_tbSach like '%" + chuoitim + "%'",
                null);
        list.clear();
        while (cs.moveToNext()) {
            String stt = cs.getString(0);
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

            list.add(new ModalBook(stt, hinh, ma, ten, matheloai, tacgia, nhaxb, gianhap, giaban, soluong, mota));
            ModalBook book = new ModalBook(stt, hinh, ma, ten, matheloai, tacgia, nhaxb, gianhap, giaban, soluong, mota);
            arr.add(stt);
        }

        cs.close();
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
            img.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CHOOSE_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
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

    private void ganMaTheLoaiVaoSpiner() {
        arrMaTheLoai = layMaTheLoai();
        ArrayAdapter<String> adapterTheLoai = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrMaTheLoai);
        adapterTheLoai.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);// không có dòng này thì hiển thị không đẹp
        spnTheLoai.setAdapter(adapterTheLoai);
    }

    private ArrayList<String> layMaTheLoai() {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = db.rawQuery("select * from tbTheLoai", null);
        while (cursor.moveToNext()) {
            String matl = cursor.getString(1);
            String tentl = cursor.getString(2);
            arr.add(matl + " - " + tentl);
        }
        cursor.close();
        return arr;
    }
}
