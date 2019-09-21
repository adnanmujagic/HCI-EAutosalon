using eAutosalon.Model.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace eAutosalon.Model
{
    public class UserRoles
    {
        public int UserRolesId { get; set; }
        public DateTime ChangeDate { get; set; }
        public int UserId { get; set; }
        public int RoleId { get; set; }

        public User User { get; set; }
        public Role Role { get; set; }
    }
}
