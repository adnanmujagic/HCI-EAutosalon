using AutoMapper;
using eAutosalon.Model.Models;
using eAutosalon.Model.ViewModels;
using eAutosalon.WebAPI.Database;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using eAutosalon.Model.Requests;
using eAutosalon.WebAPI.Helpers;
using System.Text;

namespace eAutosalon.WebAPI.Services
{
    public class VehicleService : IVehicleService
    {
        private readonly eAutosalonContext _context;

        public VehicleService(eAutosalonContext context)
        {
            _context = context;
        }

        public VehicleViewModel GetVehicles()
        {
            VehicleViewModel model = new VehicleViewModel
            {
                vehicles = _context.Vehicles.Include(x => x.User).Include(x => x.Model).Include(x => x.Model.Make).Select(x => new VehicleViewModel.Vehicles()
                {
                    VehicleId = x.VehicleId,
                    ABS = x.ABS,
                    Climatisation = x.Climatisation,
                    DoorNumber = x.DoorNumber,
                    FirstRegistration = x.FirstRegistration,
                    TypeDescription = x.TypeDescription,
                    FuelType = x.FuelType,
                    Mileage = x.Mileage,
                    Image = ConvertImage(_context.Images.Where(y => y.VehicleId == x.VehicleId).FirstOrDefault().Img),
                    ModelId = x.ModelId,
                    Model = new Model.Models.Model { ModelId = x.ModelId, Name = x.Model.Name, Make = x.Model.Make },
                    Price = x.Price,
                    OwnerEmail = x.User.Email,
                    Transmission = x.Transmission
                }).ToList()
            };

            return model;
        }

        public VehicleViewModel.Vehicles GetById(int id, int userId)
        {
            return _context.Vehicles.Include(x => x.User).Include("Models.Makes")
                .Select(x => new VehicleViewModel.Vehicles
                {
                    ABS = x.ABS,
                    VehicleId = x.VehicleId,
                    Climatisation = x.Climatisation,
                    DoorNumber = x.DoorNumber,
                    FirstRegistration = x.FirstRegistration,
                    FuelType = x.FuelType,
                    Mileage = x.Mileage,
                    IsFavorite = _context.Bookmarks.FirstOrDefault(y => y.UserId == userId && y.VehicleId == id) != null,
                    Model = new Model.Models.Model { Name = x.Model.Name, Make = x.Model.Make },
                    ModelId = (int)x.ModelId,
                    Price = x.Price,
                    AdditionalDescription = x.AdditionalDescription,
                    Transmission = x.Transmission,
                    OwnerEmail = x.User.Email,
                    Image = ConvertImage(_context.Images.Where(y => y.VehicleId == x.VehicleId).FirstOrDefault().Img),
                    TypeDescription = x.TypeDescription
                }).FirstOrDefault(x => x.VehicleId == id); ;
        }

        public BookmarkViewModel ToggleBookmark(int id, int userId)
        {
            Bookmarks bookmark = _context.Bookmarks.FirstOrDefault(x => x.UserId == userId && x.VehicleId == id);

            if (bookmark != null)
            {
                _context.Bookmarks.Remove(bookmark);
                _context.SaveChanges();
            }
            else
            {
                bookmark = new Bookmarks()
                {
                    UserId = userId,
                    VehicleId = id,
                    DateBookmarked = DateTime.Now
                };

                _context.Bookmarks.Add(bookmark);
                _context.SaveChanges();

                return new BookmarkViewModel()
                {
                    BookmarksId = bookmark.BookmarksId,
                    DateBookmarked = bookmark.DateBookmarked
                };
            }

            return null;
        }

