using System;
using System.Collections.Generic;
using System.Text;

namespace eAutosalon.Model.ViewModels
{
    public class MakesViewModel
    {
        public class Make
        {
            public int MakeId { get; set; }
            public string Name { get; set; }
        }

        public List<Make> Makes { get; set; }
    }
}
