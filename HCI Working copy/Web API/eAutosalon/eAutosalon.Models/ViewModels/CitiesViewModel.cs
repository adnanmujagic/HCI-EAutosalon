using eAutosalon.Model.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace eAutosalon.Model.ViewModels
{
    public class CitiesViewModel
    {
        public class City
        {
            public int CityId { get; set; }
            public string Name { get; set; }
            public Country Country { get; set; }
        }

        public List<City> Cities { get; set; }
    }
}
