using System;

namespace eAutosalon.Model
{
    public class User
    {
        public string UserID { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }

        public string GetFullName
        {
            get
            {
                return FirstName + " " + LastName;
            }
        }
    }
}
