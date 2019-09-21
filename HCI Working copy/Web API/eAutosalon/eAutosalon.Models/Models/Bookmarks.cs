using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace eAutosalon.Model.Models
{
    public class Bookmarks
    {
        public int BookmarksId { get; set; }
        public DateTime DateBookmarked { get; set; }
        public int? VehicleId { get; set; }
        public int? UserId { get; set; }
        public Vehicle Vehicle { get; set; }
        public User User { get; set; }
    }
}
