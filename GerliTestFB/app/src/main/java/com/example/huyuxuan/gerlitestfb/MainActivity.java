package com.example.huyuxuan.gerlitestfb;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;


public class MainActivity extends AppCompatActivity {

    ShareDialog shareDialog;
    Profile myProfile;
    String mId;
    TextView NameText;
    CallbackManager callbackManager;
    private AccessToken accessToken;
    private Bitmap myBitmap;
    Button shareBtn;
    private ProfilePictureView profileImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        NameText=(TextView) findViewById(R.id.name);
        profileImg = (ProfilePictureView) findViewById(R.id.profView);
        if(isLoggedIn()){
            profileImg.setProfileId(mId);
            NameText.setText(myProfile.getName());
        }
        shareBtn = (Button)findViewById(R.id.butShare);
          //宣告callback Manager
        shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();

        //發文
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {

                            Toast toast = Toast.makeText(MainActivity.this,"share success",Toast.LENGTH_LONG);
                            toast.show();
                        }

                    @Override
                    public void onCancel() {
                            Toast toast = Toast.makeText(MainActivity.this,"share cancel",Toast.LENGTH_LONG);
                            toast.show();
                        }

                    @Override
                    public void onError(FacebookException error) {
                            Toast toast = Toast.makeText(MainActivity.this,"share onError",Toast.LENGTH_LONG);
                            toast.show();
                        }

                });

                //螢幕截圖
                myBitmap=getScreenShot();
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
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //將全螢幕畫面轉換成Bitmap
    private Bitmap getScreenShot()
    {
        //藉由View來Cache全螢幕畫面後放入Bitmap
        View mView = getWindow().getDecorView();
        mView.setDrawingCacheEnabled(true);
        mView.buildDrawingCache();
        Bitmap mFullBitmap = mView.getDrawingCache();

        //取得系統狀態列高度
        Rect mRect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(mRect);
        int mStatusBarHeight = mRect.top;

        //取得手機螢幕長寬尺寸
        int mPhoneWidth = getWindowManager().getDefaultDisplay().getWidth();
        int mPhoneHeight = getWindowManager().getDefaultDisplay().getHeight();

        //將狀態列的部分移除並建立新的Bitmap
        Bitmap mBitmap = Bitmap.createBitmap(mFullBitmap, 0, mStatusBarHeight, mPhoneWidth, mPhoneHeight - mStatusBarHeight);
        //將Cache的畫面清除
        mView.destroyDrawingCache();

        return mBitmap;
    }

    public boolean isLoggedIn() {
        myProfile = Profile.getCurrentProfile();
        if(myProfile!=null){
            mId = myProfile.getId();
            return true;
        }
        else{
            return false;
        }
    }
}

