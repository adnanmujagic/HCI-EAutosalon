package com.example.eautosalon.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.eautosalon.R;
import com.example.eautosalon.data.VehicleListingVM;
import com.example.eautosalon.data.VehiclesVM;
import com.example.eautosalon.helpers.FragmentUtilities;
import com.example.eautosalon.helpers.MyApiRequest;
import com.example.eautosalon.helpers.MyImageConverter;
import com.example.eautosalon.helpers.MyRunnable;
import com.example.eautosalon.helpers.MySession;

import java.util.List;


public class FavoriteVehiclesFragment extends Fragment {

    private ListView lvVehicles;
    private View view;

    public FavoriteVehiclesFragment() {
    }

    public static FavoriteVehiclesFragment newInstance() {
        FavoriteVehiclesFragment fragment = new FavoriteVehiclesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite_vehicles, container, false);

        lvVehicles = view.findViewById(R.id.vehicleList);
        loadDataTask();

        lvVehicles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = view.findViewById(R.id.txtVehicleId);

                Bundle vehicleId = new Bundle();
                vehicleId.putString("vehicleId", textView.getText().toString());

                android.app.Fragment vehicleDetails = new VehicleDetailsFragment();
                vehicleDetails.setArguments(vehicleId);

                FragmentUtilities.addFragment(getActivity(), vehicleDetails, R.id.vehicle_listing_placeholder, true);

            }
        });

        return view;
    }

    private void loadDataTask(){

        MyRunnable<VehicleListingVM> myCallback = new MyRunnable<VehicleListingVM>() {
            @Override
            public void run(VehicleListingVM vehicleListingVM) {
                loadData(vehicleListingVM);
            }
        };

        MyApiRequest.get(getActivity(), "/api/Vehicles/bookmark/" + MySession.GetUser().UserId, myCallback, view);
    }

    private void loadData(final VehicleListingVM vehicles) {

        lvVehicles.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return vehicles.vehicles.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                if(view == null){
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.vehicle_item, parent,false);
                }
                ImageView imgVehicleImage = view.findViewById(R.id.imgVehicleImage);
                TextView txtVehicleId = view.findViewById(R.id.txtVehicleId);
                TextView txtCarTitle = view.findViewById(R.id.txtCarTitle);
                TextView txtCarDescription = view.findViewById(R.id.txtCarDescription);
                TextView txtPrice = view.findViewById(R.id.txtCarPrice);

                VehicleListingVM.Vehicles vehicle = vehicles.vehicles.get(position);
                txtVehicleId.setText(Integer.toString(vehicle.VehicleId));
                txtCarTitle.setText(vehicle.Model.getMake().getName() + " " + vehicle.Model.getName());
                txtCarDescription.setText(vehicle.TypeDescription);
                txtPrice.setText("€ " + Double.toString(vehicle.Price));
                if(vehicle.Image != null){
                    imgVehicleImage.setImageBitmap(MyImageConverter.decodeFromBase64ToBitmap(vehicle.Image));
                }

                return view;
            }
        });
    }

}
