package com.example.eautosalon.fragments;


import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eautosalon.R;
import com.example.eautosalon.data.UserVM;
import com.example.eautosalon.data.VehicleListingVM;
import com.example.eautosalon.helpers.MyApiRequest;
import com.example.eautosalon.helpers.MyImageConverter;
import com.example.eautosalon.helpers.MyRunnable;
import com.example.eautosalon.helpers.MySession;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;
import static com.example.eautosalon.helpers.MyImageConverter.IMAGE_PICK_CODE;
import static com.example.eautosalon.helpers.MyImageConverter.PERMISSION_CODE;

public class EditUserFragment extends Fragment {

    private UserVM user;
    private View view;
    private TextView txtUsername;
    private TextView txtFirstName;
    private TextView txtLastName;
    private TextView txtEmail;
    private TextView txtPhoneNumber;
    private TextView txtAddress;
    private TextView txtNewPassword;
    private TextView txtRepeatPassword;
    private ImageView imgUserImage;
    private Button btn_save;

    private Button btnUploadImage;
    private Bitmap bitmap;

    public EditUserFragment() {
    }

    public static EditUserFragment newInstance(String param1, String param2) {
        return new EditUserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void updateUserTask() {
        user.FirstName = txtFirstName.getText().toString();
        user.LastName = txtLastName.getText().toString();
        user.Email = txtEmail.getText().toString();
        user.Address1 = txtAddress.getText().toString();
        user.PhoneNumber = txtPhoneNumber.getText().toString();
        user.Username = txtUsername.getText().toString();
        user.Password = txtNewPassword.getText().toString();

        MyRunnable<UserVM> myCallback = new MyRunnable<UserVM>() {
            @Override
            public void run(UserVM user) {
                updateUser(user);
            }
        };

        MyApiRequest.put(getActivity(), "/api/Users/" + user.UserId, user, myCallback, view);
    }

    private void updateUser(UserVM user) {
        Toast.makeText(getActivity(), "User changes saved!", Toast.LENGTH_SHORT).show();
        MySession.SetUser(user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_user, container, false);
        user = MySession.GetUser();

        txtUsername = view.findViewById(R.id.edit_username);
        txtFirstName = view.findViewById(R.id.edit_firstName);
        txtLastName = view.findViewById(R.id.edit_lastName);
        txtEmail = view.findViewById(R.id.edit_email);
        txtPhoneNumber = view.findViewById(R.id.edit_phoneNumber);
        txtAddress = view.findViewById(R.id.edit_address1);
        txtNewPassword = view.findViewById(R.id.edit_password);
        txtRepeatPassword = view.findViewById(R.id.edit_repeatPassword);
        btn_save = view.findViewById(R.id.btn_save);
        imgUserImage = view.findViewById(R.id.img_user_image_placeholder);
        btnUploadImage = view.findViewById(R.id.btn_uploadImage);

        txtUsername.setText(user.Username);
        txtFirstName.setText(user.FirstName);
        txtLastName.setText(user.LastName);
        txtEmail.setText(user.Email);
        txtAddress.setText(user.Address1);
        txtEmail.setText(user.Email);
        txtPhoneNumber.setText(user.PhoneNumber);
        if(user.Image != null){
            imgUserImage.setImageBitmap(MyImageConverter.decodeFromBase64ToBitmap(user.Image));
        }
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = txtNewPassword.getText().toString();
                String repeatedPassword = txtRepeatPassword.getText().toString();

                if(password.isEmpty() || repeatedPassword.isEmpty())
                {
                    Toast.makeText(getActivity(), "New password and repeated password fields must contain a value!", Toast.LENGTH_SHORT).show();
                }else
                {
                    if(password.equals(repeatedPassword))
                    {
                        updateUserTask();
                    }else
                    {
                        Toast.makeText(getActivity(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, PERMISSION_CODE);
                }else{
                    pickImageFromGallery();
                }
            }
        });
        return view;
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch(requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }else{
                    Toast.makeText(getActivity(), "Permission denied!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            try {

                if (bitmap != null) {
                    bitmap.recycle();
                }
                InputStream stream = getActivity().getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(stream);
                stream.close();
                imgUserImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
              user.Image = MyImageConverter.convertToBase64(bitmap);
        }
    }


}