        public RatingsViewModel GetRatings(int vehicleId)
        {
            RatingsViewModel model = new RatingsViewModel()
            {
                ratings = _context.Ratings.Include(x => x.User).Where(x => x.VehicleId == vehicleId).Select(x => new RatingsViewModel.Ratings
                {
                    Comment = x.Comment,
                    DateReviewed = x.DateReviewed.ToString("dd-MM-yyyy"),
                    User = new Model.UserViewModel
                    {
                        FirstName = x.User.FirstName,
                        LastName = x.User.LastName,
                        Username = x.User.Username
                    },
                    RatingMark = x.RatingMark
                }).OrderByDescending(x => x.DateReviewed).ToList()
            };

            return model;
        }

        public void AddRating(RatingsViewModel.Ratings rating)
        {
            Rating ctxRating = _context.Ratings.FirstOrDefault(x => x.UserId == rating.UserId && x.VehicleId == rating.VehicleId);
            if (ctxRating != null)
            {
                ctxRating.RatingMark = rating.RatingMark;
                ctxRating.Comment = rating.Comment;
                ctxRating.DateReviewed = DateTime.Now;
            }
            else
            {
                ctxRating = new Rating();
                ctxRating.Comment = rating.Comment;
                ctxRating.DateReviewed = DateTime.Now;
                ctxRating.RatingMark = rating.RatingMark;
                ctxRating.UserId = rating.UserId;
                ctxRating.VehicleId = rating.VehicleId;
                _context.Ratings.Add(ctxRating);
            }
            _context.SaveChanges();
        }

        public MakesViewModel GetMakes()
        {
            MakesViewModel makes = new MakesViewModel
            {
                Makes = _context.Makes.Select(make => new MakesViewModel.Make
                {
                    MakeId = make.MakeId,
                    Name = make.Name
                }).OrderBy(make => make.Name).ToList()
            };
            return makes;
        }

        public ModelsViewModel GetModels(int makeId)
        {
            return new ModelsViewModel
            {
                Models = _context.Models.Where(x => x.MakeId == makeId).Select(x => new ModelsViewModel.Model
                {
                    ModelId = x.ModelId,
                    Name = x.Name
                }).OrderBy(x => x.Name).ToList()
            };
        }

        public VehicleViewModel InsertVehicle(InsertVehicleRequest vehicle)
        {
            Vehicle newVehicle = new Vehicle()
            {
                ABS = vehicle.ABS,
                AdditionalDescription = vehicle.AdditionalInformation,
                Climatisation = vehicle.Climatisation,
                DealershipId = 1,
                DoorNumber = vehicle.DoorNumber,
                FirstRegistration = vehicle.FirstRegistration,
                FuelType = vehicle.FuelType,
                Mileage = vehicle.Mileage,
                ModelId = vehicle.ModelId,
                Price = (decimal) vehicle.Price,
                Transmission = vehicle.Transmission,
                UserId = vehicle.UserId,
                TypeDescription = vehicle.TypeDescription
            };

            _context.Vehicles.Add(newVehicle);
            _context.SaveChanges();

            var image = Utilities.AddImageToDb(_context, vehicle.Image, newVehicle.VehicleId, false);

            return null;
        }

        public string ConvertImage(byte[] image)
        {
            return image != null ? Encoding.UTF8.GetString(image) : null;
        }

        public VehicleViewModel GetVehiclesOnSale(int userId)
        {
            VehicleViewModel model = new VehicleViewModel
            {
                vehicles = _context.Vehicles.Include(x => x.User).Include(x => x.Model).Include(x => x.Model.Make).Where(x => x.UserId == userId).Select(x => new VehicleViewModel.Vehicles()
                {
                    VehicleId = x.VehicleId,
                    ABS = x.ABS,
                    Climatisation = x.Climatisation,
                    DoorNumber = x.DoorNumber,
                    FirstRegistration = x.FirstRegistration,
                    TypeDescription = x.TypeDescription,
                    FuelType = x.FuelType,
                    Mileage = x.Mileage,
                    Image = ConvertImage(_context.Images.Where(y => y.VehicleId == x.VehicleId).FirstOrDefault().Img),
                    ModelId = x.ModelId,
                    Model = new Model.Models.Model { ModelId = x.ModelId, Name = x.Model.Name, Make = x.Model.Make },
                    Price = x.Price,
                    OwnerEmail = x.User.Email,
                    Transmission = x.Transmission
                }).ToList()
            };

            return model;
        }

