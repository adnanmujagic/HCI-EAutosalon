using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace eAutosalon.Model.Models
{
    public class Image
    {
        public int ImageId { get; set; }
        public byte[] Img { get; set; }
        public int? UserId { get; set; }
        public User User { get; set; }
        public int? VehicleId { get; set; }
        public Vehicle Vehicle { get; set; }
    }
}
