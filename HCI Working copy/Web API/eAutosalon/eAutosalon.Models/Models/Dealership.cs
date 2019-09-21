using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace eAutosalon.Model.Models
{
    public class Dealership
    {
        public int DealershipId { get; set; }
        public string Name { get; set; }
        public string Address { get; set; }
        public int? CityId { get; set; }
        public City City { get; set; }
    }
}
