using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;

namespace nCPS_Server.Models
{
    public class SystemContext : DbContext
    {
        public SystemContext(DbContextOptions<SystemContext> options) : base(options)
        {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<VendingMachine>().ToTable("VendingMachines");
            modelBuilder.Entity<Sale>().ToTable("Sales");
            modelBuilder.Entity<Product>().ToTable("Products");
            modelBuilder.Entity<VendingMachine>().HasMany(i => i.Stock).WithOne();
            modelBuilder.Entity<VendingMachine>().HasMany(i => i.SalesHistory).WithOne();
        }

        public DbSet<VendingMachine> VendingMachines { get; set; }
        public DbSet<Sale> Sales { get; set; }
        public DbSet<Product> Products { get; set; }
    }
}
