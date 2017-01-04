package com.gerli.gerli.btnInput;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
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
import com.gerli.gerli.calculator.NumBtnActivity;
import com.gerli.handsomeboy.gerliUnit.AccountType;
import com.gerli.handsomeboy.gerliUnit.CalendarManager;
import com.gerli.handsomeboy.gerliUnit.Info_type;
import com.gerli.handsomeboy.gerlisqlitedemo.GerliDatabaseManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BtnInputActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener,DesDialFragListener{

    String[] gridCol = {"gridImage","gridTxt"};

    TextView txtDollar;
    GridView gridView;
    TextView txt_des;
    TextView txt_InOut;
    Button btn_enter;

    String name;
    double dollar = 0.0;
    int type = -1;//-1沒有定義類別
    boolean isExpense = true;

    int myYear,myMonth,myDay;


    int[] image = {
            R.drawable.gerli_type_breakfast,R.drawable.gerli_type_lunch,R.drawable.gerli_type_dinner,
            R.drawable.gerli_type_supper,R.drawable.gerli_type_drink,R.drawable.gerli_type_snack,
            R.drawable.gerli_type_clothes,R.drawable.gerli_type_accessory,R.drawable.gerli_type_shoes,
            R.drawable.gerli_type_rent,R.drawable.gerli_type_dailysupply,R.drawable.gerli_type_payment,
            R.drawable.gerli_type_transport,R.drawable.gerli_type_fuel,R.drawable.gerli_type_car,
            R.drawable.gerli_type_book,R.drawable.gerli_type_stationery,R.drawable.gerli_type_art,
            R.drawable.gerli_type_entertainment,R.drawable.gerli_type_shopping,R.drawable.gerli_type_invest,
            R.drawable.gerli_type_gift,R.drawable.gerli_type_others
    };
    String[] imgText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_input);

        Intent intent = getIntent();
        Bundle bundle =  intent.getExtras();
        myYear = bundle.getInt(Info_type.getInfoTypeName(Info_type.YEAR));
        myMonth = bundle.getInt(Info_type.getInfoTypeName(Info_type.MONTH));
        myDay = bundle.getInt(Info_type.getInfoTypeName(Info_type.DAY));
        dollar = bundle.getDouble("DOLLAR");
        isExpense = bundle.getBoolean("INOUT");

        txtDollar = (TextView)findViewById(R.id.btn_txtdollar);
        txtDollar.setText(String.valueOf(dollar) + "NT");
        txt_des = (TextView)findViewById(R.id.txt_des);
        txt_InOut = (TextView)findViewById(R.id.txt_InOut);
        if(isExpense){
            txt_InOut.setText("支出");
        }
        else{
            isExpense = false;
            txt_InOut.setText("收入");
        }
        btn_enter = (Button)findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(listener);

        setGridString();
        createGridType();
    }

    void setGridString(){
        imgText = new String[image.length];
        for(int i = 0;i<image.length;i++){
            imgText[i] = AccountType.getString(i);
        }
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

                type = i;
                //Toast.makeText(BtnInputActivity.this,imgText[i],Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void desOnClick(View view) {
        FragmentManager fm = getSupportFragmentManager();
        BtnInput_describeFragment fragment = BtnInput_describeFragment.newInstance(txt_des.getText().toString());
        fragment.show(fm,"BtnInput_describeFragment");
    }

    public void inOutOnClick(View view) {
        PopupMenu popup = new PopupMenu(BtnInputActivity.this, view);
        popup.setOnMenuItemClickListener(BtnInputActivity.this);
        popup.inflate(R.menu.gerli_type_popmenu);
        popup.show();
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(txt_des.getText().toString().length()==0){
                Toast.makeText(BtnInputActivity.this,"請輸入描述",Toast.LENGTH_SHORT).show();
                return;
            }
            name = txt_des.getText().toString();
            if(type == -1){
                Toast.makeText(BtnInputActivity.this,"請選擇類別",Toast.LENGTH_SHORT).show();
                return;
            }
            if(!isExpense){
                dollar = (-dollar);
            }

            GerliDatabaseManager db = new GerliDatabaseManager(BtnInputActivity.this);
            boolean e = db.insertAccount(name,
                    (int)dollar,AccountType.getType(type), CalendarManager.getDay(myYear,myMonth,myDay),"");
            if(!e){
                Toast.makeText(BtnInputActivity.this,"資料庫新增資料失敗!",Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(BtnInputActivity.this, NumBtnActivity.class);
            setResult(RESULT_OK,intent);
            finish();
        }
    };

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

    @Override
    public void onFinishEditDescription(String inputText) {
        txt_des.setText(inputText);
    }
}
