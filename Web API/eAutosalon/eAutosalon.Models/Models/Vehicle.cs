using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace eAutosalon.Model.Models
{
    public class Vehicle
    {
        public Vehicle()
        {
            VehicleImages = new HashSet<Image>();
        }

        public int VehicleId { get; set; }
        public string TypeDescription { get; set; }
        public string FirstRegistration { get; set; }
        public int Mileage { get; set; }
        public string Transmission { get; set; }
        public string FuelType { get; set; }
        public bool Climatisation { get; set; }
        public bool ABS { get; set; }
        public string DoorNumber { get; set; }
        public decimal Price { get; set; }
        public int? ModelId { get; set; }
        public Model Model { get; set; }
        public int? DealershipId { get; set; }
        public Dealership Dealership { get; set; }
        public int? UserId { get; set; }
        public User User { get; set; }
        public string AdditionalDescription { get; set; }

        public ICollection<Image> VehicleImages { get; set; }
    }
}
