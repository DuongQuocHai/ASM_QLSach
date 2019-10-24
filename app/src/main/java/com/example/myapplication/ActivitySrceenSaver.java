package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class ActivitySrceenSaver extends AppCompatActivity {
    LinearLayout manHinhCho;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srceen_saver);

        manHinhCho = findViewById(R.id.manhinhcho);
        progressBar = findViewById(R.id.progressBar);
        manHinhCho.setBackgroundResource(R.drawable.bg);


        CountDownTimer countDownTimer = new CountDownTimer(3000, 10) {
            @Override
            public void onTick(long l) {
                int current = progressBar.getProgress();
                if (current >= progressBar.getMax()) {
                    startActivity(new
                            Intent(ActivitySrceenSaver.this, ActivityLogin.class));
                }
                progressBar.setProgress(current + 3);
            }
            @Override
            public void onFinish() {
                finish();
            }
        };
        countDownTimer.start();
    }
}
