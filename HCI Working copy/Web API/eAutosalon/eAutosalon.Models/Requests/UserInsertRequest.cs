using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Text;

namespace eAutosalon.Model.Requests
{
    public class UserInsertRequest
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Email { get; set; }
        public string PhoneNumber { get; set; }
        public string Username { get; set; }
        public int CityId { get; set; }
        public string Address1 { get; set; }
        public string Image { get; set; }

        public string Password { get; set; }
        public string ConfirmPassword { get; set; }

    }
}
