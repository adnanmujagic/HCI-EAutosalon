package com.example.eautosalon.data;

import java.io.Serializable;
import java.util.List;

public class VehicleListingVM implements Serializable {

    public class Vehicles {
        public int VehicleId;
        public String TypeDescription;
        public String AdditionalDescription;
        public String FirstRegistration;
        public int Mileage;
        public String Transmission;
        public String FuelType;
        public String DoorNumber;
        public String OwnerEmail;
        public String Image;
        public double Price;
        public boolean IsFavorite;
        public boolean Climatisation;
        public boolean ABS;
        public ModelVM Model;
    }

    public List<Vehicles> vehicles;
}
