package com.sng.assignmentfinalapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.sng.assignmentfinalapp.interfaces.FilterInterface;
import com.sng.assignmentfinalapp.interfaces.SortingInterface;
import com.sng.assignmentfinalapp.view.FragmentFeedAllPastday;
import com.sng.assignmentfinalapp.view.FragmentFeedFourPointFiveDay;
import com.sng.assignmentfinalapp.view.FragmentFeedOneDay;
import com.sng.assignmentfinalapp.view.FragmentFeedTwoPointFiveDay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    Class fragmentClass;
    SortingInterface sortingInterface;
    ImageView iv_filter;
    LinearLayout bottom_filter;
    FilterInterface filterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Set a Toolbar to replace the ActionBar.
        bottom_filter = (LinearLayout) findViewById(R.id.bottom_filter);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        iv_filter = (ImageView) findViewById(R.id.iv_filter);
        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideUp(bottom_filter);
            }
        });
        setUpBottomFilter();
        setSupportActionBar(toolbar);

        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView abTitle = (TextView) findViewById(titleId);
        //abTitle.setTextColor(getResources().getColor(R.color.white));

        setupDrawerContent(nvDrawer);
        //selectDrawerItem(Menu)
        if(savedInstanceState==null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, new FragmentFeedOneDay()).commit();
            nvDrawer.setCheckedItem(R.id.nav_one_point_zero_fragment);

        }
        nvDrawer.post(new Runnable() {
            @Override
            public void run() {
                setTitle(nvDrawer.getCheckedItem().getTitle());
            }
        });

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_all_in_one_fragment:
                fragmentClass = FragmentFeedAllPastday.class;
                break;
            case R.id.nav_four_point_five_fragment:
                fragmentClass = FragmentFeedFourPointFiveDay.class;
                break;
            case R.id.nav_two_point_five_fragment:
                fragmentClass = FragmentFeedTwoPointFiveDay.class;
                break;
            case R.id.nav_one_point_zero_fragment:
                fragmentClass = FragmentFeedOneDay.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_sort_by_title:
                sortingInterface.sortNow(1);
                return true;
            case R.id.action_sort_by_time:
                sortingInterface.sortNow(2);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void sortEntries(SortingInterface sortingInterface) {
        this.sortingInterface = sortingInterface;
    }

    private void setUpBottomFilter() {
        ImageView iv_close = (ImageView) findViewById(R.id.iv_close);
        TextView tv_lt_2 = (TextView) findViewById(R.id.tv_lt_2);
        TextView tv_2_to_4 = (TextView) findViewById(R.id.tv_2_to_4);
        TextView tv_4_to_6 = (TextView) findViewById(R.id.tv_4_to_6);
        TextView tv_gt_6 = (TextView) findViewById(R.id.tv_gt_6);
        TextView tv_remove_filter = (TextView) findViewById(R.id.tv_remove_filter);
        iv_close.setOnClickListener(this);
        tv_lt_2.setOnClickListener(this);
        tv_2_to_4.setOnClickListener(this);
        tv_4_to_6.setOnClickListener(this);
        tv_gt_6.setOnClickListener(this);
        tv_remove_filter.setOnClickListener(this);
    }

    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void slideDown(View view) {
        view.setVisibility(View.INVISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(false);
        view.startAnimation(animate);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                slideDown(bottom_filter);
                break;
            case R.id.tv_lt_2:
                filterInterface.filterByMeasurment(1);
                slideDown(bottom_filter);
                break;
            case R.id.tv_2_to_4:
                filterInterface.filterByMeasurment(2);
                slideDown(bottom_filter);
                break;
            case R.id.tv_4_to_6:
                filterInterface.filterByMeasurment(3);
                slideDown(bottom_filter);
                break;
            case R.id.tv_gt_6:
                filterInterface.filterByMeasurment(4);
                slideDown(bottom_filter);
                break;
            case R.id.tv_remove_filter:
                filterInterface.filterByMeasurment(5);
                slideDown(bottom_filter);
                break;
        }
    }

    public void filterMeasurment(FilterInterface filterInterface) {
        this.filterInterface = filterInterface;
    }
}