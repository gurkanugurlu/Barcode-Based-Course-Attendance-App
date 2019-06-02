using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Deneme.Models;

namespace Deneme.Controllers
{
    public class AdminController : Controller
    {
       private barkode_projectEntities db = new barkode_projectEntities();
        // GET: Admin
        public ActionResult Index()
        {
            List<student> students = db.student.ToList();
            return View();
        }
        public ActionResult Students()
        {
            List<student> students = db.student.ToList();
            return View(students);
        }
        
        public ActionResult Lessons()
        {
            List<lesson> lessons = db.lesson.ToList();
            return View(lessons);

        }
        [HttpGet]
        public ActionResult UserInf(int? id)
        {
          
            List<student_lesson> students_lessons = db.student_lesson.Where(x => x.lesson_id == id).ToList();
            List<student> students = new List<student>();

            foreach(var item in students_lessons)
            {
                student student = db.student.Where(x => x.student_id == item.student_id).FirstOrDefault();
                students.Add(student);
            }
            return View(students);
        }
        [HttpGet]
        public ActionResult Barcode()
        {
         teacher teacher=(teacher) Session["user"];
            List<lesson> lessons = db.lesson.Where(x => x.teacher_id == teacher.teacher_id).ToList();
            ViewBag.lessons = new SelectList(lessons, "lesson_id", "lesson_name");
            session session = new session();
            
            return View(session);
        }
        [HttpPost]
        public ActionResult Barcode(session session)
        {
            string text = RandomSerialGenerator.generator().ToString();
            barcode barcode = new barcode();
            session.session_content = text;
            barcode.barcode_content = text;
            session.session_onset_time = DateTime.Now;
            session.session_expiration_time = DateTime.Now.AddMinutes(5);
            lesson lesson = db.lesson.Where(x => x.lesson_id == session.lesson_id).FirstOrDefault();
            session.lesson = lesson;
            db.barcode.Add(barcode);
            session.barcode = barcode;
            db.session.Add(session);
            db.SaveChanges();
            return RedirectToAction("index", "home", new { @text = text });
        }
        public ActionResult Sessions()
        {
            List<session> sessions = db.session.ToList();
            return View(sessions);
        }
        public ActionResult SessionInf(int id)
        {
            List<history> histories = db.history.Where(x => x.session_id == id).ToList();
            List<student> students = new List<student>();
            foreach(var h in histories)
            {
                student student = db.student.Where(x => x.student_id == h.student_id).FirstOrDefault();
                students.Add(student);
            }
            return View(students);
        }
        public ActionResult BarcodeFinder(int id)
        {
            id--;
            session session = db.session.Where(x => x.barcode_id == id).FirstOrDefault();
            if (session != null)
            {
                string text = session.session_content;
                return RedirectToAction("Index", "Home", new { @text = text });
            }
            return RedirectToAction("Index", "Admin");
        }

    }
}