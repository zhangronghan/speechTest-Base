package com.example.administrator.speechtest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.widget.Toast;

import com.baidu.speech.VoiceRecognitionService;

/**
 * Created by Administrator on 2017/3/29.
 */

public class SpeechRecognizerTool implements RecognitionListener {
    private Context mContext;
    private SpeechRecognizer mSpeechRecognizer;
    private ResultsCallback mResultsCallback;

    public SpeechRecognizerTool(Context context){
        mContext=context;
    }


    public interface ResultsCallback{
        void onResults(String result);
    }

    public synchronized void createTools(){
        if(mSpeechRecognizer ==null){
            mSpeechRecognizer=SpeechRecognizer.createSpeechRecognizer(mContext,new ComponentName(mContext,VoiceRecognitionService.class));

            mSpeechRecognizer.setRecognitionListener(this);
        }

    }

    public synchronized void destroyTool() {
        mSpeechRecognizer.stopListening();
        mSpeechRecognizer.destroy();
        mSpeechRecognizer = null;
    }


    public void onStartASR(ResultsCallback callback){
        mResultsCallback=callback;
        Intent intent=new Intent();
        bindParams(intent);
        mSpeechRecognizer.startListening(intent);
    }

    private void bindParams(Intent intent){

    }

    public void stopASR() {
        mSpeechRecognizer.stopListening();
    }


    @Override
    public void onReadyForSpeech(Bundle params) {
        Toast.makeText(mContext, "请开始说话", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onResults(Bundle results) {
        if(mResultsCallback !=null){
            String content=results.get(SpeechRecognizer.RESULTS_RECOGNITION).toString().replace("]","").replace("[","");
            mResultsCallback.onResults(content);
        }


    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }

}
