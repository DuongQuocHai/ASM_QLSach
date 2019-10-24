package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.AdapterCategory;
import com.example.myapplication.Adapter.AdapterUser;
import com.example.myapplication.Database.Database;
import com.example.myapplication.Modal.ModalCategory;
import com.example.myapplication.Modal.ModalUser;

import java.util.ArrayList;

public class ActivityCategory extends AppCompatActivity {
    final String DATABASE_NAME = "IronBookStoreSQLite.sqlite";
    SQLiteDatabase database;
    ArrayList<ModalCategory> list;
    AdapterCategory adapterCategory;

    ImageView btnBackTl;
    ListView lvTheLoai;
    TextView txtStt;
    EditText edtMaTl, edtTenTl, edtVitriTl;
    Button btnAddTl, btnEditTl;

    int stt = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        addControls();
        addEnvents();
        readData();
    }

    private void addControls() {
        lvTheLoai = findViewById(R.id.lv_category);
        btnBackTl = findViewById(R.id.btn_back_category);
        edtMaTl = findViewById(R.id.edt_ma_category);
        edtTenTl = findViewById(R.id.edt_ten_category);
        edtVitriTl = findViewById(R.id.edt_vitri_category);
        btnAddTl = findViewById(R.id.btn_add_category);
        btnEditTl = findViewById(R.id.btn_edit_category);
        txtStt = findViewById(R.id.txtstt);

        list = new ArrayList<>();
        adapterCategory = new AdapterCategory(ActivityCategory.this, list);
        lvTheLoai.setAdapter(adapterCategory);
    }

    private void addEnvents() {
        btnBackTl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAddTl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (catchFromEmty() && catchSameMa()) {
                    insertCategory();
                    edtMaTl.setText("");
                    edtTenTl.setText("");
                    edtVitriTl.setText("");
                }

            }
        });
        btnEditTl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCtagory();
                edtMaTl.setText("");
                edtTenTl.setText("");
                edtVitriTl.setText("");
            }
        });

        lvTheLoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edtMaTl.setText(list.get(i).getMaTheLoai());
                edtTenTl.setText(list.get(i).getTenTheLoai());
                edtVitriTl.setText(list.get(i).getViTriTheLoai());
                txtStt.setText(list.get(i).getSttTl());
                btnEditTl.setEnabled(true);
            }
        });


    }


    public void readData() {
        database = (SQLiteDatabase) Database.initDatabase(this, DATABASE_NAME);
        Cursor cs = database.rawQuery("SELECT * FROM tbTheLoai", null);

        list.clear();
        for (int i = 0; i < cs.getCount(); i++) {
            cs.moveToPosition(i);
            String stt = cs.getString(0);
            String ma = cs.getString(1);
            String ten = cs.getString(2);
            String vitri = cs.getString(3);

            list.add(new ModalCategory(ma, ten, vitri, stt));
        }
        adapterCategory.notifyDataSetChanged();

    }

    private void insertCategory() {
        String ma = edtMaTl.getText().toString();
        String ten = edtTenTl.getText().toString();
        String vitri = edtVitriTl.getText().toString();
        Toast.makeText(ActivityCategory.this, "haha", Toast.LENGTH_SHORT).show();

        ContentValues contentValues = new ContentValues();
        contentValues.put("col_ma_tbTheLoai", ma);
        contentValues.put("col_ten_tbTheLoai", ten);
        contentValues.put("col_vitri_tbTheLoai", vitri);

        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        int kq = (int) database.insert("tbTheLoai", null, contentValues);
        if (kq == 0) {
            Toast.makeText(ActivityCategory.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ActivityCategory.this, "Thêm Thành công", Toast.LENGTH_SHORT).show();
        }
        readData();
    }

    private void updateCtagory() {
        String stt = txtStt.getText().toString();
        String ma = edtMaTl.getText().toString();
        String ten = edtTenTl.getText().toString();
        String vitri = edtVitriTl.getText().toString();
        Toast.makeText(this, stt, Toast.LENGTH_SHORT).show();

        ContentValues contentValues = new ContentValues();
        contentValues.put("col_ma_tbTheLoai", ma);
        contentValues.put("col_ten_tbTheLoai", ten);
        contentValues.put("col_vitri_tbTheLoai", vitri);

        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        int kq = database.update("tbTheLoai", contentValues, "col_stt_tbTheLoai=?", new String[]{stt});
        if (kq == 0) {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            readData();
        }

    }

    private boolean catchFromEmty() {
        String ma = edtMaTl.getText().toString();
        String ten = edtTenTl.getText().toString();
        String vitri = edtVitriTl.getText().toString();
        if (ma.length() <= 0 || ten.length() <= 0 || vitri.length() <= 0) {
            Toast.makeText(this, "Bạn phải nhập đủ thông tin trước khi thêm mới", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean catchSameMa() {
        String ma = edtMaTl.getText().toString();
        String ten = edtTenTl.getText().toString();
        Cursor cursor = database.rawQuery("select * from tbTheLoai", null);
        if (cursor.moveToFirst()) {
            do {
                String madb = cursor.getString(1);
                String tendndb = cursor.getString(2);
                if (madb.equals(ma)) {
                    Toast.makeText(this, "Mã thể loại đã được tạo!", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (tendndb.equals(ten)) {
                    Toast.makeText(this, "Tên thể loại đã được tạo!", Toast.LENGTH_SHORT).show();
                    return false;
                }

            } while (cursor.moveToNext());
        }
        return true;
    }


}
