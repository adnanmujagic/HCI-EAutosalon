package com.example.eautosalon;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eautosalon.data.UserVM;
import com.example.eautosalon.fragments.AddVehicleFragment;
import com.example.eautosalon.fragments.FavoriteVehiclesFragment;
import com.example.eautosalon.fragments.VehicleListFragment;
import com.example.eautosalon.fragments.VehiclesOnSell;
import com.example.eautosalon.helpers.FragmentUtilities;
import com.example.eautosalon.helpers.MyImageConverter;
import com.example.eautosalon.helpers.MySession;

public class MainDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(MainDrawerActivity.this, MainDrawerActivity.class));
                    return true;
                case R.id.navigation_favorites:
                    FragmentUtilities.addFragment(MainDrawerActivity.this, FavoriteVehiclesFragment.newInstance(), R.id.vehicle_listing_placeholder, true);
                    return true;
                case R.id.navigation_sell:
                    FragmentUtilities.addFragment(MainDrawerActivity.this, AddVehicleFragment.newInstance(), R.id.vehicle_listing_placeholder, true);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_drawer);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            BottomNavigationView navView = findViewById(R.id.nav_views);
            navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            getSupportActionBar().setTitle("Vehicle search");

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            loadDrawerHeaderInformation(navigationView);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorWhite));
            navigationView.setNavigationItemSelectedListener(this);
            FragmentUtilities.addFragment(this, new VehicleListFragment(), R.id.vehicle_listing_placeholder, false);
        }
        catch(Exception ex){
            Log.e("Error on load: ", ex.getMessage());
        }

    }

    private void loadDrawerHeaderInformation(NavigationView navigationView) {
        UserVM userVM = MySession.GetUser();

        View hView =  navigationView.getHeaderView(0);
        TextView nav_user_detail = (TextView)hView.findViewById(R.id.txtDrawerUsername);
        TextView nav_user_email = (TextView)hView.findViewById(R.id.txtDrawerEmail);
        ImageView imageView = (ImageView)hView.findViewById(R.id.imageView);


        nav_user_detail.setText(userVM.FirstName + " " + userVM.LastName);
        nav_user_email.setText(userVM.Email);
        if(userVM.Image != null){
            imageView.setImageBitmap(MyImageConverter.decodeFromBase64ToBitmap(userVM.Image));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_cars_on_sell) {
            FragmentUtilities.addFragment(this, new VehiclesOnSell(), R.id.vehicle_listing_placeholder, true);
        } else if (id == R.id.nav_logout) {
            MySession.SetUser(null);
            startActivity(new Intent(this, LoginActivity.class));
        } else if (id == R.id.menu_account_settings) {
            startActivity(new Intent(this, UserDetails.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
