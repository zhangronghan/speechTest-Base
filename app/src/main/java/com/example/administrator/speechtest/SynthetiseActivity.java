package com.example.administrator.speechtest;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SynthetiseActivity extends AppCompatActivity implements SpeechSynthesizerListener{
    private EditText edtSyn;
    private Button btnSyn;
    private SpeechSynthesizer mSpeechSynthesizer;
    private String mSampleDirPath;
    private static final String SAMPLE_DIR_NAME = "baiduTTS";
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license.txt";
    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";
    private static final String TAG = "SynthetiseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synthetise);

        initialEnv();
        initTTS();
        initViews();

    }

    private void initViews() {

        edtSyn= (EditText) findViewById(R.id.edt_syn);
        btnSyn= (Button) findViewById(R.id.btn_Syn);

        btnSyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=edtSyn.getText().toString();
                mSpeechSynthesizer.speak(content);
                Log.i(TAG, ">>>say: " + edtSyn.getText().toString());
                Toast.makeText(SynthetiseActivity.this, edtSyn.getText().toString(), Toast.LENGTH_SHORT).show();
               /* String text = edtSyn.getText().toString();
                //需要合成的文本text的长度不能超过1024个GBK字节。
                if (TextUtils.isEmpty(edtSyn.getText())) {
                    text = "欢迎使用百度语音合成SDK,百度语音为你提供支持。";
                    edtSyn.setText(text);
                }
                int result = mSpeechSynthesizer.speak(text);*/

            }
        });


    }


    @Override
    protected void onDestroy() {
        mSpeechSynthesizer.release();
        super.onDestroy();
    }

    private void initialEnv() {
        if (mSampleDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        }
        makeDir(mSampleDirPath);
        copyFromAssetsToSdcard(false, SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, TEXT_MODEL_NAME, mSampleDirPath + "/" + TEXT_MODEL_NAME);
        copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_TEXT_MODEL_NAME);
    }

    private void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void initTTS() {
        mSpeechSynthesizer=SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(this);
        mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        // 文本模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
                + TEXT_MODEL_NAME);
        // 声学模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
                + SPEECH_FEMALE_MODEL_NAME);
        // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了正式离线授权，不需要设置该参数，建议将该行代码删除（离线引擎）
        // 如果合成结果出现临时授权文件将要到期的提示，说明使用了临时授权文件，请删除临时授权即可。
    //  this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/" + LICENSE_FILE_NAME);
        mSpeechSynthesizer.setAppId("9454048");
        mSpeechSynthesizer.setApiKey("KTsvuGK1GQXofOD8G0497YRg","8187783195ca856854f6fce79c736d3a");
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);

        AuthInfo authInfo=mSpeechSynthesizer.auth(TtsMode.MIX);

        if (authInfo.isSuccess()) {
            mSpeechSynthesizer.initTts(TtsMode.MIX);
            mSpeechSynthesizer.speak("百度语音实例正在合成");
            Log.i(TAG, ">>>auth success.");
        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            Log.i(TAG, ">>>auth failed errorMsg: " + errorMsg);
        }

    }


    private void copyFromAssetsToSdcard(boolean isCover, String source, String dest) {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = getResources().getAssets().open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void onSynthesizeStart(String s) {
        Log.d(TAG,">>>onSynthesizeStart<<<  "+s);
    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
        Log.d(TAG,">>>onSynthesizeDataArrived<<<"+s);
    }

    @Override
    public void onSynthesizeFinish(String s) {
        Log.d(TAG,">>>onSynthesizeFinish<<<  "+s);
    }

    @Override
    public void onSpeechStart(String s) {
        Log.d(TAG,">>>onSpeechStart<<<  "+s);
    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {
        Log.d(TAG,">>>onSpeechProgressChanged<<<  "+s);
    }

    @Override
    public void onSpeechFinish(String s) {
        Log.d(TAG,">>>onSpeechFinish<<<  "+s);
    }

    @Override
    public void onError(String s, SpeechError speechError) {
        Log.d(TAG,">>>onError<<<  "+s+"  SpeechError "+speechError.description);
    }



}
