package com.example.eautosalon.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eautosalon.R;
import com.example.eautosalon.data.MakesListVM;
import com.example.eautosalon.data.ModelsListVM;
import com.example.eautosalon.data.SearchRequestVM;
import com.example.eautosalon.data.SpinnerKeyValue;
import com.example.eautosalon.data.VehicleListingVM;
import com.example.eautosalon.helpers.FragmentUtilities;
import com.example.eautosalon.helpers.MyApiRequest;
import com.example.eautosalon.helpers.MyRunnable;

import java.util.ArrayList;

public class SearchDialogFragment extends DialogFragment {

    private View view;
    private Spinner makeSpinner;
    private Spinner modelSpinner;
    private int makeId;
    private int modelId;
    private Spinner fuelTypeSpinner;
    private EditText txtFirstRegistrationFrom;
    private EditText txtFirstRegistrationTo;
    private Button btnSearch;
    private EditText txtPriceFrom;
    private EditText txtPriceTo;

    public SearchDialogFragment() {
    }

    public static SearchDialogFragment newInstance() {
        SearchDialogFragment fragment = new SearchDialogFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_dialog, container, false);
        makeSpinner = view.findViewById(R.id.makesSpinner);
        modelSpinner = view.findViewById(R.id.modelsSpinner);
        fuelTypeSpinner = view.findViewById(R.id.fuelTypeSpinner);
        txtFirstRegistrationFrom = view.findViewById(R.id.txtFirstRegistrationFrom);
        txtFirstRegistrationTo = view.findViewById(R.id.txtFirstRegistrationTo);
        txtPriceFrom = view.findViewById(R.id.txtPriceFrom);
        txtPriceTo = view.findViewById(R.id.txtPriceTo);

        btnSearch = view.findViewById(R.id.btnSearch);

        makeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerKeyValue s = (SpinnerKeyValue) parent.getItemAtPosition(position);
                makeId = s.key;
                if(makeId != 0)
                    loadModelsTask(makeId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        modelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(makeId != 0){
                    SpinnerKeyValue s = (SpinnerKeyValue) parent.getItemAtPosition(position);
                    modelId = s.key;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchVehicle();
            }
        });

        loadMakesTask();

        return view;
    }

    private void searchVehicle() {
        SearchRequestVM search = new SearchRequestVM();


        String regFrom = txtFirstRegistrationFrom.getText().toString();
        String regTo = txtFirstRegistrationTo.getText().toString();
        String priceFrom = txtPriceFrom.getText().toString();
        String priceTo = txtPriceTo.getText().toString();

        search.ModelId = modelId;
        search.FuelType = fuelTypeSpinner.getSelectedItem().toString();
        search.RegistrationFrom = Integer.parseInt(regFrom.length() > 0 ? regFrom : "0");
        search.RegistrationTo = Integer.parseInt(regTo.length() > 0 ? regTo : "0");
        search.PriceFrom = Double.parseDouble(priceFrom.length() > 0 ? priceFrom : "0");
        search.PriceTo = Double.parseDouble(priceTo.length() > 0 ? priceTo : "0");

        MyRunnable<VehicleListingVM> myCallback = new MyRunnable<VehicleListingVM>() {
            @Override
            public void run(VehicleListingVM vehicleListingVM) {
                if(vehicleListingVM.vehicles.size() > 0){
                    Bundle arg = new Bundle();
                    arg.putSerializable("vehicles",vehicleListingVM);
                    VehicleListFragment newFragment = new VehicleListFragment();
                    newFragment.setArguments(arg);
                    getDialog().dismiss();
                    FragmentUtilities.addFragment(getActivity(),newFragment, R.id.vehicle_listing_placeholder, false);
                }else{
                    Toast.makeText(getActivity(), "No vehicles match your search!", Toast.LENGTH_SHORT).show();
                }
            }
        };

        MyApiRequest.post(getActivity(), "/api/Vehicles/search", search, myCallback, view);
    }

    private void loadMakesTask() {
        MyRunnable<MakesListVM> myCallback = new MyRunnable<MakesListVM>() {
            @Override
            public void run(MakesListVM makeVMS) {
                loadMakes(makeVMS);
            }
        };

        MyApiRequest.get(getActivity(), "/api/Vehicles/makes", myCallback, view);
    }

    private void loadMakes(MakesListVM makeVMS) {
        ArrayList<SpinnerKeyValue> options = new ArrayList<>();

        options.add(new SpinnerKeyValue("Choose a make...", 0));

        for (MakesListVM.Makes make : makeVMS.Makes) {
            options.add(new SpinnerKeyValue(make.Name, make.MakeId));
        }

        ArrayAdapter<SpinnerKeyValue> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,options);
        makeSpinner.setAdapter(adapter);
    }

    private void loadModelsTask(int makeId){
        MyRunnable<ModelsListVM> myCallback = new MyRunnable<ModelsListVM>() {
            @Override
            public void run(ModelsListVM modelVMS) {
                loadModels(modelVMS);
            }
        };

        MyApiRequest.get(getActivity(), "/api/Vehicles/makes/" + makeId + "/models", myCallback, view);
    }

    private void loadModels(ModelsListVM modelVMS) {
        ArrayList<SpinnerKeyValue> models = new ArrayList<>();

        if(modelVMS.Models.size() > 0){
            for(ModelsListVM.Model model : modelVMS.Models){
                models.add(new SpinnerKeyValue(model.Name, model.ModelId));
            }
            ArrayAdapter<SpinnerKeyValue> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,models);
            modelSpinner.setAdapter(adapter);
            modelSpinner.setEnabled(true);
        }else{
            models.add(new SpinnerKeyValue("Choose a make...", 0));
            ArrayAdapter<SpinnerKeyValue> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,models);
            modelSpinner.setEnabled(false);
        }
    }
}
