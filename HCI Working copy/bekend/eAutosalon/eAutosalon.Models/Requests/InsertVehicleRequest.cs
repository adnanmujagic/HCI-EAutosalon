using System;
using System.Collections.Generic;
using System.Text;

namespace eAutosalon.Model.Requests
{
    public class InsertVehicleRequest
    {
        public string FirstRegistration { get; set; }
        public int Mileage { get; set; }
        public string Transmission { get; set; }
        public string FuelType { get; set; }
        public bool Climatisation { get; set; }
        public bool ABS { get; set; }
        public string DoorNumber { get; set; }
        public string TypeDescription { get; set; }
        public string AdditionalInformation { get; set; }
        public string Image { get; set; }
        public double Price { get; set; }
        public int ModelId { get; set; }
        public int UserId { get; set; }
    }
}
