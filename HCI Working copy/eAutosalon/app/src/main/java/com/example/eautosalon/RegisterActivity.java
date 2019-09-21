package com.example.eautosalon;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eautosalon.data.CitiesListVM;
import com.example.eautosalon.data.CityVM;
import com.example.eautosalon.data.SpinnerKeyValue;
import com.example.eautosalon.data.UserVM;
import com.example.eautosalon.helpers.MyApiRequest;
import com.example.eautosalon.helpers.MyRunnable;
import com.example.eautosalon.helpers.MySession;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private TextView txtUsername;
    private TextView txtFirstName;
    private TextView txtLastName;
    private TextView txtEmail;
    private TextView txtPhoneNumber;
    private TextView txtAddress;
    private TextView txtNewPassword;
    private TextView txtRepeatPassword;
    private Button btn_save;
    private UserVM newUser;
    private Spinner citiesSpinner;
    private CityVM cityVM;
    private int cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Create new account");

        cityVM = new CityVM();
        txtUsername = findViewById(R.id.edit_username);
        txtFirstName = findViewById(R.id.edit_firstName);
        txtLastName = findViewById(R.id.edit_lastName);
        txtEmail = findViewById(R.id.edit_email);
        txtPhoneNumber = findViewById(R.id.edit_phoneNumber);
        txtAddress = findViewById(R.id.edit_address1);
        txtNewPassword = findViewById(R.id.edit_password);
        txtRepeatPassword = findViewById(R.id.edit_repeatPassword);
        btn_save = findViewById(R.id.btn_save);
        citiesSpinner = findViewById(R.id.citiesSpinner);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(invalidFields()){
                    Snackbar.make(v, "Please fill in all the fields!", Snackbar.LENGTH_LONG).show();
                }else{
                    if(passwordsMatch()){
                        createUser();
                    }else{
                        Snackbar.make(v, "Passwords do not match!", Snackbar.LENGTH_LONG).show();
                    }
                }

            }
        });

        citiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerKeyValue s = (SpinnerKeyValue) parent.getItemAtPosition(position);
                cityId = s.key;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loadCitiesTask();
    }

    private void loadCitiesTask() {
        MyRunnable<CitiesListVM> myCallback = new MyRunnable<CitiesListVM>() {
            @Override
            public void run(CitiesListVM cityVM) {
                loadCitiesSpinner(cityVM);
            }
        };

        MyApiRequest.get(this, "/api/users/cities", myCallback, null);
    }

    private void loadCitiesSpinner(CitiesListVM cityVM) {
        ArrayList<SpinnerKeyValue> options = new ArrayList<>();

        options.add(new SpinnerKeyValue("Choose a city...", 0));

        for (CitiesListVM.City city : cityVM.Cities) {
            options.add(new SpinnerKeyValue(city.Name, city.CityId));
        }

        ArrayAdapter<SpinnerKeyValue> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,options);
        citiesSpinner.setAdapter(adapter);
    }

    private void createUser() {
        newUser = new UserVM();

        newUser.Username = txtUsername.getText().toString();
        newUser.FirstName = txtFirstName.getText().toString();
        newUser.LastName = txtLastName.getText().toString();
        newUser.PhoneNumber = txtPhoneNumber.getText().toString();
        newUser.Email = txtEmail.getText().toString();
        newUser.Address1 = txtAddress.getText().toString();
        newUser.Password = txtNewPassword.getText().toString();
        newUser.ConfirmPassword = txtRepeatPassword.getText().toString();
        newUser.CityId = cityId;

        MyRunnable<UserVM> myCallback = new MyRunnable<UserVM>() {
            @Override
            public void run(UserVM user) {
                redirectToLogin();
            }
        };

        MyApiRequest.post(this, "/api/Users/", newUser, myCallback, null);
    }

    private void redirectToLogin() {
        Toast.makeText(this, "Account created! Please log in", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
    }

    private boolean passwordsMatch() {
        return txtNewPassword.getText().toString().equals(txtRepeatPassword.getText().toString());
    }

    private boolean invalidFields(){

        boolean hasError = false;

        if(txtUsername.getText().toString().length() == 0){
            hasError = true;
        }

        if(txtFirstName.getText().toString().length() == 0){
            hasError = true;
        }

        if(txtLastName.getText().toString().length() == 0){
            hasError = true;
        }

        if(txtEmail.getText().toString().length() == 0){
            hasError = true;
        }

        if(txtPhoneNumber.getText().toString().length() == 0){
            hasError = true;
        }

        if(txtAddress.getText().toString().length() == 0){
            hasError = true;
        }

        if(txtNewPassword.getText().toString().length() == 0){
            hasError = true;
        }

        if(txtRepeatPassword.getText().toString().length() == 0){
            hasError = true;
        }
        if(cityId == 0)
            hasError = true;

        return hasError;
    }
}
