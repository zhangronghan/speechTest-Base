package com.example.administrator.speechtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity{
    private Button btnRegnizer;
    private Button btnSyn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSyn= (Button) findViewById(R.id.btn_Syn);
        btnRegnizer = (Button) findViewById(R.id.btn_Reg);

        btnRegnizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RecognizerActivity.class));
            }
        });

        btnSyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SynthetiseActivity.class));
            }
        });

    }
}



