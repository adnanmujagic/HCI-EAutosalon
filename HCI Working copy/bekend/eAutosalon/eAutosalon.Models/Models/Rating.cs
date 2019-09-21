using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace eAutosalon.Model.Models
{
    public class Rating
    {
        public int RatingId { get; set; }
        public string Comment { get; set; }
        public double RatingMark { get; set; }
        public DateTime DateReviewed { get; set; }
        public int? VehicleId { get; set; }
        public int? UserId { get; set; }
        public Vehicle Vehicle { get; set; }
        public User User { get; set; }
    }
}
