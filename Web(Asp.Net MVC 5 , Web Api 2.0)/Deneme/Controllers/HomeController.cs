using Deneme.Models;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Web;
using System.Web.Mvc;
namespace Deneme.Controllers
{
    public class HomeController : Controller
    {
        private barkode_projectEntities db = new barkode_projectEntities();
        // GET: Home
        public ActionResult Index(string text)
        {
            QrCode qrCode = new QrCode();
            qrCode.qrCodeGenerate(text);
            return View(qrCode);
        }
        [HttpGet]
        public ActionResult Login()
        {
            return View();
        }
        [HttpPost]
        public ActionResult Login(string mail, string password)
        {
            teacher teacher = db.teacher.Where(X => X.teacher_login_id == mail && X.teacher_login_password == password).FirstOrDefault();
            if (teacher != null)
            {
                Session["user"] = teacher;
                return RedirectToAction("index","admin");
            }
            
            return RedirectToAction("login");
        }
       
    }
}