using AutoMapper;
using eAutosalon.Model;
using eAutosalon.Model.Models;
using eAutosalon.Model.Requests;
using eAutosalon.Model.ViewModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace eAutosalon.WebAPI.Mapper
{
    public class Mapper : Profile
    {
        public Mapper()
        {
            CreateMap<Model.Models.User, UserViewModel>();
            CreateMap<Model.Models.User, UserInsertRequest>().ReverseMap();

            CreateMap<Model.Models.Vehicle, VehicleViewModel>();
        }
    }
}
