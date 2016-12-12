package com.gerli.gerli.calculator;

import android.content.Intent;
import android.icu.math.BigDecimal;
import android.icu.math.MathContext;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gerli.gerli.R;
import com.gerli.gerli.btnInput.BtnInputActivity;

public class NumBtnActivity extends AppCompatActivity {
    final double eps = 1e-10;

    TextView txtDollar;
    boolean add = false,sub = false,mul = false,div = false;
    boolean dot = false;
    boolean cal = false;
    boolean res = false;
    int dotDigit = 0;
    double currentNT = 0;
    double tmpNT = 0;

    Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    Button btnAdd,btnSub,btnMul,btnDiv;
    Button btnAC,btnOK,btnDot,btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_btn);

        display();
        setListener();
    }

    void display(){
        //TextView
        txtDollar = (TextView)findViewById(R.id.txtDollar);
        //Button
        btn0 = (Button)findViewById(R.id.btn0);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);
        btn6 = (Button)findViewById(R.id.btn6);
        btn7 = (Button)findViewById(R.id.btn7);
        btn8 = (Button)findViewById(R.id.btn8);
        btn9 = (Button)findViewById(R.id.btn9);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnSub = (Button)findViewById(R.id.btnSub);
        btnMul = (Button)findViewById(R.id.btnMul);
        btnDiv = (Button)findViewById(R.id.btnDiv);
        btnAC = (Button)findViewById(R.id.btnAC);
        btnOK = (Button)findViewById(R.id.btnOK);
        btnDot = (Button)findViewById(R.id.btnDot);
        btnBack = (Button)findViewById(R.id.btnBack);
    }

    void setListener(){
        btn0.setOnClickListener(listener);
        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);
        btn6.setOnClickListener(listener);
        btn7.setOnClickListener(listener);
        btn8.setOnClickListener(listener);
        btn9.setOnClickListener(listener);
        btnAdd.setOnClickListener(listener);
        btnSub.setOnClickListener(listener);
        btnMul.setOnClickListener(listener);
        btnDiv.setOnClickListener(listener);
        btnAC.setOnClickListener(listener);
        btnOK.setOnClickListener(listener);
        btnDot.setOnClickListener(listener);
        btnBack.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn0:
                    addDigit(0);
                    break;
                case R.id.btn1:
                    addDigit(1);
                    break;
                case R.id.btn2:
                    addDigit(2);
                    break;
                case R.id.btn3:
                    addDigit(3);
                    break;
                case R.id.btn4:
                    addDigit(4);
                    break;
                case R.id.btn5:
                    addDigit(5);
                    break;
                case R.id.btn6:
                    addDigit(6);
                    break;
                case R.id.btn7:
                    addDigit(7);
                    break;
                case R.id.btn8:
                    addDigit(8);
                    break;
                case R.id.btn9:
                    addDigit(9);
                    break;
                case R.id.btnAdd:
                    calculate(1);
                    break;
                case R.id.btnSub:
                    calculate(2);
                    break;
                case R.id.btnMul:
                    calculate(3);
                    break;
                case R.id.btnDiv:
                    calculate(4);
                    break;
                case R.id.btnAC:
                    clearDigit();
                    break;
                case R.id.btnDot:
                    addDot();
                    break;
                case R.id.btnBack:
                    deleteDigit();
                    break;
                case R.id.btnOK:
                    enterResult();
                    break;
            }
        }
        void addDigit(int digit){
            if(res && !(add || sub || mul || div)){
                clearDigit();
            }
            if(!dot){
                if(currentNT == 0){
                    currentNT += digit;
                }
                else{
                    currentNT *= 10;
                    currentNT += digit;
                }
                int decimal = (int)currentNT;
                txtDollar.setText(Integer.toString(decimal));
            }
            else{
                if(dotDigit > 1){
                    return;
                }
                dotDigit++;
                for(int i = 0; i < dotDigit; i++){
                    currentNT *= 10;
                }
                currentNT += digit;
                java.math.BigDecimal NTdecimal = new java.math.BigDecimal(currentNT);
                java.math.BigDecimal rightShift = new java.math.BigDecimal(10);
                for(int i = 0; i < dotDigit; i++){
                    NTdecimal = NTdecimal.divide(rightShift,2, BigDecimal.ROUND_HALF_UP);
                }
                currentNT = NTdecimal.doubleValue();
                txtDollar.setText(Double.toString(currentNT));
            }
        }

        void clearDigit(){
            add = sub = mul = div = false;
            dot = false;
            cal = false;
            res = false;
            dotDigit = 0;
            txtDollar.setText("0");
            currentNT = Double.parseDouble(txtDollar.getText().toString());
            tmpNT = currentNT;
        }

        void addDot(){
            if(!dot){
                dot = true;
                txtDollar.setText(txtDollar.getText().toString() + ".");
            }
        }

        void deleteDigit(){
            if(!dot){
                currentNT /= 10;
                currentNT = Math.floor(currentNT);
                int decimal = (int)currentNT;
                txtDollar.setText(Integer.toString(decimal));
            }
            else{
                if(dotDigit == 0){
                    dot = false;
                    int decimal = (int)currentNT;
                    txtDollar.setText(Integer.toString(decimal));
                    return;
                }
                dotDigit--;
                for(int i = 0; i < dotDigit; i++){
                    currentNT *= 10;

                }
                currentNT = Math.floor(currentNT);
                java.math.BigDecimal NTdecimal = new java.math.BigDecimal(currentNT);
                java.math.BigDecimal rightShift = new java.math.BigDecimal(10);
                for(int i = 0; i < dotDigit; i++){
                    NTdecimal = NTdecimal.divide(rightShift,2, BigDecimal.ROUND_HALF_UP);
                }
                if(dotDigit == 0){
                    int decimal = (int)currentNT;
                    txtDollar.setText(Integer.toString(decimal) + ".");
                }
                else{
                    currentNT = NTdecimal.doubleValue();
                    txtDollar.setText(Double.toString(currentNT));
                }
            }
        }

        void calculate(int mode){
            dotDigit = 0;
            dot = false;
            cal = true;
            if(add || sub || mul || div){
                java.math.BigDecimal NTdecimal = new java.math.BigDecimal(currentNT);
                java.math.BigDecimal tmpDecimal = new java.math.BigDecimal(tmpNT);
                if(add){
                    tmpDecimal = tmpDecimal.add(NTdecimal);
                }
                else if(sub){
                    tmpDecimal = tmpDecimal.subtract(NTdecimal);
                }
                else if(mul){
                    tmpDecimal = tmpDecimal.multiply(NTdecimal);
                    tmpDecimal = tmpDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
                }
                else{
                    tmpDecimal = tmpDecimal.divide(NTdecimal,2, BigDecimal.ROUND_HALF_UP);
                }

                tmpNT = tmpDecimal.doubleValue();
                currentNT = 0;

                if(isFloat(tmpNT)){
                    txtDollar.setText(Double.toString(tmpNT));
                }
                else{

                    txtDollar.setText(Integer.toString((int)tmpNT));
                }

                add = sub = mul = div = false;
            }
            else{
                tmpNT = currentNT;
                currentNT = 0;
            }

            switch (mode){
                case 1:
                    add = true;
                    break;
                case 2:
                    sub = true;
                    break;
                case 3:
                    mul = true;
                    break;
                case 4:
                    div = true;
                    break;
                default:
                    break;
            }
        }

        void enterResult(){
            if(cal){
                calculate(5);
                currentNT = tmpNT;
                cal = false;
                res = true;
            }
            else{
                Intent intent = new Intent(NumBtnActivity.this, BtnInputActivity.class);

                Bundle bundle = new Bundle();
                bundle.putDouble("DOLLAR",currentNT);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        }

        boolean isFloat(double num){
            return num - Math.floor(num) >= eps;
        }
    };
}
