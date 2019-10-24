package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Adapter.AdapterBill;
import com.example.myapplication.Adapter.AdapterBook;
import com.example.myapplication.Adapter.AdapterCategory;
import com.example.myapplication.Database.Database;
import com.example.myapplication.Modal.ModalBill;
import com.example.myapplication.Modal.ModalBook;

import java.util.ArrayList;

public class ActivityBill extends AppCompatActivity {
    final String DATABASE_NAME = "IronBookStoreSQLite.sqlite";

    SQLiteDatabase database;
    ArrayList<ModalBill> list;
    AdapterBill adapterBill;

    ListView lvBill;
    ImageButton btnBack,btnAdd;
    ImageView btnSearch, btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        addControls();
        addEnvents();
        readData();
    }

    private void addControls() {
        lvBill = findViewById(R.id.lv_bill);
        btnBack = findViewById(R.id.btn_back_bill);
        btnAdd = findViewById(R.id.btn_add_bill);

        list = new ArrayList<>();
        adapterBill = new AdapterBill(ActivityBill.this, list);
        lvBill.setAdapter(adapterBill);

    }
    private void addEnvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityBill.this,ActivityAddBill.class);
                startActivity(i);
                finish();
            }
        });
    }
    private void readData() {
        database = (SQLiteDatabase) Database.initDatabase(this, DATABASE_NAME);
        Cursor cs = database.rawQuery("SELECT * FROM tbHoaDon", null);

        list.clear();
        for (int i = 0; i < cs.getCount(); i++) {
            cs.moveToPosition(i);
            int id = cs.getInt(0);
            String ma = cs.getString(1);
            String ngay = cs.getString(2);
            String masach = cs.getString(3);
            int soluongmua = cs.getInt(4);
            float thanhtien =cs.getFloat(5);

            list.add(new ModalBill(ma,ngay,masach,id,soluongmua,thanhtien));
        }
        cs.close();
        adapterBill.notifyDataSetChanged();
    }
}
