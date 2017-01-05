package com.gerli.gerli.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.gerli.gerli.R;
import com.gerli.gerli.ShareFunction;
import com.gerli.handsomeboy.gerliUnit.CalendarManager;
import com.gerli.handsomeboy.gerliUnit.UnitPackage;
import com.gerli.handsomeboy.gerlisqlitedemo.GerliDatabaseManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class DAY extends Fragment  {

    private static int year1;
    private static int month1;
    private static int day1;

    public DAY() {
        // Required empty public constructor
    }

    private Random random;//用於產生隨機數
    private ListView listView;
    private ArrayAdapter adapter;
    private static  View myView;
    private Button shareBtn;
    private ShareDialog shareDialog;
    private CallbackManager callbackManager;
    private Bitmap myBitmap;
    private GerliDatabaseManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_day, container, false);
        TextView choseDateText = (TextView) myView.findViewById(R.id.choose_date_text);
        choseDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialogFragment dlg = DatePickerDialogFragment.newInstance();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                dlg.show(fm, "datepickerdialog");
                Toast.makeText(getActivity(), "onDayClick: " +year1 +month1 +day1 , Toast.LENGTH_SHORT).show();
            }
        });
        choseDateText.setText(CalendarManager.getDay());

        manager = new GerliDatabaseManager(getContext());

        //FB分享
        shareBtn = (Button)myView.findViewById(R.id.butShareDay);
        shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();

        shareBtn.setOnClickListener(shareOnclick);

        setpiechart();
        sum();
        return  myView;
    }
    public  void sum()
    {
        TextView icome = (TextView) myView.findViewById(R.id.income);
        TextView expense = (TextView) myView.findViewById(R.id.expense);
        TextView total = (TextView) myView.findViewById(R.id.total);
        GerliDatabaseManager manager = new GerliDatabaseManager(getContext());
        UnitPackage.TotalPackage totalPackage = manager.getTodayTotal();
        if(totalPackage == null)
        {
            return;
        }
        int income1 = totalPackage.Income;
        int expense1 = totalPackage.Expense;

        icome.setText(String.valueOf(income1));
        expense.setText(String.valueOf(expense1));

    }
    public void setpiechart(){
        //GerliDatabaseManager manager = new GerliDatabaseManager(getContext());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        UnitPackage.PieChartPackage pieChartPackage = manager.getPieChartByDay(year,month,date,10);
        if(pieChartPackage == null){
            return;
        }
        ArrayList<String> dataList = pieChartPackage.typeList;
        float[] expenseArr = pieChartPackage.expenseArr;

        list_update();
        PieChart pieChart = (PieChart) myView.findViewById(R.id.chart);
        random = new Random();//隨機數


        ArrayList<Entry> entries = new ArrayList<>(); //數值填入
        for(int i=0;i<expenseArr.length;i++)
        {
            entries.add(new Entry(expenseArr[i], i));

        }

        PieDataSet dataset = new PieDataSet(entries, "各類別分析");//類別填入

        ArrayList<String> labels = new ArrayList<String>();
        for (int i = 0; i <dataList.size(); i++) {
            labels.add(dataList.get(i));
        }

        PieData data = new PieData(labels, dataset);
        int[] color = {
                Color.rgb(241, 189, 157), Color.rgb(61, 146, 200), Color.rgb(53, 62, 112),
                Color.rgb(122, 198, 174)
        };
        dataset.setColors(color);
        dataset.setSliceSpace(5f);
        dataset.setValueTextColor(Color.WHITE);
        dataset.setValueTextSize(18f);//

        pieChart.setDescription("");
        pieChart.setData(data);
        pieChart.animateY(2500);

    }
    void list_update(){
        //GerliDatabaseManager manager = new GerliDatabaseManager(getContext());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        UnitPackage.PieChartPackage pieChartPackage = manager.getPieChartByDay(year,month,date,10);
        if(pieChartPackage == null){
            return;
        }
        ArrayList<String> dataList = pieChartPackage.typeList;
        float[] expenseArr = pieChartPackage.expenseArr;

        //listview
        listView = (ListView) myView.findViewById(R.id.listView1);

        adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1);
        if(pieChartPackage != null){
            // 清單陣列
            for(int i=0;i<dataList.size();i++){
                adapter.add((i+1)+". "+dataList.get(i)+"  "+expenseArr[i]);
            }
        }
        listView.setAdapter(adapter);

    }
    public View.OnClickListener shareOnclick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {

                    Toast.makeText(getActivity(),"share success",Toast.LENGTH_LONG).show();

                }

                @Override
                public void onCancel() {
                    Toast toast = Toast.makeText(getActivity(),"share cancel",Toast.LENGTH_LONG);
                    toast.show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast toast = Toast.makeText(getActivity(),"share onError",Toast.LENGTH_LONG);
                    toast.show();
                }

            });

            //螢幕截圖
            myBitmap= ShareFunction.getScreenShot(getActivity());
            Log.d("share","getScreen shot");

            //建立分享內容
            if (ShareDialog.canShow(SharePhotoContent.class)) {
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(myBitmap)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();

                shareDialog.show(content);
            }

        }

    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public static void getDate(int year, int monthOfYear, int dayOfMonth) {
        TextView output = (TextView) myView.findViewById(R.id.choose_date_text);
        output.setText(Integer.toString(year) + "/ " +
                Integer.toString(monthOfYear + 1) + "/ " +
                Integer.toString(dayOfMonth));
        year1 = Integer.valueOf(year);
        month1 =  Integer.valueOf(monthOfYear + 1);
        day1 = Integer.valueOf(dayOfMonth);

    }
    @Override
    public void onResume(){
        super.onResume();
       setpiechart();
    }
}
