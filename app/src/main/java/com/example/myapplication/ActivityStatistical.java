package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.Database.Database;

public class ActivityStatistical extends AppCompatActivity {
    final String DATABASE_NAME = "IronBookStoreSQLite.sqlite";
    TextView txtDoanhThu;
    SQLiteDatabase database;
    float dt = 0;

    ImageButton btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);

        txtDoanhThu = findViewById(R.id.txt_doanhthu);
        database = (SQLiteDatabase) Database.initDatabase(this, DATABASE_NAME);
        Cursor cs = database.rawQuery("SELECT * FROM tbHoaDon", null);
        for (int i = 0; i < cs.getCount(); i++) {
            cs.moveToPosition(i);
            float  doanhthu = cs.getFloat(5);
            dt += doanhthu;
        }
        cs.close();
        txtDoanhThu.setText(dt+" VND");

        btnback = findViewById(R.id.btn_back_doanhthu);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
