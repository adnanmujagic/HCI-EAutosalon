﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using eAutosalon.WebAPI.Database;

namespace eAutosalon.WebAPI.Migrations
{
    [DbContext(typeof(eAutosalonContext))]
    [Migration("20190623162139_AddedUserIdToImages")]
    partial class AddedUserIdToImages
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.1.8-servicing-32085")
                .HasAnnotation("Relational:MaxIdentifierLength", 128)
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("eAutosalon.Model.Models.Bookmarks", b =>
                {
                    b.Property<int>("BookmarksId")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<DateTime>("DateBookmarked");

                    b.Property<int?>("UserId");

                    b.Property<int?>("VehicleId");

                    b.HasKey("BookmarksId");

                    b.HasIndex("UserId");

                    b.HasIndex("VehicleId");

                    b.ToTable("Bookmarks");
                });

            modelBuilder.Entity("eAutosalon.Model.Models.City", b =>
                {
                    b.Property<int>("CityId")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<int?>("CountryId");

                    b.Property<string>("Name");

                    b.HasKey("CityId");

                    b.HasIndex("CountryId");

                    b.ToTable("Cities");
                });

            modelBuilder.Entity("eAutosalon.Model.Models.Country", b =>
                {
                    b.Property<int>("CountryId")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("Name");

                    b.HasKey("CountryId");

                    b.ToTable("Countries");
                });

            modelBuilder.Entity("eAutosalon.Model.Models.Dealership", b =>
                {
                    b.Property<int>("DealershipId")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("Address");

                    b.Property<int?>("CityId");

                    b.Property<string>("Name");

                    b.HasKey("DealershipId");

                    b.HasIndex("CityId");

                    b.ToTable("Dealerships");
                });

            modelBuilder.Entity("eAutosalon.Model.Models.Image", b =>
                {
                    b.Property<int>("ImageId")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<byte[]>("Img");

                    b.Property<int?>("UserId");

                    b.Property<int?>("VehicleId");

                    b.HasKey("ImageId");

                    b.HasIndex("UserId");

                    b.HasIndex("VehicleId");

                    b.ToTable("Images");
                });

            modelBuilder.Entity("eAutosalon.Model.Models.Make", b =>
                {
                    b.Property<int>("MakeId")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("Name");

                    b.HasKey("MakeId");

                    b.ToTable("Makes");
                });

            modelBuilder.Entity("eAutosalon.Model.Models.Model", b =>
                {
                    b.Property<int>("ModelId")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<int?>("MakeId");

                    b.Property<string>("Name");

                    b.HasKey("ModelId");

                    b.HasIndex("MakeId");

                    b.ToTable("Models");
                });

            modelBuilder.Entity("eAutosalon.Model.Models.Rating", b =>
                {
                    b.Property<int>("RatingId")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("Comment");

                    b.Property<DateTime>("DateReviewed");

                    b.Property<double>("RatingMark");

                    b.Property<int?>("UserId");

                    b.Property<int?>("VehicleId");

                    b.HasKey("RatingId");

                    b.HasIndex("UserId");

                    b.HasIndex("VehicleId");

                    b.ToTable("Ratings");
                });

            modelBuilder.Entity("eAutosalon.Model.Models.Role", b =>
                {
                    b.Property<int>("RoleId")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("Description");

                    b.Property<string>("Name");

                    b.HasKey("RoleId");

                    b.ToTable("Roles");
                });

            modelBuilder.Entity("eAutosalon.Model.Models.SoldCars", b =>
                {
                    b.Property<int>("SoldCarsId")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("PaymentMethod");

                    b.Property<decimal>("PriceSold");

                    b.Property<DateTime>("SellingDate");

                    b.Property<int?>("UserId");

                    b.Property<int?>("VehicleId");

                    b.HasKey("SoldCarsId");

                    b.HasIndex("UserId");

                    b.HasIndex("VehicleId");

                    b.ToTable("SoldCars");
                });

            modelBuilder.Entity("eAutosalon.Model.Models.User", b =>
                {
                    b.Property<int>("UserId")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("Active");

                    b.Property<string>("Address1");

                    b.Property<string>("Address2");

                    b.Property<int?>("CityId");

                    b.Property<string>("Email");

                    b.Property<string>("FirstName");

                    b.Property<string>("LastName");

                    b.Property<string>("PasswordHash");

                    b.Property<string>("PasswordSalt");

                    b.Property<string>("PhoneNumber");

                    b.Property<string>("Username");

                    b.HasKey("UserId");

                    b.HasIndex("CityId");

                    b.ToTable("Users");
                });

            modelBuilder.Entity("eAutosalon.Model.Models.UserRoles", b =>
                {
                    b.Property<int>("UserRolesId")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<DateTime>("ChangeDate");

                    b.Property<int?>("RoleId");

                    b.Property<int?>("UserId");

                    b.HasKey("UserRolesId");

                    b.HasIndex("RoleId");

                    b.HasIndex("UserId");

                    b.ToTable("UserRoles");
                });

