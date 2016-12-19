package com.gerli.gerli;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gerli.gerli.parser.MoneyHandler;
import com.gerli.handsomeboy.gerliUnit.UnitPackage;
import com.gerli.handsomeboy.gerlisqlitedemo.GerliDatabaseManager;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceInputActivity extends AppCompatActivity {

    Button recordBtn;//語音輸入btn
    String resultStr;
    MoneyHandler moneyHandler; ;
    private static final int RQS_VOICE_RECOGNITION = 1;
    Button btCheck;//檢查今日收入支出
    Button backBtn;//回上一頁

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_input);
        final GerliDatabaseManager manager = new GerliDatabaseManager(this);
        moneyHandler = new MoneyHandler(manager);
        btCheck = (Button)findViewById(R.id.btnCheck);
        recordBtn = (Button) findViewById(R.id.recordBtn);
        backBtn = (Button)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(back);
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

        //檢查當入總支出收入，debug用可刪掉
        btCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnitPackage.TotalPackage tmp = manager.getTodayTotal();
                Log.d("check:","expense:"+tmp.Expense+" income:"+tmp.Income);
                Toast toast = Toast.makeText(VoiceInputActivity.this,"expense:"+Integer.toString(tmp.Expense)+" income:"+Integer.toString(tmp.Income),Toast.LENGTH_LONG);
                toast.show();
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

    private ImageButton.OnClickListener back = new ImageButton.OnClickListener(){
        public void onClick(View v){
            //回到上一頁畫面

        }
    };
}
