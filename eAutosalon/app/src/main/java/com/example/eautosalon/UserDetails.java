package com.example.eautosalon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eautosalon.data.UserVM;
import com.example.eautosalon.fragments.EditUserFragment;
import com.example.eautosalon.fragments.UserDetailsFragment;
import com.example.eautosalon.helpers.FragmentUtilities;
import com.example.eautosalon.helpers.MySession;

import org.w3c.dom.Text;

public class UserDetails extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        FragmentUtilities.addFragment(UserDetails.this, new UserDetailsFragment(), R.id.user_details_placeholder, false);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("User Details");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //call super
        super.onActivityResult(requestCode, resultCode, data);
    }
}
