using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace nCPS_Server.Models
{
    public class Product
    {
        public long ProductId { get; set; }
        public long? MachineId { get; set; }
        public string Name { get; set; }
        public TimeSpan ShelfLife { get; set; }
        public double Cost { get; set; }
    }
}
