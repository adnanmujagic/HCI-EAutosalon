using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace eAutosalon.WinUI
{
    public partial class frmUserDetails : Form
    {
        private readonly APIService _apiService = new APIService("users");
        private int? _userId = null;
        public frmUserDetails(int? id = null)
        {
            InitializeComponent();
            _userId = id;
        }

        private async void frmUserDetails_Load(object sender, EventArgs e)
        {
            if (_userId.HasValue)
            {
                var result = await _apiService.GetById<Model.UserViewModel>(_userId);

                txtIme.Text = result.Username;
                txtPrezime.Text = result.LastName;
                txtEmail.Text = result.Email;
                txtTelefon.Text = result.PhoneNumber;
                txtUsername.Text = result.Username;
            }
        }

        private async void btnSave_Click(object sender, EventArgs e)
        {
            var request = new Model.Requests.UserInsertRequest()
            {
                FirstName = txtIme.Text,
                LastName = txtPrezime.Text,
                Email = txtEmail.Text,
                PhoneNumber = txtTelefon.Text,
                Username = txtUsername.Text
            };

            if (_userId.HasValue)
            {
                await _apiService.Update<Model.Requests.UserInsertRequest>(_userId, request);
                MessageBox.Show("User successfully updated!");
            }
            else
            {
                await _apiService.Insert<Model.Requests.UserInsertRequest>(request);
                MessageBox.Show("User successfully added!");
            }
        }
    }
}
