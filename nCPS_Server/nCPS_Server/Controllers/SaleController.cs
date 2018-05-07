using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Mvc;
using nCPS_Server.Models;

namespace nCPS_Server.Controllers
{
    [Produces("application/json")]
    [Route("api/Sale")]
    public class SaleController : Controller
    {
        private readonly SystemContext _context;

        public SaleController(SystemContext context)
        {
            _context = context;
        }

        // GET: api/values
        [HttpGet]
        public IEnumerable<Sale> Get()
        {
            return _context.Sales.ToList();
        }

        // GET api/values/5
        [HttpGet("{id}", Name = "GetSale")]
        public IActionResult Get(long id)
        {
            var item = _context.Sales.FirstOrDefault(t => t.SaleId == id);
            if (item == null)
            {
                return NotFound();
            }
            return new ObjectResult(item);
        }

        // POST api/values
        [HttpPost]
        public IActionResult Post([FromBody] Sale sale)
        {
            if (sale == null) return BadRequest();

            _context.Sales.Add(sale);
            _context.SaveChanges();

            return CreatedAtRoute("GetSale", new { id = sale.SaleId }, sale);
        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}