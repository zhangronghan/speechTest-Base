package com.example.administrator.speechtest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecognizerActivity extends AppCompatActivity implements SpeechRecognizerTool.ResultsCallback{

    private TextView mContent;
    private Button btnTouch;
    private SpeechRecognizerTool mSpeechRecognizerTool=new SpeechRecognizerTool(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognizer);

        mContent= (TextView) findViewById(R.id.tv_content);
        btnTouch= (Button) findViewById(R.id.btn_Touch);

        btnTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action=event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizerTool.onStartASR(RecognizerActivity.this);
                        btnTouch.setBackgroundColor(Color.RED);
                        break;

                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizerTool.stopASR();
                        btnTouch.setBackgroundColor(Color.GRAY);
                        break;

                    default:
                        return false;
                }
                return true;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mSpeechRecognizerTool.createTools();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSpeechRecognizerTool.destroyTool();

    }


    public void onResults(String result) {
        final String finalResult=result;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mContent.setText(finalResult);
            }
        });


    }
}
