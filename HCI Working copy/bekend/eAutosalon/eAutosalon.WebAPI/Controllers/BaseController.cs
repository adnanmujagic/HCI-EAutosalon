using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using eAutosalon.WebAPI.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace eAutosalon.WebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class BaseController<T> : ControllerBase
    {
        private readonly IService<T> _service;
        public BaseController(IService<T> service)
        {
            _service = service;
        }
    }
}