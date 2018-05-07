using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;

namespace nCPS_Server.Models
{
    public class SaleContext : DbContext
    {
        public SaleContext(DbContextOptions<ProductContext> options) : base(options)
        {
        }

        public DbSet<Product> Sales { get; set; }
    }
}
