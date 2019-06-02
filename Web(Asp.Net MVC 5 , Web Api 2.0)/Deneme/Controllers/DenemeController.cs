using Deneme.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Deneme.Controllers
{
    [RoutePrefix("api/deneme")]
    public class DenemeController : ApiController
    {
        private barkode_projectEntities barkode_ProjectEntities = new barkode_projectEntities();
        private HttpResponseMessage message;

        //public HttpResponseMessage Get(){ message = Request.CreateResponse(HttpStatusCode.OK, student2);}
        //[Route("")]
        //public student Get()
        //{
        //    student student = barkode_ProjectEntities.student.Where(x => x.student_id == 141001006).SingleOrDefault();
        //    return student;

        //}

        [HttpGet]
        [Route("lesson/{id}")]
        public HttpResponseMessage Get(int id)
        {
            List<student_lesson> student_Lessons = barkode_ProjectEntities.student_lesson.Where(x => x.student_id == id).ToList();
            List<LessonSender> lessonSenders = new List<LessonSender>();
            foreach (student_lesson st in student_Lessons)
            {
                lesson lesson = barkode_ProjectEntities.lesson.Where(x => x.lesson_id == st.lesson_id).FirstOrDefault();
                LessonSender lessonSender = new LessonSender()
                {
                    lesson_id = lesson.lesson_id,
                    lesson_expiration_time = lesson.lesson_expiration_time.ToString(),
                    lesson_name = lesson.lesson_name,
                    lesson_onset_time = lesson.lesson_onset_time.ToString()

                };
                lessonSenders.Add(lessonSender);
            }
            if (lessonSenders != null)
            {
                message = Request.CreateResponse(HttpStatusCode.OK, lessonSenders);
                return message;
            }
            message = Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Ders Bulunamadi");
            return message;
        }
        [Route("student")]
        public HttpResponseMessage Post(student student)
        {
            student student2 = barkode_ProjectEntities.student.Where(x => x.student_id == student.student_id && x.student_password == student.student_password).SingleOrDefault();
            if (student2 != null)
            {
                StudentSender studentSender = new StudentSender()
                {
                    student_id = student2.student_id,
                    student_name = student2.student_name,
                    student_password = student2.student_password,
                    student_surname = student2.student_surname
                };
                message = Request.CreateResponse(HttpStatusCode.OK, studentSender);
                return message;
                //return student2;
            }
            message = Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Kullanici Bulunamadi");

            return message;

        }
        [Route("barcode")]
        public HttpResponseMessage Post(StudentPackage studentPackage)
        {
            DateTime dateTime = DateTime.Now;
            int timeNow = (dateTime.Hour*60)+ dateTime.Minute;
            session session = barkode_ProjectEntities.session.Where(x => x.session_content == studentPackage.content).FirstOrDefault();
            if (session != null)
            {
                DateTime dt =(DateTime) session.session_expiration_time;
                int sessionTime= (dt.Hour * 60) + dt.Minute;
                if (timeNow-sessionTime > 0)
                {
                    message = Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Session Kayıt Basarisiz");
                    return message;
                }
                student student2 = barkode_ProjectEntities.student.Where(x => x.student_id == studentPackage.student.student_id).SingleOrDefault();
                if (student2 != null)
                {
                    List<student_lesson> student_Lesson = barkode_ProjectEntities.student_lesson.Where(x => x.student_id == student2.student_id).ToList();
                    foreach(var item in student_Lesson)
                    {
                        if (item.lesson_id == session.lesson_id)
                        {
                            break;
                        }
                       
                    }


                    
                    history history = barkode_ProjectEntities.history.Where(x => x.student_id == student2.student_id && x.session_id == session.session_id).FirstOrDefault();
                    if (history == null)
                    {
                        history = new history();
                        history.session = session;
                        history.student = student2;
                        barkode_ProjectEntities.history.Add(history);
                        barkode_ProjectEntities.SaveChanges();
                        message = Request.CreateResponse(HttpStatusCode.OK, student2);
                        return message;

                        //return student2;
                    }
                    message = Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Oturumda Daha Önce Kayıt Oldunuz.");

                    

                }
                message = Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Session Kayıt Basarisiz");

               

            }
            return message;

        }

            
        [HttpPost]
        [Route("history/{id}")]
        public HttpResponseMessage Post(int id)
        {
            List<history> history = barkode_ProjectEntities.history.Where(x => x.student_id == id).ToList();
            List<HistorySender> historySenders = new List<HistorySender>();
            foreach ( var h  in history)
            {
                session session = barkode_ProjectEntities.session.Where(x => x.session_id == h.session_id).FirstOrDefault();
                HistorySender historySender = new HistorySender()
                {
                    id=session.session_id,
                    lesson_name=session.lesson.lesson_name,
                    session_onset_time=session.session_onset_time.ToString(),
                    session_expiration_time=session.session_expiration_time.ToString()
              
                };
                historySenders.Add(historySender);
            }
            if (historySenders != null)
            {
                message = Request.CreateResponse(HttpStatusCode.OK, historySenders);
                return message;
            }
            message = Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Ders Bulunamadi");
            return message;

        }

        [HttpPut]
        [Route("")]
        public HttpResponseMessage Put(PassChange passChange)
        {
            student student = barkode_ProjectEntities.student.Where(x => x.student_id == passChange.id).FirstOrDefault();
            student.student_password = passChange.password;
            if (student != null)
            {
                barkode_ProjectEntities.SaveChanges();
                message = Request.CreateResponse(HttpStatusCode.OK, passChange);
                return message;
            }
            message = Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Ders Bulunamadi");
            return message;


        }

        [HttpPost]
        [Route("rate")]
        public HttpResponseMessage rate(rate rate)
        {
            barkode_ProjectEntities.rate.Add(rate);
            barkode_ProjectEntities.SaveChanges();
            message = Request.CreateResponse(HttpStatusCode.OK, rate);
            return message;
        }
    }
}

