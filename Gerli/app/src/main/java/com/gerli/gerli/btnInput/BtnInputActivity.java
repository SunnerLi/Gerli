package com.gerli.gerli.btnInput;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.GridLayoutAnimationController;
import android.widget.GridView;
import android.widget.TextView;

import com.gerli.gerli.R;

public class BtnInputActivity extends AppCompatActivity {
    double dollar = 0.0;

    TextView txtDollar;

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_input);

        Intent intent = getIntent();
        Bundle bundle =  intent.getExtras();
        dollar = bundle.getDouble("DOLLAR");

        txtDollar = (TextView)findViewById(R.id.btn_txtdollar);
        txtDollar.setText(String.valueOf(dollar));

        createGridType();
    }

    void createGridType(){

    }
}
