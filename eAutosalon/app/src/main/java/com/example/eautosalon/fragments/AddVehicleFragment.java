package com.example.eautosalon.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eautosalon.R;
import com.example.eautosalon.data.AddVehicleOnSellRequest;
import com.example.eautosalon.data.MakeVM;
import com.example.eautosalon.data.MakesListVM;
import com.example.eautosalon.data.ModelVM;
import com.example.eautosalon.data.ModelsListVM;
import com.example.eautosalon.data.SpinnerKeyValue;
import com.example.eautosalon.data.VehiclesVM;
import com.example.eautosalon.helpers.FragmentUtilities;
import com.example.eautosalon.helpers.MyApiRequest;
import com.example.eautosalon.helpers.MyApiResult;
import com.example.eautosalon.helpers.MyImageConverter;
import com.example.eautosalon.helpers.MyRunnable;
import com.example.eautosalon.helpers.MySession;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.eautosalon.helpers.MyImageConverter.PERMISSION_CODE;

public class AddVehicleFragment extends android.app.Fragment {

    public static final int IMAGE_PICK_CODE = 1000;
    public static final int PERMISSION_CODE = 1001;

    private View view;
    private Spinner makeSpinner;
    private Spinner modelSpinner;
    private EditText txtMileage;
    private ImageView carImageHolder;
    private Spinner gearboxSpinner;
    private Spinner fuelTypeSpinner;
    private EditText txtFirstRegistration;
    private EditText txtDoorNumber;
    private EditText txtModelDescription;
    private EditText txtAdditionalInformation;
    private EditText txtPrice;
    private CheckBox checkboxClimatisation;
    private CheckBox checkboxABS;
    private Button btnUploadImageCar;
    private Button btnPutOnSell;
    private Bitmap bitmap;
    private int makeId;
    private int modelId;
    private AddVehicleOnSellRequest vehicleModel;

    public static AddVehicleFragment newInstance() {
        return new AddVehicleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_add_vehicle, container, false);
        vehicleModel = new AddVehicleOnSellRequest();
        btnUploadImageCar = view.findViewById(R.id.btnUploadImageCar);
        btnPutOnSell = view.findViewById(R.id.btnPutOnSell);
        carImageHolder = view.findViewById(R.id.carImageHolder);
        makeSpinner = (Spinner) view.findViewById(R.id.makesSpinner);
        modelSpinner = (Spinner) view.findViewById(R.id.modelsSpinner);
        gearboxSpinner = view.findViewById(R.id.gearboxSpinner);
        fuelTypeSpinner = view.findViewById(R.id.fuelTypeSpinner);
        txtMileage = view.findViewById(R.id.txtMileage);
        txtFirstRegistration = view.findViewById(R.id.txtFirstRegistration);
        txtDoorNumber = view.findViewById(R.id.txtDoorNumber);
        txtModelDescription = view.findViewById(R.id.txtModelDescription);
        txtAdditionalInformation = view.findViewById(R.id.txtAdditionalInformation);
        txtPrice = view.findViewById(R.id.txtPrice);

        checkboxClimatisation = view.findViewById(R.id.checkboxClimatisation);
        checkboxABS = view.findViewById(R.id.checkboxABS);

        modelSpinner.setEnabled(false);
        loadMakesTask();

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

        btnUploadImageCar.setOnClickListener(new View.OnClickListener() {
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

        btnPutOnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(vehicleModel.Image != null){
                    if(invalidFields()){
                        Snackbar.make(view, "Please add the required details of the vehicle!", Snackbar.LENGTH_LONG).show();
                    }else{
                        addVehicleTask();
                    }
                }else{
                    Snackbar.make(view, "Please select an image from the gallery!", Snackbar.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }

    private void addVehicleTask() {
        populateModel();

        MyRunnable<VehiclesVM> myCallback = new MyRunnable<VehiclesVM>() {
            @Override
            public void run(VehiclesVM vehiclesVM) {
                Snackbar.make(view, "Vehicle successfully added on sell.", Snackbar.LENGTH_LONG).show();
                FragmentUtilities.addFragment(getActivity(), new VehiclesOnSell(), R.id.vehicle_listing_placeholder, false);
            }
        };

        MyApiRequest.post(getActivity(), "/api/vehicles", vehicleModel, myCallback, view);
    }

    private void populateModel() {
        vehicleModel.ABS = checkboxABS.isChecked();
        vehicleModel.Climatisation = checkboxClimatisation.isChecked();
        vehicleModel.DoorNumber = txtDoorNumber.getText().toString();
        vehicleModel.FirstRegistration = txtFirstRegistration.getText().toString();
        vehicleModel.FuelType = fuelTypeSpinner.getSelectedItem().toString();
        vehicleModel.Mileage = Integer.parseInt(txtMileage.getText().toString());
        vehicleModel.ModelId = modelId;
        vehicleModel.Price = Double.parseDouble(txtPrice.getText().toString());
        vehicleModel.Transmission = gearboxSpinner.getSelectedItem().toString();
        vehicleModel.TypeDescription = txtModelDescription.getText().toString();
        vehicleModel.AdditionalInformation = txtAdditionalInformation.getText().toString();
        vehicleModel.UserId = MySession.GetUser().UserId;
        vehicleModel.Image = MyImageConverter.convertToBase64(bitmap);
    }

    private boolean invalidFields() {

        boolean hasError = false;

        if(makeId == 0){
            ((TextView)makeSpinner.getSelectedView()).setError("Please select a make!");
            hasError = true;
        }

        if(txtPrice.getText().toString().length() == 0){
            txtPrice.setError("Please add a price!");
            hasError = true;
        }

        if(txtMileage.getText().toString().length() == 0){
            txtMileage.setError("Please add a valid mileage!");
            hasError = true;
        }

        if(txtFirstRegistration.getText().toString().length() == 0){
            txtFirstRegistration.setError("Please add a valid first registration!");
            hasError = true;
        }

        return hasError;
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
                carImageHolder.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            vehicleModel.Image = MyImageConverter.convertToBase64(bitmap);
        }
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

    private void loadModelsTask(int makeId){
        MyRunnable<ModelsListVM> myCallback = new MyRunnable<ModelsListVM>() {
            @Override
            public void run(ModelsListVM modelVMS) {
                loadModels(modelVMS);
            }
        };

        MyApiRequest.get(getActivity(), "/api/Vehicles/makes/" + makeId + "/models", myCallback, view);
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