            modelBuilder.Entity("eAutosalon.Model.Models.Vehicle", b =>
                {
                    b.Property<int>("VehicleId")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<bool>("ABS");

                    b.Property<bool>("Climatisation");

                    b.Property<int?>("DealershipId");

                    b.Property<string>("DoorNumber");

                    b.Property<string>("FirstRegistration");

                    b.Property<string>("FuelType");

                    b.Property<int>("Mileage");

                    b.Property<int?>("ModelId");

                    b.Property<decimal>("Price");

                    b.Property<string>("Transmission");

                    b.Property<string>("TypeDescription");

                    b.Property<int?>("UserId");

                    b.HasKey("VehicleId");

                    b.HasIndex("DealershipId");

                    b.HasIndex("ModelId");

                    b.HasIndex("UserId");

                    b.ToTable("Vehicles");
                });

            modelBuilder.Entity("eAutosalon.Model.Models.Bookmarks", b =>
                {
                    b.HasOne("eAutosalon.Model.Models.User", "User")
                        .WithMany()
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.SetNull);

                    b.HasOne("eAutosalon.Model.Models.Vehicle", "Vehicle")
                        .WithMany()
                        .HasForeignKey("VehicleId")
                        .OnDelete(DeleteBehavior.SetNull);
                });

            modelBuilder.Entity("eAutosalon.Model.Models.City", b =>
                {
                    b.HasOne("eAutosalon.Model.Models.Country", "Country")
                        .WithMany()
                        .HasForeignKey("CountryId")
                        .OnDelete(DeleteBehavior.SetNull);
                });

            modelBuilder.Entity("eAutosalon.Model.Models.Dealership", b =>
                {
                    b.HasOne("eAutosalon.Model.Models.City", "City")
                        .WithMany()
                        .HasForeignKey("CityId")
                        .OnDelete(DeleteBehavior.SetNull);
                });

            modelBuilder.Entity("eAutosalon.Model.Models.Image", b =>
                {
                    b.HasOne("eAutosalon.Model.Models.User", "User")
                        .WithMany()
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.SetNull);

                    b.HasOne("eAutosalon.Model.Models.Vehicle", "Vehicle")
                        .WithMany("VehicleImages")
                        .HasForeignKey("VehicleId")
                        .OnDelete(DeleteBehavior.SetNull);
                });

            modelBuilder.Entity("eAutosalon.Model.Models.Model", b =>
                {
                    b.HasOne("eAutosalon.Model.Models.Make", "Make")
                        .WithMany()
                        .HasForeignKey("MakeId")
                        .OnDelete(DeleteBehavior.SetNull);
                });

            modelBuilder.Entity("eAutosalon.Model.Models.Rating", b =>
                {
                    b.HasOne("eAutosalon.Model.Models.User", "User")
                        .WithMany()
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.SetNull);

                    b.HasOne("eAutosalon.Model.Models.Vehicle", "Vehicle")
                        .WithMany()
                        .HasForeignKey("VehicleId")
                        .OnDelete(DeleteBehavior.SetNull);
                });

            modelBuilder.Entity("eAutosalon.Model.Models.SoldCars", b =>
                {
                    b.HasOne("eAutosalon.Model.Models.User", "User")
                        .WithMany()
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.SetNull);

                    b.HasOne("eAutosalon.Model.Models.Vehicle", "Vehicle")
                        .WithMany()
                        .HasForeignKey("VehicleId")
                        .OnDelete(DeleteBehavior.SetNull);
                });

            modelBuilder.Entity("eAutosalon.Model.Models.User", b =>
                {
                    b.HasOne("eAutosalon.Model.Models.City", "City")
                        .WithMany()
                        .HasForeignKey("CityId")
                        .OnDelete(DeleteBehavior.SetNull);
                });

            modelBuilder.Entity("eAutosalon.Model.Models.UserRoles", b =>
                {
                    b.HasOne("eAutosalon.Model.Models.Role", "Role")
                        .WithMany()
                        .HasForeignKey("RoleId")
                        .OnDelete(DeleteBehavior.SetNull);

                    b.HasOne("eAutosalon.Model.Models.User", "User")
                        .WithMany("UserRoles")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.SetNull);
                });

            modelBuilder.Entity("eAutosalon.Model.Models.Vehicle", b =>
                {
                    b.HasOne("eAutosalon.Model.Models.Dealership", "Dealership")
                        .WithMany()
                        .HasForeignKey("DealershipId")
                        .OnDelete(DeleteBehavior.SetNull);

                    b.HasOne("eAutosalon.Model.Models.Model", "Model")
                        .WithMany()
                        .HasForeignKey("ModelId")
                        .OnDelete(DeleteBehavior.SetNull);

                    b.HasOne("eAutosalon.Model.Models.User", "User")
                        .WithMany("Vehicles")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.SetNull);
                });
#pragma warning restore 612, 618
        }
    }
}
