using eAutosalon.Model.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace eAutosalon.Model
{
    public class UserViewModel
    {
        public int UserId { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Email { get; set; }
        public string PhoneNumber { get; set; }
        public string Username { get; set; }
        public string Address1 { get; set; }
        public string Address2 { get; set; }
        public int? CityId { get; set; }
        public string Image { get; set; }
        public City City { get; set; }
    }
}
