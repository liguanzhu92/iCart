package com.example.guanzhuli.icart;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.guanzhuli.icart.Fragment.*;
import com.example.guanzhuli.icart.data.SPManipulation;
import com.facebook.login.LoginManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SPManipulation mSPManipulation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CartActivity.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        mSPManipulation = SPManipulation.getInstance(this);
        TextView name = (TextView) hView.findViewById(R.id.header_username);
        name.setText(mSPManipulation.getName());
        TextView email = (TextView) hView.findViewById(R.id.header_email);
        email.setText(mSPManipulation.getEmail());
        navigationView.setNavigationItemSelectedListener(this);

        if(findViewById(R.id.main_fragment_container) != null) {
            HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, homeFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (getFragmentManager().getBackStackEntryCount() > 0 ) {
            getFragmentManager().popBackStack();
        }
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (id) {
            case R.id.nav_home:
                HomeFragment homeFragment = new HomeFragment();
                transaction.replace(R.id.main_fragment_container, homeFragment).commit();
                break;
            case R.id.nav_category:
                CategoryFragment categoryFragment = new CategoryFragment();
                transaction.addToBackStack(CategoryFragment.class.getName());
                transaction.replace(R.id.main_fragment_container, categoryFragment).commit();
                break;
            case R.id.nav_profile:
                ProfileFragment profileFragment = new ProfileFragment();
                transaction.addToBackStack(CategoryFragment.class.getName());
                transaction.replace(R.id.main_fragment_container, profileFragment).commit();
                break;
            case R.id.nav_wallet:
                break;
            case R.id.nav_order:
                OrderHistoryFragment orderHistoryFragment = new OrderHistoryFragment();
                transaction.addToBackStack(OrderHistoryFragment.class.getName());
                transaction.replace(R.id.main_fragment_container, orderHistoryFragment).commit();
                break;
            case R.id.nav_favorite:
                break;
            case R.id.nav_help:
                HelpFragment helpFragment = new HelpFragment();
                transaction.addToBackStack(OrderHistoryFragment.class.getName());
                transaction.replace(R.id.main_fragment_container, helpFragment).commit();
                break;
            case R.id.nav_rate:
                break;
            case R.id.nav_logout:
                LoginManager.getInstance().logOut();
                mSPManipulation.clearSharedPreference(MainActivity.this);
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
