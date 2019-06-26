package com.example.rec;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rec.menu_fragments.fragment_aboutUs;
import com.example.rec.menu_fragments.fragment_filters;
import com.example.rec.menu_fragments.fragment_home;
import com.example.rec.menu_fragments.fragment_message;
import com.example.rec.menu_fragments.fragment_myAds;
import com.example.rec.menu_fragments.fragment_notification;
import com.example.rec.menu_fragments.fragment_profile;
import com.example.rec.menu_fragments.fragment_settings;

public class homePg extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pg);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer= findViewById(R.id.drawer_layout);
        //these 2 lines actually call the navigation
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       /* ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
*/
        if(savedInstanceState==null)
        {
            //this will show map page(fragment) on start of the application
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new fragment_home()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_home()).commit();
                break;
            case R.id.nav_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_message()).commit();
                break;
            case R.id.nav_notification:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_notification()).commit();
                break;
            case R.id.nav_myAds:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_myAds()).commit();
                break;
            case R.id.nav_filters:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_filters()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_settings()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_profile()).commit();
                break;
            case R.id.nav_aboutus:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_aboutUs()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
