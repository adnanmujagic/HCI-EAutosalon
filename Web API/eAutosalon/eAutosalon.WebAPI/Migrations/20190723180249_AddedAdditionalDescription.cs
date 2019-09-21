using Microsoft.EntityFrameworkCore.Migrations;

namespace eAutosalon.WebAPI.Migrations
{
    public partial class AddedAdditionalDescription : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "AdditionalDescription",
                table: "Vehicles",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "AdditionalDescription",
                table: "Vehicles");
        }
    }
}
