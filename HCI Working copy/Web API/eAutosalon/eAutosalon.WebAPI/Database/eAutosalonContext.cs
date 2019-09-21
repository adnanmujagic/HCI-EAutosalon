using eAutosalon.Model.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace eAutosalon.WebAPI.Database
{
    public partial class eAutosalonContext: DbContext
    {
        public eAutosalonContext()
        {
        }

        public eAutosalonContext(DbContextOptions<eAutosalonContext> options) : base(options)
        {
        }


        public virtual DbSet<Bookmarks> Bookmarks { get; set; }
        public virtual DbSet<City> Cities { get; set; }
        public virtual DbSet<Country> Countries { get; set; }
        public virtual DbSet<Dealership> Dealerships { get; set; }
        public virtual DbSet<Make> Makes { get; set; }
        public virtual DbSet<eAutosalon.Model.Models.Model> Models { get; set; }
        public virtual DbSet<Rating> Ratings { get; set; }
        public virtual DbSet<Role> Roles { get; set; }
        public virtual DbSet<SoldCars> SoldCars { get; set; }
        public virtual DbSet<User> Users { get; set; }
        public virtual DbSet<UserRoles> UserRoles { get; set; }
        public virtual DbSet<Vehicle> Vehicles { get; set; }
        public virtual DbSet<Image> Images { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            foreach (var relationship in modelBuilder.Model.GetEntityTypes().SelectMany(e => e.GetForeignKeys()))
            {
                relationship.DeleteBehavior = DeleteBehavior.SetNull;
            }
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                //optionsBuilder.UseSqlServer("Server=localhost;Database=eAutosalon;Trusted_Connection=True;");
                optionsBuilder.UseSqlServer("Server=s81.wrd.app.fit.ba;Database=eAutosalon;Trusted_Connection=False; User ID=eAutosalon; Password=@pa4jL31;ConnectRetryCount=0");
            }
        }
    }
}
