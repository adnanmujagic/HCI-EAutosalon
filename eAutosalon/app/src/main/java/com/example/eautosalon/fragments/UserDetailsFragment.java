package com.example.eautosalon.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eautosalon.R;
import com.example.eautosalon.UserDetails;
import com.example.eautosalon.data.UserVM;
import com.example.eautosalon.helpers.FragmentUtilities;
import com.example.eautosalon.helpers.MyImageConverter;
import com.example.eautosalon.helpers.MySession;

public class UserDetailsFragment extends Fragment {

    private View view;

    private Button btn;
    private TextView txt_details_name;
    private TextView txt_details_country;
    private TextView txt_details_address;
    private TextView txt_details_city;
    private TextView txt_details_email;
    private TextView txt_details_phoneNumber;
    private UserVM user;
    private ImageView img_user_detail;

    public UserDetailsFragment() {
        // Required empty public constructor
    }

    public static UserDetailsFragment newInstance(String param1, String param2) {
        UserDetailsFragment fragment = new UserDetailsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_details, container, false);
        txt_details_name = view.findViewById(R.id.txt_details_name);
        txt_details_country = view.findViewById(R.id.txt_details_country);
        txt_details_address = view.findViewById(R.id.txt_details_address);
        txt_details_city = view.findViewById(R.id.txt_details_city);
        txt_details_email = view.findViewById(R.id.txt_details_email);
        txt_details_phoneNumber = view.findViewById(R.id.txt_details_phoneNumber);
        txt_details_phoneNumber = view.findViewById(R.id.txt_details_phoneNumber);
        img_user_detail = view.findViewById(R.id.img_user_detail);

        user = MySession.GetUser();

        txt_details_name.setText(user.FirstName + " " + user.LastName);
        txt_details_country.setText(user.City.Country.Name);
        txt_details_city.setText(user.City.Name);
        txt_details_address.setText(user.Address1);
        txt_details_email.setText(user.Email);
        txt_details_phoneNumber.setText(user.PhoneNumber);
        if(user.Image != null){
            img_user_detail.setImageBitmap(MyImageConverter.decodeFromBase64ToBitmap(user.Image));
        }

        btn = (Button) view.findViewById(R.id.edit_user_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtilities.addFragment(getActivity(), new EditUserFragment(), R.id.user_details_placeholder, true);
            }
        });

        return view;
    }

}
