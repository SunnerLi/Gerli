package com.gerli.gerli.btnInput;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gerli.gerli.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BtnInputActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    double dollar = 0.0;
    String[] gridCol = {"gridImage","gridTxt"};

    TextView txtDollar;
    GridView gridView;
    TextView txt_des;
    TextView txt_InOut;
    Button btn_enter;


    String name;
    boolean isExpense = true;


    int[] image = {
            R.drawable.gerli_ui_gerli,R.drawable.gerli_ui_charge,R.drawable.gerli_ui_chart_analysis,
            R.drawable.gerli_ui_setting,R.drawable.gerli_ui_month_plan,R.drawable.gerli_ui_year_plan
    };
    String[] imgText = {
            "gerli","記帳","分析","設定","月計畫","年計畫"
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_input);

        Intent intent = getIntent();
        Bundle bundle =  intent.getExtras();
        dollar = bundle.getDouble("DOLLAR");

        txtDollar = (TextView)findViewById(R.id.btn_txtdollar);
        txtDollar.setText(String.valueOf(dollar));
        txt_des = (TextView)findViewById(R.id.txt_des);
        txt_InOut = (TextView)findViewById(R.id.txt_InOut);
        btn_enter = (Button)findViewById(R.id.btn_enter);

        createGridType();
    }

    void createGridType(){
        List<Map<String,Object>> items = new ArrayList<>();
        for(int i = 0; i < image.length;i++){
            Map<String,Object> item = new HashMap<>();
            item.put(gridCol[0],image[i]);
            item.put(gridCol[1],imgText[i]);
            items.add(item);
        }

        final SimpleAdapter adapter = new SimpleAdapter(this,items,R.layout.grid_type_button
                ,gridCol,new int[]{R.id.gridImage,R.id.gridText});

        gridView = (GridView)findViewById(R.id.gridTypeList);
        gridView.setNumColumns(5);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                View tag=(View)adapterView.getTag();
                if (tag != null)
                {
                    if (tag.getId()==l) return;
                    tag.setBackgroundColor(Color.TRANSPARENT);
                }
                view.setBackgroundColor(Color.GRAY);
                adapterView.setTag(view);

                Toast.makeText(BtnInputActivity.this,imgText[i],Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void desOnClick(View view) {
    }

    public void inOutOnClick(View view) {
        PopupMenu popup = new PopupMenu(BtnInputActivity.this, view);
        popup.setOnMenuItemClickListener(BtnInputActivity.this);
        popup.inflate(R.menu.gerli_type_popmenu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.popmenu_expense:
                isExpense = true;
                txt_InOut.setText("支出");
                break;
            case R.id.popmenu_income:
                isExpense = false;
                txt_InOut.setText("收入");
                break; }
        return false;
    }
}
