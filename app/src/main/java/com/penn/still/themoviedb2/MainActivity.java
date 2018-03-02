package com.penn.still.themoviedb2;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.penn.still.themoviedb2.fragment.FavoriteFragment;
import com.penn.still.themoviedb2.fragment.NowPlayingFragment;
import com.penn.still.themoviedb2.fragment.SearchFragment;
import com.penn.still.themoviedb2.fragment.UpcomingFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    public static boolean isListView = true;
    private String title = "THE MOVIE DB";

    public static final String EXTRAS_TITLE = "extras_title";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        //toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState==null){
            Fragment currFragment = new NowPlayingFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main,currFragment)
                    .commit();
        }

        if(savedInstanceState!=null){
            String title = savedInstanceState.get(EXTRAS_TITLE).toString();
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        toggle = new ActionBarDrawerToggle(this,
                drawer,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onPause(){
        super.onPause();
        drawer.removeDrawerListener(toggle);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
            //return true;
        }else if(id==R.id.action_list){
            isListView = true;
        }else if(id==R.id.action_cardView){
            isListView = false;
        }

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_main);
        Log.d("ACTIVE_FRAGMENT", f.toString());
        if (f instanceof NowPlayingFragment ||
                f instanceof UpcomingFragment ||
                f instanceof FavoriteFragment)
            ((FragmentInterface) f).refresh();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        Fragment fragment = null;
        String dotitle = "";

        if(id == R.id.ic_menu_now_playing) {
            dotitle = title+"-Now Playing";
            fragment = new NowPlayingFragment();
        }else if(id == R.id.ic_menu_upcoming) {
            dotitle = title+"-Upcoming";
            fragment = new UpcomingFragment();
        }else if(id == R.id.ic_menu_search){
            dotitle = title+"-Search";
            fragment = new SearchFragment();

        }else if(id == R.id.ic_menu_favorite){
            dotitle = title+"-Favorite";
            fragment = new FavoriteFragment();
        }

        if(fragment!=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main,fragment)
                    .commit();
        }

        getSupportActionBar().setTitle(dotitle);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(EXTRAS_TITLE,getSupportActionBar().getTitle().toString());
    }
}
