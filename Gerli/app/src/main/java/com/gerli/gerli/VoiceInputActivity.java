package com.gerli.gerli;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gerli.gerli.parser.MoneyHandler;
import com.gerli.handsomeboy.gerliUnit.UnitPackage;
import com.gerli.handsomeboy.gerlisqlitedemo.GerliDatabaseManager;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceInputActivity extends AppCompatActivity {

    ImageButton recordBtn;//語音輸入btn
    String resultStr;
    MoneyHandler moneyHandler; ;
    private static final int RQS_VOICE_RECOGNITION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_input);
        final GerliDatabaseManager manager = new GerliDatabaseManager(this);
        moneyHandler = new MoneyHandler(manager);
        recordBtn = (ImageButton) findViewById(R.id.recordBtn);

        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //語音辨識
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                //設定辨識的語言為英文
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH.toString());
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
                resultStr = (String)result.get(0);

                Log.d("recognition",resultStr);
                Toast.makeText(this, resultStr, Toast.LENGTH_SHORT).show();
                String InStr = "(.*)income(.*)";
                int index;
                if(resultStr.matches(InStr)){
                    Log.d("recognition","match");
                    String tmp = "income";
                    index = resultStr.indexOf(tmp);
                    Log.d("recognition","index:"+index);
                    String tmp1 = resultStr.substring(0,index-1);
                    index = index + 7;
                    String tmp2 = resultStr.substring(index);
                    resultStr = tmp1+" others -"+tmp2;
                    //dinner income 100
                }
                else if(resultStr.contains(" ")){
                    index = resultStr.lastIndexOf(' ');
                    String tmp1 = resultStr.substring(0,index);
                    String tmp2 = resultStr.substring(index+1);
                    resultStr = tmp1+" +"+tmp2;
                }

                if(moneyHandler.work(resultStr)==false){
                    Toast.makeText(this, "Format wrong.Check your format.", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

}