        public VehicleViewModel RemoveVehicleOnSale(int vehicleId)
        {
            Vehicle vehicle = _context.Vehicles.FirstOrDefault(x => x.VehicleId == vehicleId);
            _context.Vehicles.Remove(vehicle);
            _context.SaveChanges();

            return GetVehiclesOnSale((int)vehicle.UserId);
        }

        public VehicleViewModel GetFavoriteVehicles(int userId)
        {
            return new VehicleViewModel()
            {
                vehicles = _context.Bookmarks.Include(x => x.Vehicle)
                .Where(x => x.UserId == userId)
                .Select(x => new VehicleViewModel.Vehicles
                {
                    ABS = x.Vehicle.ABS,
                    VehicleId = x.Vehicle.VehicleId,
                    Climatisation = x.Vehicle.Climatisation,
                    DoorNumber = x.Vehicle.DoorNumber,
                    FirstRegistration = x.Vehicle.FirstRegistration,
                    FuelType = x.Vehicle.FuelType,
                    Mileage = x.Vehicle.Mileage,
                    IsFavorite = true,
                    Model = new Model.Models.Model { Name = x.Vehicle.Model.Name, Make = x.Vehicle.Model.Make },
                    ModelId = (int)x.Vehicle.ModelId,
                    Price = x.Vehicle.Price,
                    AdditionalDescription = x.Vehicle.AdditionalDescription,
                    Transmission = x.Vehicle.Transmission,
                    OwnerEmail = x.User.Email,
                    Image = ConvertImage(_context.Images.Where(y => y.VehicleId == x.VehicleId).FirstOrDefault().Img),
                    TypeDescription = x.Vehicle.TypeDescription
                }).ToList()
            };
        }

        public VehicleViewModel SearchVehicle(SearchRequest searchParams)
        {
            var vehicles = _context.Vehicles.Include(x => x.User).Include(x => x.Model).Include(x => x.Model.Make).AsQueryable();

            vehicles = vehicles.Where(x => searchParams.ModelId != 0 ? x.ModelId == searchParams.ModelId : true
                            && x.FuelType == searchParams.FuelType
                            && int.Parse(x.FirstRegistration) >= searchParams.RegistrationFrom
                            && searchParams.RegistrationTo == 0 ? int.Parse(x.FirstRegistration) <= int.MaxValue : int.Parse(x.FirstRegistration) <= searchParams.RegistrationTo
                            && x.Price <= (decimal)searchParams.PriceTo
                            && searchParams.PriceTo == 0 ? x.Price <= int.MaxValue : x.Price <= (decimal)searchParams.PriceTo);

            return new VehicleViewModel()
            {
                vehicles = vehicles.Select(x => new VehicleViewModel.Vehicles()
                {
                    VehicleId = x.VehicleId,
                    ABS = x.ABS,
                    Climatisation = x.Climatisation,
                    DoorNumber = x.DoorNumber,
                    FirstRegistration = x.FirstRegistration,
                    TypeDescription = x.TypeDescription,
                    FuelType = x.FuelType,
                    Mileage = x.Mileage,
                    Image = ConvertImage(_context.Images.Where(y => y.VehicleId == x.VehicleId).FirstOrDefault().Img),
                    ModelId = x.ModelId,
                    Model = new Model.Models.Model { ModelId = x.ModelId, Name = x.Model.Name, Make = x.Model.Make },
                    Price = x.Price,
                    OwnerEmail = x.User.Email,
                    Transmission = x.Transmission
                }).ToList()
            };
        }
    }
}
