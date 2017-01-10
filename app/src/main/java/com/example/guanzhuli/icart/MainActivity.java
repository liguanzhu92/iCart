package com.example.guanzhuli.icart;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.MenuInflater;
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
import com.example.guanzhuli.icart.data.DBManipulation;
import com.example.guanzhuli.icart.fragments.*;
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        mSPManipulation = SPManipulation.getInstance(this);
        TextView textName = (TextView) hView.findViewById(R.id.header_username);
        String name = mSPManipulation.getName();
        textName.setText(name);
        TextView textEmail = (TextView) hView.findViewById(R.id.header_email);
        textEmail.setText(mSPManipulation.getEmail());

        navigationView.setNavigationItemSelectedListener(this);

        if(findViewById(R.id.main_fragment_container) != null) {
            HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, homeFragment).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String name = mSPManipulation.getName();
        String mobile = mSPManipulation.getMobile();
        int amount = DBManipulation.getInstance(this, name + mobile).getRecordNumber();
        TextView textAmount = (TextView) findViewById(R.id.cart_amount);
        textAmount.setText(Integer.toString(amount));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_view_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        Drawable searchIcon = searchViewItem.getIcon(); // change 0 with 1,2 ...
        searchIcon.mutate();
        searchIcon.setColorFilter(getResources().getColor(R.color.icons), PorterDuff.Mode.SRC_IN);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        MenuItem item = menu.findItem(R.id.action_cart);
        Drawable cartIcon = item.getIcon();
        cartIcon.setColorFilter(getResources().getColor(R.color.icons), PorterDuff.Mode.SRC_IN);
        item.setIntent(new Intent(MainActivity.this, CartActivity.class));
        return super.onCreateOptionsMenu(menu);
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
