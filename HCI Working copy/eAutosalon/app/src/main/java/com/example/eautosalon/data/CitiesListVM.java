package com.example.eautosalon.data;

import java.util.List;

public class CitiesListVM {

    public class City {
        public int CityId;
        public String Name;
        public CountryVM Country;
    }

    public List<City> Cities;
}
