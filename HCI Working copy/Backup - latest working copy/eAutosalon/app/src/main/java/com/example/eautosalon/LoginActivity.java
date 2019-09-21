package com.example.eautosalon;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eautosalon.data.UserLoginViewModel;
import com.example.eautosalon.data.UserVM;
import com.example.eautosalon.helpers.MyApiRequest;
import com.example.eautosalon.helpers.MyRunnable;
import com.example.eautosalon.helpers.MySession;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;
    private UserLoginViewModel loginData;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginData = new UserLoginViewModel();

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);


        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_checkLogin();
            }
        });

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_OpenRegister();
            }
        });
    }

    private void do_OpenRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void do_checkLogin() {
        loginData.Username = txtUsername.getText().toString();
        loginData.Password = txtPassword.getText().toString();

        UserVM user = null;

        checkLoginTask(loginData);

    }

    private void checkLoginTask(UserLoginViewModel loginData){

        MyRunnable<UserVM> myCallback = new MyRunnable<UserVM>() {
            @Override
            public void run(UserVM user) {
                checkLogin(user);
            }
        };

        MyApiRequest.post(this, "/api/Users/Login", loginData, myCallback, null);
    }

    private void checkLogin(final UserVM user) {

        if(user == null){
            view = findViewById(android.R.id.content);
            Snackbar.make(view, "Oops! Incorrect username or password!", Snackbar.LENGTH_LONG).show();
        }else{
            MySession.SetUser(user);
            startActivity(new Intent(this, MainDrawerActivity.class));
        }

    }

}
