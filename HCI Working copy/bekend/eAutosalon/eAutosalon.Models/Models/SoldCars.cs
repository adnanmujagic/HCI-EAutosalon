using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace eAutosalon.Model.Models
{
    public class SoldCars
    {
        public int SoldCarsId { get; set; }
        public string PaymentMethod { get; set; }
        public DateTime SellingDate { get; set; }
        public decimal PriceSold { get; set; }
        public int? UserId { get; set; }
        public int? VehicleId { get; set; }
        public User User { get; set; }
        public Vehicle Vehicle { get; set; }
    }
}
