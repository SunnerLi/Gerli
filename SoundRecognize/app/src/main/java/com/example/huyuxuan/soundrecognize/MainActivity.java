package com.example.huyuxuan.soundrecognize;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button recogBtn;
    TextView resultView;
    private String resultText;  //最後語音轉成的文字
    private static final int RQS_VOICE_RECOGNITION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recogBtn = (Button)findViewById(R.id.SpeechBtn);
        resultView = (TextView)findViewById(R.id.textResultView);

        recogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //語音辨識
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Start Speech");
                startActivityForResult(intent, RQS_VOICE_RECOGNITION);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(requestCode == RQS_VOICE_RECOGNITION){
            if(resultCode == RESULT_OK){

                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                resultText = (String)result.get(0);
                resultView.setText(resultText);
            }
        }
    }

    public String getResult(){
        return resultText;
    }
}
