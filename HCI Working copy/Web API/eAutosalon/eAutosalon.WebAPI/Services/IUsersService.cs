using eAutosalon.Model;
using eAutosalon.Model.Requests;
using eAutosalon.Model.ViewModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace eAutosalon.WebAPI.Services
{
    public interface IUsersService<TModel> : IService<TModel>
    {
        List<UserViewModel> GetBySearch(KorisniciSearchRequest request);
        UserViewModel Insert(UserInsertRequest request);
        UserViewModel Update(int id, UserInsertRequest request);
        UserViewModel Authenticiraj(string username, string password);
        CitiesViewModel GetCities();
    }
}
