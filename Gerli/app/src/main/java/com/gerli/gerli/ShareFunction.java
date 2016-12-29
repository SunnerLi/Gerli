package com.gerli.gerli;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;


/**
 * Created by huyuxuan on 2016/12/28.
 */

public class ShareFunction {
    //將全螢幕畫面轉換成Bitmap
    static public Bitmap getScreenShot(FragmentActivity ac)
    {
        //藉由View來Cache全螢幕畫面後放入Bitmap
        View mView = ac.getWindow().getDecorView();
        mView.setDrawingCacheEnabled(true);
        mView.buildDrawingCache();
        Bitmap mFullBitmap = mView.getDrawingCache();

        //取得系統狀態列高度
        Rect mRect = new Rect();
        ac.getWindow().getDecorView().getWindowVisibleDisplayFrame(mRect);
        int mStatusBarHeight = mRect.top;

        //取得手機螢幕長寬尺寸
        int mPhoneWidth = ac.getWindowManager().getDefaultDisplay().getWidth();
        int mPhoneHeight = ac.getWindowManager().getDefaultDisplay().getHeight();

        //將狀態列的部分移除並建立新的Bitmap
        Bitmap mBitmap = Bitmap.createBitmap(mFullBitmap, 0, mStatusBarHeight, mPhoneWidth, mPhoneHeight - mStatusBarHeight);
        //將Cache的畫面清除
        mView.destroyDrawingCache();

        return mBitmap;
    }


}
