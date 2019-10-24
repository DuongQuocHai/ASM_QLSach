package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.AdapterAddBill;
import com.example.myapplication.Adapter.AdapterBook;
import com.example.myapplication.Database.Database;
import com.example.myapplication.Modal.ModalAddBill;
import com.example.myapplication.Modal.ModalBill;
import com.example.myapplication.Modal.ModalBook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ActivityAddBill extends AppCompatActivity {
    final String DATABASE_NAME = "IronBookStoreSQLite.sqlite";
    SQLiteDatabase database;
    LinearLayout viewHdct, viewHd, viewInfoSach;
    EditText edtMaHd, edtMaSach, edtSoLuong;
    public TextView txtShowTtHd, txtNgay, txtGiaSach, txtThanhTien, txtSoLuongSach, txtTenSach;
    ListView lvAddBill;

    String mahd, ngay, masach, soluong, thanhtien, dongia;

    String giabandb, tensachdb, soluongsachdb;

    int tongtien = 0;

    ArrayList<ModalAddBill> list;
    AdapterAddBill adapterAddBill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        addControls();
        getString();
    }

    private void addControls() {
        edtMaHd = findViewById(R.id.edt_mahd_addbill);
        edtMaSach = findViewById(R.id.edt_masach_addbill);
        edtSoLuong = findViewById(R.id.edt_soluong_addbill);

        txtNgay = findViewById(R.id.txt_ngay_addbill);
        txtThanhTien = findViewById(R.id.txt_thanhtien_addbill);
        txtGiaSach = findViewById(R.id.txt_giasach_addbill);
        txtTenSach = findViewById(R.id.txt_tensach_addbill);
        txtSoLuongSach = findViewById(R.id.txt_soluongsach_addbill);

        lvAddBill = findViewById(R.id.lv_addbill);

        viewHdct = findViewById(R.id.view_hoadonchitiet);
        viewHd = findViewById(R.id.view_hoadon);
        viewInfoSach = findViewById(R.id.view_ifsach_addbill);
        txtShowTtHd = findViewById(R.id.txt_showtthd_addbill);

        list = new ArrayList<>();
        adapterAddBill = new AdapterAddBill(ActivityAddBill.this, list);
        lvAddBill.setAdapter(adapterAddBill);


        edtMaSach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (checkMaSach()) {
                    viewInfoSach.setVisibility(View.VISIBLE);
                    txtGiaSach.setText(giabandb);
                    txtTenSach.setText(tensachdb);
                    txtSoLuongSach.setText(soluongsachdb);

                } else if (masach.trim().length() > 0) {
                    viewInfoSach.setVisibility(View.GONE);
                    txtGiaSach.setText("");
                    txtTenSach.setText("");
                    txtSoLuongSach.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void getString() {
        mahd = edtMaHd.getText().toString();
        ngay = txtNgay.getText().toString();
        masach = edtMaSach.getText().toString();
        thanhtien = txtThanhTien.getText().toString();
        soluong = edtSoLuong.getText().toString();
        dongia = txtGiaSach.getText().toString();


    }

    public void enventAddBill(View view) {
        getString();
        switch (view.getId()) {
            case R.id.btn_next_addbill:
                if (catchEmtyForm()) {
                    viewHdct.setVisibility(View.VISIBLE);
                    viewHd.setVisibility(View.GONE);
                    txtShowTtHd.setVisibility(View.VISIBLE);
                    txtShowTtHd.setText(mahd + " - " + ngay);
                }
                break;
            case R.id.txt_showtthd_addbill:
                viewHdct.setVisibility(View.GONE);
                viewHd.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_thanhtoan_addbill:
                insertBill();
                finish();
                Intent intent = new Intent(ActivityAddBill.this,ActivityBill.class);
                startActivity(intent);
                break;
            case R.id.btn_huy1_addbill:
                finish();
                break;
            case R.id.btn_huy2_addbill:
                finish();
                break;
            case R.id.btn_showdate_addbill:
                chonNgay();
                break;
            case R.id.btn_them_addbill:
                if (!checkMaSach()) {
                    Toast.makeText(ActivityAddBill.this, "Mã sách không tồn tại", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(soluong) > Integer.parseInt(soluongsachdb)) {
                    Toast.makeText(this, "Số lượng sách không đủ", Toast.LENGTH_SHORT).show();
                } else {
                    addSach();
                    edtMaSach.setText("");
                    edtSoLuong.setText("");
                }

                break;
        }
    }


    private void insertBill() {
        getString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("col_ma_tbXuat", mahd);
        contentValues.put("col_ngay_tbXuat", ngay);
        contentValues.put("col_masach_tbXuat", masach);
        contentValues.put("col_soluong_tbXuat", soluong);
        contentValues.put("col_thanhtien_tbXuat", thanhtien);

        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        int kq = (int) database.insert("tbHoaDon", null, contentValues);
        if (kq == 0) {
            Toast.makeText(ActivityAddBill.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ActivityAddBill.this, "Thêm Thành công", Toast.LENGTH_SHORT).show();
        }
    }

    private void addSach() {
        float dg = Float.parseFloat(dongia);
        int sl = Integer.parseInt(soluong);
        float tt = dg * sl;
        tongtien += tt;
        txtThanhTien.setText(tongtien + "");
        int i;
        for (i = 0; i < list.size(); i++) {
            if (masach.equals(list.get(i).getMasach())) {
                int slm = list.get(i).getSoluong() + sl;
                float ttm = list.get(i).getThanhtien() + tt;
                list.set(i, new ModalAddBill(ttm, dg, slm, masach));
                break;
            }
        }
        if (i >= list.size()) {
            list.add(new ModalAddBill(tt, dg, sl, masach));
        }


        adapterAddBill.notifyDataSetChanged();
    }

    private boolean catchEmtyForm() {
        if (mahd.trim().length() == 0 || ngay.equals("Chọn ngày")) {
            Toast.makeText(this, "Bạn cần nhập đủ thông tin hóa đơn", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkMaSach() {
        getString();
        database = (SQLiteDatabase) Database.initDatabase(this, DATABASE_NAME);
        Cursor cs = database.rawQuery("SELECT * FROM tbSach", null);
        if (cs.moveToFirst()) {
            do {
                String masachdb = cs.getString(2);
                giabandb = cs.getString(8);
                tensachdb = cs.getString(3);
                soluongsachdb = cs.getString(9);

                if (masachdb.equals(masach)) {
                    return true;
                }

            } while (cs.moveToNext());
        }
        return false;
    }

    private void chonNgay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY");
                txtNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
}
