using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace eAutosalon.Model.Models
{
    public class User
    {
        public User()
        {
            UserRoles = new HashSet<UserRoles>();
            Vehicles = new HashSet<Vehicle>();
        }
        public int UserId { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Email { get; set; }
        public string PhoneNumber { get; set; }
        public string Username { get; set; }
        public string PasswordHash { get; set; }
        public string PasswordSalt { get; set; }
        public string Address1 { get; set; }
        public string Address2 { get; set; }
        public string Active { get; set; }
        public int? CityId { get; set; }
        public City City { get; set; }

        public ICollection<UserRoles> UserRoles { get; set; }
        public ICollection<Vehicle> Vehicles { get; set; }
    }
}
