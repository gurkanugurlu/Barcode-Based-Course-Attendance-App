using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Deneme.Models
{
    public class LessonSender
    {
        public int lesson_id { get; set; }
        public string lesson_name { get; set; }
        public string lesson_onset_time { get; set; }
        public string lesson_expiration_time { get; set; }
    }
}