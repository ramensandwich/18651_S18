using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using nCPS_Server.Models;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace nCPS_Server.Controllers
{
    [Route("api/VendingMachine")]
    public class VendingMachineController : Controller
    {
        private readonly SystemContext _context;

        public VendingMachineController(SystemContext context)
        {
            _context = context;
        }

        // GET: api/values
        [HttpGet]
        public IEnumerable<VendingMachine> Get()
        {
            List<VendingMachine> machineList = _context.VendingMachines.ToList();
            foreach (VendingMachine v in machineList)
            {
                List<Product> products = _context.Products.Where(p => p.MachineId == v.MachineId).ToList<Product>();
                List<Sale> sales = _context.Sales.Where(s => s.MachineId == v.MachineId).ToList<Sale>();

                v.Stock = products;
                v.SalesHistory = sales;
            }

            return machineList;
        }

        // GET api/values/5
        [HttpGet("{id}", Name = "GetVendingMachine")]
        public IActionResult Get(long id)
        {
            //Load the requested machine
            VendingMachine machine = _context.VendingMachines.FirstOrDefault(t => t.MachineId == id);
            if (machine == null)
            {
                return NotFound();
            }

            //Load the machine's current stock
            Product[] products = _context.Products.Where(p => p.MachineId == id).ToArray<Product>();
            machine.Stock = products;

            //TODO: Want to load sales history? Can be a *lot* of data...
            Sale[] sales = _context.Sales.Where(s => s.MachineId == id).ToArray<Sale>();
            machine.SalesHistory = sales;

            return new ObjectResult(machine);
        }

        // POST api/values
        [HttpPost]
        public IActionResult Post([FromBody] VendingMachine vendingMachine)
        {
            if (vendingMachine == null) return BadRequest();

            _context.VendingMachines.Add(vendingMachine);

            foreach (Sale sale in vendingMachine.SalesHistory)
            {
                sale.MachineId = vendingMachine.MachineId;
                _context.Sales.Add(sale);
            }
            _context.SaveChanges();

            return CreatedAtRoute("GetVendingMachine", new { id = vendingMachine.MachineId }, vendingMachine);
        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody]string value)
        {
            //This is for updating vending machines, but we don't really make real-time changes to it.
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
            //We don't delete vending machines in our sim, so we don't implement this.
        }
    }
}
