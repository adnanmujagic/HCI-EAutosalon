package com.example.eautosalon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.eautosalon.data.UserVM;
import com.example.eautosalon.helpers.MySession;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserVM user = MySession.GetUser();

        if (user == null)
            startActivity(new Intent(this, LoginActivity.class));
        else
            startActivity(new Intent(this, MainDrawerActivity.class));
    }
}
