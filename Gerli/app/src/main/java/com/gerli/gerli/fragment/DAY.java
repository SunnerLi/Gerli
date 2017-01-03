package com.gerli.gerli.fragment;


import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.gerli.gerli.DatePickerFragment;
import com.gerli.gerli.R;
import com.gerli.gerli.ShareFunction;
import com.gerli.handsomeboy.gerliUnit.CalendarManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class DAY extends Fragment implements DatePickerFragment.PassOnDateSetListener{
    public DAY() {
        // Required empty public constructor
    }
    private ListView listView;
    private ArrayAdapter adapter;
    private View myView;
    private Button shareBtn;
    private ShareDialog shareDialog;
    private CallbackManager callbackManager;
    private Bitmap myBitmap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_day, container, false);
        TextView choseDateText = (TextView) myView.findViewById(R.id.choose_date_text);
        choseDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getFragmentManager(), "datePicker");
            }
        });
        choseDateText.setText(CalendarManager.getDay());
        //FB分享
        shareBtn = (Button)myView.findViewById(R.id.butShareDay);
        shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();

        shareBtn.setOnClickListener(shareOnclick);

        //init();//當按下一個日期
        return  myView;
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

    @Override
    public void passOnDateSet(int year, int month, int day) {
        TextView choseDateText = (TextView) myView.findViewById(R.id.choose_date_text);
        choseDateText.setText(CalendarManager.getDay(year, month + 1, day));
    }
}
