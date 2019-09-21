using eAutosalon.Model.Models;
using eAutosalon.WebAPI.Database;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace eAutosalon.WebAPI.Helpers
{
    public static class Utilities
    {
        public static Image AddImageToDb(eAutosalonContext _context, string image, int id, bool isUser)
        {
            var byteImage = System.Text.Encoding.UTF8.GetBytes(image);

            Image dbImage;

            if(isUser)
                dbImage = _context.Images.FirstOrDefault(x => x.UserId == id);
            else
                dbImage = _context.Images.FirstOrDefault(x => x.VehicleId == id);

            if (dbImage != null)
            {
                dbImage.Img = byteImage;
            }
            else
            {
                dbImage = new Image
                {
                    UserId = isUser ? id : (int?)null,
                    VehicleId = isUser ? (int?)null : id,
                    Img = byteImage
                };

                _context.Images.Add(dbImage);
            }
            _context.SaveChanges();

            return dbImage;
        }

    }
}
