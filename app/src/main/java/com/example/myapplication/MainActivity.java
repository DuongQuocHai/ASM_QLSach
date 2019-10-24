package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton btnLogOut;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        user = i.getStringExtra("user");

        btnLogOut = findViewById(R.id.btn_backmain);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.icon_logout);
                builder.setTitle("Đăng xuất");
                builder.setMessage("Bạn muốn đăng xuất");
                builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
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


    }

    public void mainClick(View view) {
        switch (view.getId()) {
            case R.id.main_btnuser:
                Intent iuser = new Intent(MainActivity.this, ActivityUser.class);
                iuser.putExtra("user2", user);
                startActivity(iuser);
                break;
            case R.id.main_btnmap:
                Intent imap = new Intent(MainActivity.this, ActivityMap.class);
                startActivity(imap);
                break;
            case R.id.main_btncategory:
                Intent icategory = new Intent(MainActivity.this, ActivityCategory.class);
                startActivity(icategory);
                break;
            case R.id.main_btnbook:
                Intent ibook = new Intent(MainActivity.this, ActivityBook.class);
                startActivity(ibook);
                break;
            case R.id.main_btnexport:
                Intent iexport = new Intent(MainActivity.this, ActivityBill.class);
                startActivity(iexport);
                break;
            case R.id.main_btnstatiscal:
                Intent istatistical = new Intent(MainActivity.this, ActivityStatistical.class);
                startActivity(istatistical);
                break;
            case R.id.main_btnnews:
                Intent inews = new Intent(MainActivity.this, ActivityNews.class);
                startActivity(inews);
                overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                break;


        }
    }
}
