package com.gerli.gerli.fragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.gerli.gerli.R;
import com.gerli.gerli.TimedRemind.SetAlarmService;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{
    // 加入欄位變數宣告
    private SharedPreferences sharedPreferences;
    private CheckBoxPreference defaultNotice, defaultCloudCopy;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        addPreferencesFromResource(R.xml.settings);

        defaultNotice = (CheckBoxPreference) findPreference("notice");
        defaultCloudCopy = (CheckBoxPreference) findPreference("cloudCopy");
        defaultNotice.setOnPreferenceChangeListener(this);

        // 建立SharedPreferences物件
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean b = sharedPreferences.getBoolean("notice",false);
        defaultNotice.setDefaultValue(b);
        defaultNotice.setChecked(b);

        b = sharedPreferences.getBoolean("cloudCopy",false);
        defaultCloudCopy.setDefaultValue(b);
        defaultCloudCopy.setChecked(b);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        switch (preference.getKey()) {
            case "notice":
                boolean b = !sharedPreferences.getBoolean("notice", false);
                Intent intent = new Intent(mContext, SetAlarmService.class);
                if (b) {
                    mContext.startService(intent);
                } else {
                    mContext.stopService(intent);
                }
                defaultNotice.setChecked(b);
                sharedPreferences.edit()
                    .putBoolean("notice", b)
                    .apply();
                break;
            case "cloudCopy":
                boolean needCopy = !sharedPreferences.getBoolean("cloudCopy", false);
                defaultCloudCopy.setChecked(needCopy);
                sharedPreferences.edit()
                    .putBoolean("cloudCopy", needCopy)
                    .apply();
                break;
        }
        return false;
    }
}
