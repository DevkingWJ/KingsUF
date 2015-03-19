package com.devking.android.frame1.app.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.astuetz.PagerSlidingTabStrip;
import com.devking.android.frame1.app.R;
import com.devking.android.frame1.app.util.L;
import com.google.common.collect.Lists;
import github.chenupt.dragtoplayout.DragTopLayout;
import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;
import it.gmariotti.cardslib.library.view.CardViewNative;

import java.util.List;


public class MainActivity extends BaseActivity {
    private Toolbar mToolbar;

    private DragTopLayout dragTopLayout;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private ModelPagerAdapter mModelPagerAdapter;
    private ListView mDrawerListView;

    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dragTopLayout = (DragTopLayout) findViewById(R.id.drag_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_settings:
                        Toast.makeText(MainActivity.this, "action_settings", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initDrawerLayout();

        getCard().addCardHeader(getCardHeader());
        CardViewNative cardView = (CardViewNative) findViewById(R.id.carddemo);
        cardView.setCard(getCard());
        //Init DragTopLayout
        DragTopLayout.from(this)
                .open()
                .listener(new DragTopLayout.SimplePanelListener() {
                    @Override
                    public void onSliding(float radio) {
                        L.e("current radio is >>>>> " + radio);
                    }
                }).setup(dragTopLayout);
        dragTopLayout.setOverDrag(false);
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.pager);

        PagerModelManager manager = new PagerModelManager();
//        manager.addCommonFragment(TestListFragment.class,getTitles(),getTitles());
        manager.addFragment(new TestListFragment(),"Tab1");
        manager.addFragment(new TestListFragment(),"Tab2");
        manager.addFragment(new TestListFragment(),"Tab3");
        mModelPagerAdapter = new ModelPagerAdapter(getSupportFragmentManager(),manager);
        mViewPager.setAdapter(mModelPagerAdapter);
        mPagerSlidingTabStrip.setViewPager(mViewPager);

    }

    private void initDrawerLayout(){
        headerView = View.inflate(this,R.layout.view_image,null);
        mDrawerListView = (ListView) findViewById(R.id.list_drawer_view);
        String[] titles = getResources().getStringArray(R.array.list_title);
        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this,R.layout.drawer_item_list,R.id.drawer_item_text,titles);
        mDrawerListView.setAdapter(arrayAdapter);
        mDrawerListView.addHeaderView(headerView);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private List<String> getTitles(){
        return Lists.newArrayList("快速拨号", "最近", "联系人");
    }

    public void onEvent(Boolean b){
        dragTopLayout.setTouchMode(b);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
