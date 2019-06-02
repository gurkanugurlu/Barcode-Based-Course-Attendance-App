using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Deneme.Models
{
    public class HistorySender
    {
        public int id { get; set; }
        public string lesson_name { get; set; }
        public string session_onset_time { get; set; }
        public string session_expiration_time { get; set; }
    }
}