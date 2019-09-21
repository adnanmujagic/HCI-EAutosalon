using AutoMapper;
using eAutosalon.WebAPI.Database;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace eAutosalon.WebAPI.Services
{
    public class BaseService<TModel, TDatabaseModel> : IService<TModel> where TDatabaseModel: class
    {
        private readonly eAutosalonContext _context;
        private readonly IMapper _mapper;

        public BaseService(eAutosalonContext context, IMapper mapper)
        {
            _context = context;
            _mapper = mapper;
        }

        public virtual List<TModel> Get()
        {
            var dataList = _context.Set<TDatabaseModel>().Take(5);

            return _mapper.Map<List<TModel>>(dataList.ToList());
        }

        public virtual TModel GetById(int id)
        {
            var entry = _context.Set<TDatabaseModel>().Find(id);
            var mapped = _mapper.Map<TModel>(entry);
            return mapped;
        }
    }
}
