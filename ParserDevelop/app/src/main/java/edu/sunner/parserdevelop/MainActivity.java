package edu.sunner.parserdevelop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.sunner.parserdevelop.parser.Parser;
import edu.sunner.parserdevelop.parser.RemoteParser;

/**
 * Main activity
 * <p/>
 * Example sentence:
 * I paid 100 dollars for the card saving
 *
 * @author SunnerLi
 * @revise 10/29/2016
 */

public class MainActivity extends AppCompatActivity {
    public static EditText addr;

    /**
     * Default onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Set the click listener
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Get the widget
        Button send1 = (Button) findViewById(R.id.send1);
        final EditText edit1 = (EditText) findViewById(R.id.edit1);
        Button send2 = (Button) findViewById(R.id.send2);
        final EditText edit2 = (EditText) findViewById(R.id.edit2);
        Button send3 = (Button) findViewById(R.id.send3);
        final EditText edit3 = (EditText) findViewById(R.id.edit3);
        addr = (EditText) findViewById(R.id.addr);
        Button exit = (Button) findViewById(R.id.exit);

        // Set the listener
        send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        work(edit1.getText().toString());

                    }
                }.start();
            }
        });
        send2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        work(edit2.getText().toString());

                    }
                }.start();
            }
        });
        send3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        work(edit3.getText().toString());

                    }
                }.start();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        new Parser().parse("exit", Parser.control);
                    }
                }.start();
            }
        });
    }

    /**
     * 教學文件上寫的步驟
     * 最後會把批評或鼓勵的話印出來。
     *
     * @param string 想分析的話
     */
    private void work(String string) {
        Parser parser = new Parser();
        parser.parse(string, Parser.sentence);
        Log.i("--- 手機回應:", parser.getSentence());
    }
}
