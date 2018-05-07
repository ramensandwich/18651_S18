using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;

namespace nCPS_Server.Models
{
    public class VendingMachine
    {
        [Key]
        public long MachineId { get; set; }
        public virtual ICollection<Product> Stock { get; set; }
        public virtual ICollection<Sale> SalesHistory { get; set; }
        public string Location { get; set; }
        public string Name { get; set; }
    }
}
