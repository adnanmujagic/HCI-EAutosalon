using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace eAutosalon.WebAPI.Services
{
    public interface IService<T>
    {
        List<T> Get();
        T GetById(int id);
    }
}
