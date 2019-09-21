package com.example.eautosalon.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eautosalon.R;
import com.example.eautosalon.data.BookmarkVM;
import com.example.eautosalon.data.UserVM;
import com.example.eautosalon.data.VehicleListingVM;
import com.example.eautosalon.helpers.FragmentUtilities;
import com.example.eautosalon.helpers.MyApiRequest;
import com.example.eautosalon.helpers.MyImageConverter;
import com.example.eautosalon.helpers.MyRunnable;
import com.example.eautosalon.helpers.MySession;

public class VehicleDetailsFragment extends Fragment {

    private View view;
    private String vehicleId;
    private VehicleListingVM.Vehicles vehicle;
    private ImageView img_favorite;
    private UserVM currentUser;
    private ImageView imgCarDetails;
    private TextView txtPrice;


    public VehicleDetailsFragment() {
    }

    public static VehicleDetailsFragment newInstance(String param1, String param2) {
        VehicleDetailsFragment fragment = new VehicleDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        vehicleId = bundle.getString("vehicleId");
        currentUser = MySession.GetUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_vehicle_details, container, false);

        imgCarDetails = view.findViewById(R.id.imgCarDetails);
        Button callSeller = view.findViewById(R.id.btn_call_seller);
        Button btn_email_seller = view.findViewById(R.id.btn_email_seller);
        Button btn_leave_rating = view.findViewById(R.id.btn_leave_rating);
        img_favorite = view.findViewById(R.id.img_favorite);
        txtPrice = view.findViewById(R.id.txtPrice);


        callSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel: +38762696027"));
                startActivity(intent);
            }
        });

        btn_email_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:" + vehicle.OwnerEmail + "?subject=" + "Question about the " + vehicle.Model.getMake().getName() + " - " + vehicle.Model.getName() + " on sell");
                intent.setData(data);
                startActivity(intent);
            }
        });

        btn_leave_rating.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle bundle = new Bundle();
                bundle.putString("vehicleId", vehicleId);
                bundle.putString("image", vehicle.Image);
                Fragment vehicleRating = new VehicleRatingFragment();
                vehicleRating.setArguments(bundle);
                FragmentUtilities.addFragment(getActivity(), vehicleRating, R.id.vehicle_listing_placeholder, true);
            }
        });

        img_favorite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                toggleBookmarkTask();
            }
        });

        loadDataTask();
        return view;
    }

    private void toggleBookmarkTask() {
        MyRunnable<BookmarkVM> myCallback = new MyRunnable<BookmarkVM>() {
            @Override
            public void run(BookmarkVM bookmarkVM) {
                toggleBookmark(bookmarkVM);
            }
        };

        MyApiRequest.get(getActivity(), "/api/vehicles/bookmark/" + vehicleId.toString() + "/" + currentUser.UserId, myCallback, view);
    }

    private void toggleBookmark(BookmarkVM bookmarkVM) {
        if(bookmarkVM == null){
            Toast.makeText(getActivity(), "Vehicle removed from favorites", Toast.LENGTH_LONG).show();
            img_favorite.setImageResource(R.drawable.ic_star_border_black_24dp);
        }else{
            Toast.makeText(getActivity(), "Vehicle added to favorites", Toast.LENGTH_LONG).show();
            img_favorite.setImageResource(R.drawable.ic_star_black_24dp);
        }
    }

    private void loadDataTask(){

        MyRunnable<VehicleListingVM.Vehicles> myCallback = new MyRunnable<VehicleListingVM.Vehicles>() {
            @Override
            public void run(VehicleListingVM.Vehicles vehicleListingVM) {
                loadData(vehicleListingVM);
            }
        };

        MyApiRequest.get(getActivity(), "/api/Vehicles/" + vehicleId.toString() + "/" + currentUser.UserId, myCallback, view);
    }

    private void loadData(final VehicleListingVM.Vehicles vehicleData) {

        vehicle = vehicleData;

        TextView txtTitle = view.findViewById(R.id.txtVehicleTitle);
        TextView txtTypeDescription = view.findViewById(R.id.txtVehicleTypeDescription);
        TextView txtMileageValue = view.findViewById(R.id.txtMileageValue);
        TextView txtFuelTypeValue = view.findViewById(R.id.txtFuelTypeValue);
        TextView txtFirstRegistrationValue = view.findViewById(R.id.txtFirstRegistrationValue);
        TextView txtTransmissionValue = view.findViewById(R.id.txtTransmissionValue);
        TextView txtAbsValue = view.findViewById(R.id.txtAbsValue);
        TextView txtClimatisation = view.findViewById(R.id.txtClimatisation);
        TextView txtAdditionalInformation = view.findViewById(R.id.txtAdditionalInformation);
        TextView txtDoorNumberValue = view.findViewById(R.id.txtDoorNumberValue);

        ImageView imgFavorite = (ImageView) view.findViewById(R.id.img_favorite);

        txtTitle.setText(vehicle.Model.getMake().getName() + " " + vehicle.Model.getName());
        txtTypeDescription.setText(vehicle.TypeDescription);
        txtMileageValue.setText(Integer.toString(vehicle.Mileage )+ " km");
        txtFuelTypeValue.setText(vehicle.FuelType);
        txtFirstRegistrationValue.setText(vehicle.FirstRegistration);
        txtTransmissionValue.setText(vehicle.Transmission);
        txtAbsValue.setText(vehicle.ABS ? "Yes" : "No");
        txtClimatisation.setText(vehicle.Climatisation ? "Yes" : "No");
        txtDoorNumberValue.setText(vehicle.DoorNumber);
        txtAdditionalInformation.setText(vehicle.AdditionalDescription);
        txtPrice.setText("€ " + Double.toString(vehicle.Price));
        imgFavorite.setImageResource(vehicle.IsFavorite ? R.drawable.ic_star_black_24dp : R.drawable.ic_star_border_black_24dp);
        if(vehicle.Image != null)
            imgCarDetails.setImageBitmap(MyImageConverter.decodeFromBase64ToBitmap(vehicle.Image));
    }

}
