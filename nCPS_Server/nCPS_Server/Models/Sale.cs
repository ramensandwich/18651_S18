using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace nCPS_Server.Models
{
    public enum PaymentType
    {
        Cash,
        Credit,
        App
    };

    public enum SaleType
    {
        Standard, //No discount
        WebPromo, //Distributor promotion
        StockPromo //Trying to dump stock before it goes bad
    }
    public class Sale
    {
        public long MachineId { get; set; }             //Id of machine that made the sale
        public long ProductId { get; set; }             //Id of the product sold
        public long SaleId { get; set; }                //Unique ID of this sale
        public DateTime TimeOfSale { get; set; }        //Time of sale
        public PaymentType PaymentType { get; set; }    //
        public SaleType SaleType { get; set; }
        public double SalePrice { get; set; }           //How much product was sold for
    }
}
