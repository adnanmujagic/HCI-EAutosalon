﻿using System;
using System.Collections.Generic;
using System.Text;

namespace eAutosalon.Model.Models
{
    public class City
    {
        public int CityId { get; set; }
        public string Name { get; set; }
        public int? CountryId { get; set; }
        public Country Country { get; set; }
    }
}
