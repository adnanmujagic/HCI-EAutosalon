using eAutosalon.Model.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace eAutosalon.Model.ViewModels
{
    public class VehicleViewModel
    {
        public class Vehicles
        {
            public int VehicleId { get; set; }
            public string FirstRegistration { get; set; }
            public string TypeDescription { get; set; }
            public string AdditionalDescription { get; set; }
            public int Mileage { get; set; }
            public string Transmission { get; set; }
            public string FuelType { get; set; }
            public bool Climatisation { get; set; }
            public bool ABS { get; set; }
            public bool IsFavorite { get; set; }
            public string DoorNumber { get; set; }
            public string Image { get; set; }
            public decimal Price { get; set; }
            public int? ModelId { get; set; }
            public Models.Model Model { get; set; }
            public string OwnerEmail { get; set; }

        }
        public List<Vehicles> vehicles { get; set; }
    }
}
