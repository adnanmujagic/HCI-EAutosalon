using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using eAutosalon.Model;
using eAutosalon.Model.Requests;
using eAutosalon.Model.ViewModels;
using eAutosalon.WebAPI.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace eAutosalon.WebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        private readonly IUsersService<UserViewModel> _usersService;
        public UsersController(IUsersService<UserViewModel> usersService)
        {
            _usersService = usersService;
        }

        [HttpGet]
        public List<UserViewModel> Get()
        {
            return _usersService.Get();
        }

        [HttpGet("SearchUser")]
        public List<UserViewModel> GetBySearch([FromQuery]KorisniciSearchRequest request)
        {

            return _usersService.GetBySearch(request);
        }

        [HttpGet("{id}")]
        public UserViewModel GetUserById(int id)
        {
            return _usersService.GetById(id);
        }

        [HttpPost]
        public UserViewModel Insert(UserInsertRequest request)
        {
            return _usersService.Insert(request);
        }

        [HttpPut("{id}")]
        public UserViewModel Update(int id, UserInsertRequest request)
        {
            return _usersService.Update(id, request);
        }

        [HttpPost("Login")]
        public UserViewModel Login([FromBody]UserLoginViewModel login)
        {
            return _usersService.Authenticiraj(login.Username, login.Password);
        }

        [HttpGet("cities")]
        public IActionResult GetCities()
        {
            return Ok(_usersService.GetCities());
        }
    }
}