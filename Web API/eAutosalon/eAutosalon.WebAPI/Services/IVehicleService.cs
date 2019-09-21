using eAutosalon.Model.Models;
using eAutosalon.Model.Requests;
using eAutosalon.Model.ViewModels;
using System.Collections.Generic;

namespace eAutosalon.WebAPI.Services
{
    public interface IVehicleService
    {
        VehicleViewModel GetVehicles();
        VehicleViewModel GetVehiclesOnSale(int userId);
        VehicleViewModel RemoveVehicleOnSale(int vehicleId);
        VehicleViewModel.Vehicles GetById(int id, int userId);
        VehicleViewModel GetFavoriteVehicles(int userId);
        BookmarkViewModel ToggleBookmark(int id, int userId);
        RatingsViewModel GetRatings(int vehicleId);
        MakesViewModel GetMakes();
        ModelsViewModel GetModels(int makeId);
        VehicleViewModel InsertVehicle(InsertVehicleRequest vehicle);
        void AddRating(RatingsViewModel.Ratings vehicleId);
        VehicleViewModel SearchVehicle(SearchRequest searchParams);
    }
}
