package com.example.eautosalon.data;

import com.example.eautosalon.helpers.MyObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Storage {

    private static List<VehiclesVM> vehicles;
    private static List<UserVM> users;

    public static List<VehiclesVM> getVehicles(){

//        if(vehicles == null){
//            vehicles = new ArrayList<>();
//            vehicles.add(new VehiclesVM("2010","3.0 TDI Street Edition",120150, "Manual", "Diesel", "4/5",8900, true, true, new ModelVM("Golf 5", new MakeVM("Volkswagen"))));
//            vehicles.add(new VehiclesVM("2012","2.0 TDI Standard Edition",201222, "Manual", "Diesel", "4/5",14900, true, true, new ModelVM("Golf 6", new MakeVM("Volkswagen"))));
//            vehicles.add(new VehiclesVM("2016","",159222, "Manual", "Diesel", "4/5",19900, true, true, new ModelVM("M3", new MakeVM("BMW"))));
//            vehicles.add(new VehiclesVM("2008","1.8 TDI",105156, "Manual", "Diesel", "2/3",10900, true, true, new ModelVM("C220", new MakeVM("Mercedes"))));
//            vehicles.add(new VehiclesVM("2005","1.8 Some description",190585, "Manual", "Petrol", "4/5",5900, true, true, new ModelVM("Golf 3", new MakeVM("Volkswagen"))));
//        }

        return vehicles;
    }

//    public static List<UserVM> GetUsers(){
//
//        if(users == null){
//            users = new ArrayList<>();
//            users.add(new UserVM("Adnan", "Mujagic", "sowa", "1234", "adnan@mail.com", "062 696 027"));
//            users.add(new UserVM("Kenan", "Spahic", "keno", "12345", "keno@mail.com", "062 480 868"));
//        }
//
//        return users;
//    }
//
//    public static UserVM UserLogin(String username, String password){
//        List<UserVM> users = GetUsers();
//        for (UserVM x : GetUsers()){
//            if(MyObjects.equals(x.getUsername(), username) && MyObjects.equals(x.getPassword(), password))
//                return x;
//        }
//        return null;
//    }
}
