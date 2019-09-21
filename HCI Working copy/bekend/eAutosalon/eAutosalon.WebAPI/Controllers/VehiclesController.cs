using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using eAutosalon.Model.Requests;
using eAutosalon.Model.ViewModels;
using eAutosalon.WebAPI.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace eAutosalon.WebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class VehiclesController : ControllerBase
    {
        private readonly IVehicleService _service;

        public VehiclesController(IVehicleService service)
        {
            _service = service;
        }

        [HttpGet]
        public VehicleViewModel Get()
        {
            return _service.GetVehicles();
        }

        [HttpGet("{id}/{userId}")]
        public VehicleViewModel.Vehicles GetById(int id, int userId)
        {
            return _service.GetById(id, userId);
        }

        [HttpGet("on-sale/{userId}")]
        public IActionResult GetVehiclesOnSale(int userId)
        {
            return Ok(_service.GetVehiclesOnSale(userId));
        }

        [HttpDelete("on-sale/remove/{vehicleId}")]
        public IActionResult RemoveVehicleOnSale(int vehicleId)
        {
            return Ok(_service.RemoveVehicleOnSale(vehicleId));
        }

        [HttpGet("bookmark/{id}/{userId}")]
        public BookmarkViewModel ToggleBookmark(int id, int userId)
        {
            return _service.ToggleBookmark(id, userId);
        }

        [HttpGet("ratings/{vehicleId}")]
        public RatingsViewModel GetRatings(int vehicleId)
        {
            return _service.GetRatings(vehicleId);
        }

        [HttpPost("ratings")]
        public IActionResult AddRating(RatingsViewModel.Ratings rating)
        {
            _service.AddRating(rating);
            return Ok();
        }

        [HttpGet("makes")]
        public IActionResult GetMakes()
        {
            return Ok(_service.GetMakes());
        }

        [HttpGet("makes/{makeId}/models")]
        public IActionResult GetModels(int makeId)
        {
            return Ok(_service.GetModels(makeId));
        }

        [HttpPost]
        public IActionResult AddVehicle(InsertVehicleRequest vehicle)
        {
            return Ok(_service.InsertVehicle(vehicle));
        }

        [HttpGet("bookmark/{userId}")]
        public IActionResult GetBookmarkedVehicles(int userId)
        {
            return Ok(_service.GetFavoriteVehicles(userId));
        }

        [HttpPost("search")]
        public IActionResult FilterVehicles(SearchRequest searchParams)
        {
            return Ok(_service.SearchVehicle(searchParams));
        }
    }
}