package com.devking.android.frame1.app.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.astuetz.PagerSlidingTabStrip;
import com.devking.android.frame1.app.R;
import com.devking.android.frame1.app.multiplemodel.viewpager.ModelPagerAdapter;
import com.devking.android.frame1.app.multiplemodel.viewpager.PagerModelManager;
import com.google.common.collect.Lists;
import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.DragTopLayout;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardViewNative;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    private Toolbar mToolbar;

    private DragTopLayout dragTopLayout;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private ModelPagerAdapter mModelPagerAdapter;



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
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //Create a Card
        Card card = new Card(this);

        //Create a CardHeader
        CardHeader header = new CardHeader(this);
        //Add Header to card
        card.addCardHeader(header);
        CardViewNative cardView = (CardViewNative) findViewById(R.id.carddemo);
        cardView.setCard(card);

//        //Create menu icon
//        ImageView icon = new ImageView(this); // Create an icon
//        Resources res = getResources();
//        Drawable imageDrawable = res.getDrawable(R.drawable.ic_action_dial);
//        icon.setImageDrawable(imageDrawable);
//        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
//                .setContentView(icon)
//                .setPosition(FloatingActionButton.POSITION_BOTTOM_CENTER)
//                .setBackgroundDrawable(R.drawable.button_action_blue)
//                .build();
//        actionButton.offsetLeftAndRight(0);

        //Init DragTopLayout
        DragTopLayout.from(this)
                .open()
                .listener(new DragTopLayout.SimplePanelListener() {
                    @Override
                    public void onSliding(float radio) {

                    }
                }).setup(dragTopLayout);

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


    private List<String> getTitles(){
        return Lists.newArrayList("快速拨号", "最近", "联系人");
    }

    public void onEvent(Boolean b){
        dragTopLayout.setTouchMode(b);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
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
