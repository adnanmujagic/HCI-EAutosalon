using System;
using System.Collections.Generic;
using System.Text;

namespace eAutosalon.Model.ViewModels
{
    public class ModelsViewModel
    {
        public class Model
        {
            public int? ModelId { get; set; }
            public string Name { get; set; }
        }

        public List<Model> Models { get; set; }
    }
}
