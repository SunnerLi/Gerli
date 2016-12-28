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
import android.widget.Button;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WEEK extends Fragment {

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
        setbarchart();
        shareBtn = (Button)myView.findViewById(R.id.butShareWeek);

        shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();

        shareBtn.setOnClickListener(shareOnclick);
        return  myView;
    }

    private void setbarchart() {
        BarChart barChart = (BarChart)myView.findViewById(R.id.chart);

        // HorizontalBarChart barChart= (HorizontalBarChart) findViewById(R.id.chart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));

        BarDataSet dataset = new BarDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");


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
