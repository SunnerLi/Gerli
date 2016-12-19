package com.gerli.gerli.chat;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.gerli.gerli.R;

import java.util.ArrayList;
import com.gerli.gerli.parser.MoneyHandler;
import com.gerli.handsomeboy.gerlisqlitedemo.GerliDatabaseManager;

public class ChatInputActivity extends AppCompatActivity {

    // Widget
    private ListView mListView;
    private Button mButtonSend;
    private EditText mEditTextMessage;
    private ChatMessageAdapter mAdapter;

    // SQLite
    GerliDatabaseManager manager;

    // Handler
    private MoneyHandler moneyHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_input);

        // Get the instance since it cannot define before onCreate
        manager = new GerliDatabaseManager(this);
        moneyHandler = new MoneyHandler(manager);

        // get view object
        setView();
    }

    /**
     * Get the instance and set the listener
     */
    private void setView(){
        mListView = (ListView) findViewById(R.id.listView);
        mButtonSend = (Button) findViewById(R.id.btn_send);
        mEditTextMessage = (EditText) findViewById(R.id.et_message);

        mAdapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        mListView.setAdapter(mAdapter);

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mEditTextMessage.getText().toString();
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                sendMessage(message);
                // mEditTextMessage.setText("");
            }
        });
    }

    /**
     * Set the message of this side
     *
     * @param message the message you want to assign
     */
    private void sendMessage(final String message) {
        ChatMessage chatMessage = new ChatMessage(message, true, false);
        mAdapter.add(chatMessage);

        // mimicOtherMessage("feed back - " + message);
        new Thread(){
            @Override
            public void run() {
                super.run();
                moneyHandler.work(message);
                responseHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    Handler responseHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String string = moneyHandler.getSentence();
            Log.i("--> Chat", string);
            mimicOtherMessage(string);
        }
    };

    /**
     * Set the opposite message
     *
     * @param message the opposite message you want to assign
     */
    private void mimicOtherMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, false, false);
        mAdapter.add(chatMessage);
    }
}
