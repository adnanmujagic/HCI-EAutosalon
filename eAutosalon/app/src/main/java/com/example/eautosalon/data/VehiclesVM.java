package com.example.eautosalon.data;

public class VehiclesVM {
    public int VehicleId;
    public String TypeDescription;
    public String FirstRegistration;
    public int Mileage;
    public String Transmission;
    public String FuelType;
    public String DoorNumber;
    public double Price;
    public boolean Climatisation;
    public boolean ABS;

    public VehiclesVM(String typeDescription, String firstRegistration, int mileage, String transmission, String fuelType, String doorNumber, double price, boolean climatisation, boolean ABS, ModelVM model) {
        TypeDescription = typeDescription;
        FirstRegistration = firstRegistration;
        Mileage = mileage;
        Transmission = transmission;
        FuelType = fuelType;
        DoorNumber = doorNumber;
        Price = price;
        Climatisation = climatisation;
        this.ABS = ABS;
        Model = model;
    }

    public ModelVM Model;
}
