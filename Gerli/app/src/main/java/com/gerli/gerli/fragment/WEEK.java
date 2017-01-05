package com.gerli.gerli.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import com.gerli.handsomeboy.gerliUnit.UnitPackage;
import com.gerli.handsomeboy.gerlisqlitedemo.GerliDatabaseManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class WEEK extends Fragment {
    private ListView listView;
    private ArrayAdapter adapter;
    private Random random;//用於產生隨機數
    private View myView;
    private Button shareBtn;
    private ShareDialog shareDialog;
    private CallbackManager callbackManager;
    private Bitmap myBitmap;
    public WEEK() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       myView= inflater.inflate(R.layout.fragment_week, container, false);
        setpiechart();
        setbarchart();
        shareBtn = (Button)myView.findViewById(R.id.butShareWeek);

        shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();

        shareBtn.setOnClickListener(shareOnclick);
        return  myView;
    }
    public void setpiechart(){
        GerliDatabaseManager manager = new GerliDatabaseManager(getContext());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        UnitPackage.PieChartPackage pieChartPackage = manager.getPieChartByWeek(year,month,date,10);
        if(pieChartPackage == null){
            return;
        }
        ArrayList<String> dataList = pieChartPackage.typeList;
        float[] expenseArr = pieChartPackage.expenseArr;

        list_update();

        PieChart pieChart = (PieChart) myView.findViewById(R.id.chart);
        random = new Random();//隨機數


        ArrayList<Entry> entries = new ArrayList<>(); //數值填入
//        for (int i = 0; i < 6; i++) {
//            float profit = random.nextFloat() * 100;
//            //entries.add(BarEntry(float val,int positon);
//            entries.add(new Entry(profit, i));
//            // xVals.add((i + 1) + "月");
//        }
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
        pieChart.animateY(5000);

    }
    void list_update(){
        //資料庫
        GerliDatabaseManager manager = new GerliDatabaseManager(getContext());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int data = calendar.get(Calendar.DAY_OF_MONTH);
        UnitPackage.PieChartPackage pieChartPackage = manager.getPieChartByWeek(year,month,data,5);
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
        new Utility().setListViewHeightBasedOnChildren(listView);

    }
    public class Utility {
        public  void setListViewHeightBasedOnChildren(ListView listView) {
            //獲取ListView對應的Adapter
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }

            int totalHeight = 30;
            for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回數據項的數目
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);  //計算子項View 的寬高
                totalHeight += listItem.getMeasuredHeight();  //統計所有子項的總高度
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            //listView.getDividerHeight()獲取子項間分隔符占用的高度
            //params.height最後得到整個ListView完整顯示需要的高度
            listView.setLayoutParams(params);
        }
    }
    private void setbarchart() {
        GerliDatabaseManager manager = new GerliDatabaseManager(getContext());
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017,1,29);

        UnitPackage.BarChartPackage barChartPackage = manager.getBarChartByWeek();
        if(barChartPackage == null){
            return;
        }
        ArrayList<String> dataList = barChartPackage.dateList;
        float[] expenseArr = barChartPackage.expenseArr;


        BarChart barChart = (BarChart)myView.findViewById(R.id.chart2);

        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i=0;i<expenseArr.length;i++)
        {
            entries.add(new BarEntry(expenseArr[i], i));

        }


        BarDataSet dataset = new BarDataSet(entries, "總金額");

        ArrayList<String> labels = new ArrayList<String>();
        for(int i=0;i<dataList.size();i++)
        {
            labels.add(dataList.get(i));

        }

        BarData data = new BarData(labels, dataset);
        int[] color = {
                Color.rgb(241, 189, 157), Color.rgb(61, 146, 200), Color.rgb(53, 62, 112),
                Color.rgb(122, 198, 174)
        };
        dataset.setColors(color); //
        barChart.setData(data);
        barChart.animateY(5000);
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

}
