﻿using Flurl.Http;
using Flurl;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using eAutosalon.Model;

namespace eAutosalon.WinUI
{
    public class APIService
    {
        private string _route = null;
        public APIService(string route)
        {
            _route = route;
        }

        public async Task<T> Get<T>(object search)
        {
            var url = $"{Properties.Settings.Default.APIUrl}/{_route}";

            if(search != null)
            {
                url += "?";
                url += await search.ToQueryString();
            }

            var result = await url.GetJsonAsync<T>();

            return result;
        }

        public async Task<T> GetById<T>(object id)
        {
            var url = $"{Properties.Settings.Default.APIUrl}/{_route}/{id}";

            return await url.GetJsonAsync<T>();
        }

        public async Task<T> Insert<T>(object request)
        {
            var url = $"{Properties.Settings.Default.APIUrl}/{_route}";

            return await url.PostJsonAsync(request).ReceiveJson<T>();
        }

        public async Task<T> Update<T>(object id, object request)
        {
            var url = $"{Properties.Settings.Default.APIUrl}/{_route}/{id}";

            return await url.PutJsonAsync(request).ReceiveJson<T>();
        }
    }
}
