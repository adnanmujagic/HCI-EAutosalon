using AutoMapper;
using eAutosalon.Model;
using eAutosalon.Model.Models;
using eAutosalon.Model.Requests;
using eAutosalon.Model.ViewModels;
using eAutosalon.WebAPI.Database;
using eAutosalon.WebAPI.Exceptions;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace eAutosalon.WebAPI.Services
{
    public class UsersService : BaseService<UserViewModel, Model.Models.User>, IUsersService<UserViewModel>
    {
        private readonly eAutosalonContext _context;
        private readonly IMapper _mapper;

        public UsersService(eAutosalonContext context, IMapper mapper): base(context, mapper)
        {
            _context = context;
            _mapper = mapper;
        }

        public override List<UserViewModel> Get()
        {
            var userList = _context.Users.Take(5).AsQueryable();

            return _mapper.Map<List<UserViewModel>>(userList.ToList());
        }

        public override UserViewModel GetById(int id)
        {
            Model.Models.User user = _context.Users.Include(x => x.City.Country).FirstOrDefault(x => x.UserId == id);

            if (user == null)
                return null;

            UserViewModel model = new UserViewModel()
            {
                UserId = user.UserId,
                City = user.City,
                Address1 = user.Address1,
                Address2 = user.Address2,
                CityId = user.CityId,
                Email = user.Email,
                FirstName = user.FirstName,
                LastName = user.LastName,
                PhoneNumber = user.PhoneNumber,
                Username = user.Username
            };
            return model;
        }

        public List<UserViewModel> GetBySearch(KorisniciSearchRequest request)
        {
            var userList = _context.Users.Take(5).AsQueryable();

            if (!string.IsNullOrEmpty(request.FirstName))
            {
                userList = userList.Where(user => user.FirstName.StartsWith(request.FirstName));
            }
            if (!string.IsNullOrEmpty(request.LastName))
            {
                userList = userList.Where(user => user.LastName.StartsWith(request.LastName));
            }

            return _mapper.Map<List<UserViewModel>>(userList.ToList());
        }

        public UserViewModel Insert(UserInsertRequest request)
        {
            if (request.Password == request.ConfirmPassword)
            {
                var newKorisnik = _mapper.Map<Model.Models.User>(request);

                newKorisnik.PasswordSalt = GenerateSalt();
                newKorisnik.PasswordHash = GenerateHash(newKorisnik.PasswordSalt, request.Password);

                _context.Users.Add(newKorisnik);
                _context.SaveChanges();

                return _mapper.Map<UserViewModel>(newKorisnik);
            }
            throw new UserException("The passwords do not match.");
        }

        //public UserViewModel Insert(UserInsertRequest request)
        //{
        //    if (request.Password == request.ConfirmPassword)
        //    {
        //        var newKorisnik = _mapper.Map<Model.Models.User>(request);

        //        newKorisnik.PasswordSalt = GenerateSalt();
        //        newKorisnik.PasswordHash = GenerateHash(newKorisnik.PasswordSalt, request.Password);

        //        _context.Users.Add(newKorisnik);
        //        _context.SaveChanges();

        //        return _mapper.Map<UserViewModel>(newKorisnik);
        //    }
        //    throw new UserException("The passwords do not match.");
        //}

        public UserViewModel Update(int id, UserInsertRequest request)
        {
            var user = _context.Users.Include(x => x.City.Country).FirstOrDefault(x => x.UserId == id);
            Image dbImage = null;

            _mapper.Map(request, user);
            user.PasswordSalt = GenerateSalt();
            user.PasswordHash = GenerateHash(user.PasswordSalt, request.Password);

            if (!String.IsNullOrEmpty(request.Image))
            {
                dbImage = AddImageToDb(request.Image, id);
            }

            _context.SaveChanges();

            return new UserViewModel
            {
                Address1 = user.Address1,
                City = user.City,
                Email = user.Email,
                FirstName = user.FirstName,
                LastName = user.LastName,
                UserId = user.UserId,
                PhoneNumber = user.PhoneNumber,
                Username = user.Username,
                Image = dbImage != null ? Encoding.UTF8.GetString(dbImage.Img) : null
            };

            //return _mapper.Map<UserViewModel>(user);
            //TODO: Check passwords
        }

        public Image AddImageToDb(string image, int id)
        {
            var byteImage = System.Text.Encoding.UTF8.GetBytes(image);

            Image dbImage = _context.Images.FirstOrDefault(x => x.UserId == id);
            
            if(dbImage != null)
            {
                dbImage.Img = byteImage;
            }
            else
            {
                dbImage = new Image
                {
                    UserId = id,
                    Img = byteImage
                };
                _context.Images.Add(dbImage);
            }
            _context.SaveChanges();

            return dbImage;
        }

        //public UserViewModel Update(int id, UserInsertRequest request)
        //{
        //    var user = _context.Users.Find(id);

        //    _mapper.Map(request, user);

        //    _context.SaveChanges();

        //    return _mapper.Map<UserViewModel>(user);
        //    //TODO: Check passwords
        //}

        public UserViewModel Authenticiraj(string username, string password)
        {
            var user = _context.Users.Include("UserRoles.Role").Include(x => x.City.Country).FirstOrDefault(usr => usr.Username == username);
            if (user != null)
            {
                var newHash = GenerateHash(user.PasswordSalt, password);
                if (newHash == user.PasswordHash)
                {
                    UserViewModel model = new UserViewModel()
                    {
                        UserId = user.UserId,
                        City = user.City,
                        Address1 = user.Address1,
                        Address2 = user.Address2,
                        Email = user.Email,
                        FirstName = user.FirstName,
                        LastName = user.LastName,
                        PhoneNumber = user.PhoneNumber,
                        Username = user.Username
                    };

                    return model;
                }
            }
            return null;
        }

        //public UserViewModel Authenticiraj(string username, string password)
        //{
        //    var usr = _context.Users.Include("UserRoles.Role").FirstOrDefault(user => user.Username == username);
        //    if (usr != null)
        //    {
        //        var newHash = GenerateHash(usr.PasswordSalt, password);
        //        if (newHash == usr.PasswordHash)
        //            return _mapper.Map<UserViewModel>(usr);
        //    }
        //    return null;
        //}

        private static string GenerateSalt()
        {
            var buf = new byte[16];
            (new RNGCryptoServiceProvider()).GetBytes(buf);
            return Convert.ToBase64String(buf);
        }

        private static string GenerateHash(string salt, string password)
        {
            byte[] src = Convert.FromBase64String(salt);
            byte[] bytes = Encoding.Unicode.GetBytes(password);
            byte[] dst = new byte[src.Length + bytes.Length];

            System.Buffer.BlockCopy(src, 0, dst, 0, src.Length);
            System.Buffer.BlockCopy(bytes, 0, dst, src.Length, bytes.Length);

            HashAlgorithm algorithm = HashAlgorithm.Create("SHA1");
            byte[] inArray = algorithm.ComputeHash(dst);
            return Convert.ToBase64String(inArray);
        }

        public CitiesViewModel GetCities()
        {
            return new CitiesViewModel()
            {
                Cities = _context.Cities.Include(x => x.Country).Select(x => new CitiesViewModel.City
                {
                    CityId = x.CityId,
                    Name = x.Name,
                    Country = x.Country
                }).ToList()
            };
        }
    }
}
