using eAutosalon.Model.Models;
using System.Collections.Generic;

namespace eAutosalon.Model.ViewModels
{
    public class RatingsViewModel
    {
        public class Ratings
        {
            public int VehicleId { get; set; }
            public string Comment { get; set; }
            public double RatingMark { get; set; }
            public string DateReviewed { get; set; }
            public int UserId { get; set; }
            public UserViewModel User { get; set; }
        }
        public List<Ratings> ratings { get; set; }
    }
}
