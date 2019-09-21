using System;
using System.Collections.Generic;
using System.Windows.Forms;
using eAutosalon.Model;
using eAutosalon.Model.Requests;

namespace eAutosalon.WinUI
{
    public partial class frmUsers : Form
    {
        //https://localhost:44397/api/users
        private readonly APIService _apiService = new APIService("users");
        public frmUsers()
        {
            InitializeComponent();
        }

        private async void btnShowUsers_Click(object sender, EventArgs e)
        {

            var searchQuery = new KorisniciSearchRequest()
            {
                FirstName = txtSearchUsers.Text
            };

            var result = await _apiService.Get<List<UserViewModel>>(searchQuery);

            dgvUsers.AutoGenerateColumns = false;
            dgvUsers.DataSource = result;
        }

        private void dgvUsers_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            var id = dgvUsers.SelectedRows[0].Cells[0].Value;

            frmUserDetails form = new frmUserDetails(int.Parse(id.ToString()));
            form.Show();
        }
    }
}
