using System;
using System.Collections.Generic;
using System.Text;

namespace eAutosalon.Model.Requests
{
    public class SearchRequest
    {
        public int ModelId { get; set; }
        public string FuelType { get; set; }
        public int RegistrationFrom { get; set; }
        public int RegistrationTo { get; set; }
        public double PriceFrom { get; set; }
        public double PriceTo { get; set; }
    }
}
