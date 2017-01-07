package com.gerli.gerli;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.facebook.FacebookSdk;
import com.gerli.gerli.TimedRemind.SetAlarmService;
import com.gerli.gerli.fragment.ChargeFragment;
import com.gerli.gerli.fragment.ChartAnalysisFragment;
import com.gerli.gerli.fragment.DropboxView;
import com.gerli.gerli.fragment.MonthPlanFragment;
import com.gerli.handsomeboy.gerlisqlitedemo.GerliDatabaseManager;

import java.io.File;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public final String TAG = "## NavigationActivity";
    public Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);


        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            ChargeFragment firstFragment = new ChargeFragment();
            mFragment = firstFragment;
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, firstFragment).commit();

        }

        // Check the system remind
        setPreference();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.navigation, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_charge) {
            getSupportActionBar().setTitle(R.string.app_name);
            // Create fragment and give it an argument specifying the article it should show
            ChargeFragment newFragment = new ChargeFragment();
            mFragment = newFragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);

            // Commit the transaction
            transaction.commit();
        } else if (id == R.id.nav_month_plan) {
            getSupportActionBar().setTitle(R.string.month_plan);
            MonthPlanFragment newFragment = new MonthPlanFragment();
            mFragment = newFragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);

            transaction.commit();
        } else if (id == R.id.nav_chart_analysis) {
            getSupportActionBar().setTitle(R.string.chart_analysis);
            ChartAnalysisFragment newFragment = new ChartAnalysisFragment();
            mFragment = newFragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);

            transaction.commit();
        } else if (id == R.id.nav_cloud) {
            final String MyDropbox_DIR = "/Backup/";//<<<===============dropbox directory
            File downloadDir = new File(this.getDatabasePath(GerliDatabaseManager.DatabaseName).getParent());//<<<========local directory
            final String APP_KEY = this.getString(R.string.dropbox_app_key); //<<<============replace app key
            final String APP_SECRET = this.getString(R.string.dropbox_app_secret); //<<<=============replace app secret
            final String fileExt = "gerli.db";//<<<===========副檔名 null or * 為全部

            getSupportActionBar().setTitle(R.string.cloud);
            DropboxView newFragment = DropboxView.newInstance(APP_KEY, APP_SECRET,
                    MyDropbox_DIR, downloadDir.getAbsolutePath(), fileExt);
            mFragment = newFragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);

            transaction.commit();
        } else if (id == R.id.nav_setting) {
            /*
            getSupportActionBar().setTitle(R.string.setting);
            SettingFragment newFragment = new SettingFragment();
            mFragment = newFragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);

            transaction.commit();
            */

            startActivity(new Intent(NavigationActivity.this, SettingActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Load the value of remind setting and start the system service
     */
    private void setPreference() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean b = sharedPrefs.getBoolean("notice", true);
        Intent intent = new Intent(NavigationActivity.this, SetAlarmService.class);
        if (b) {
            Log.i(TAG, "Start load setting");
            startService(intent);
        } else {
            stopService(intent);
        }
    }
}
