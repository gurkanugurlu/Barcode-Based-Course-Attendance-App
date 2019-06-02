using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Deneme.Models
{
    public class RandomSerialGenerator
    {

        public static Guid generator()
        {
            Guid guid = Guid.NewGuid();
            return guid;
        }
    }
}