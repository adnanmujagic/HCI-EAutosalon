package com.example.eautosalon;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eautosalon.fragments.AddVehicleFragment;
import com.example.eautosalon.fragments.VehicleListFragment;
import com.example.eautosalon.helpers.FragmentUtilities;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    RelativeLayout card;
    Button btn;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(MainActivity.this, VehicleOnSell.class));
                    return true;
                case R.id.navigation_favorites:
                    Toast.makeText(getApplicationContext(), "Favorites", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, UserDetails.class));
                    return true;
                case R.id.navigation_sell:
                    Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
                    openAddNewVehicleFragment();
                    //startActivity(new Intent(MainActivity.this, RateVehicle.class));

                    return true;
            }
            return false;
        }
    };

    private void openAddNewVehicleFragment() {
        FragmentUtilities.addFragment(MainActivity.this, AddVehicleFragment.newInstance(), R.id.vehicle_listing_placeholder, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        actionBar.setTitle("Car search");

        FragmentUtilities.addFragment(this, new VehicleListFragment(), R.id.vehicle_listing_placeholder, false);

//        card = (RelativeLayout) findViewById(R.id.vehicle_listing_placeholder);
//        card.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                startActivity(new Intent(MainActivity.this, CarDetailsActivity.class));
//            }
//        });
    }




}
