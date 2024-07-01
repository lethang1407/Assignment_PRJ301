package controller.exam;

import controller.auth.BaseRequiredLecturerAuthenticationController;
import dal.ExamDBContext;
import dal.GradeDBContext;
import dal.StudentDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import model.Exam;
import model.Grade;
import model.Lecturer;
import model.Student;
import model.Lecturer_Account;

public class TakeExamController extends BaseRequiredLecturerAuthenticationController {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, Lecturer_Account user, Lecturer lecturer)
            throws ServletException, IOException {
        StudentDBContext stuDB = new StudentDBContext();
        ExamDBContext examDB = new ExamDBContext();
        GradeDBContext graDB = new GradeDBContext();

        int cid = Integer.parseInt(request.getParameter("cid"));
        ArrayList<Student> students = stuDB.getStudentsByCourse(cid);

        String[] raw_eids = request.getParameterValues("eid");
        ArrayList<Integer> eids = new ArrayList<>();
        for (String raw_eid : raw_eids) {
            eids.add(Integer.valueOf(raw_eid));
        }

        ArrayList<Exam> exams = examDB.getExamsByExamIds(eids);
        ArrayList<Grade> grades = graDB.getGradesFromExamIds(eids);

        request.setAttribute("students", students);
        request.setAttribute("exams", exams);
        request.setAttribute("grades", grades);

        request.getRequestDispatcher("../View/exam/take.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, Lecturer_Account user, Lecturer lecturer)
            throws ServletException, IOException {
        int cid = Integer.parseInt(request.getParameter("cid"));
        HashSet<Integer> eids = new HashSet<>();

        String[] raw_gradeids = request.getParameterValues("gradeid");
        ArrayList<Grade> grades = new ArrayList<>();
        for (String raw_gradeid : raw_gradeids) {
            int sid = Integer.parseInt(raw_gradeid.split("_")[0]);
            int eid = Integer.parseInt(raw_gradeid.split("_")[1]);

            eids.add(eid);

            String raw_score = request.getParameter("score" + sid + "_" + eid);
            if (raw_score.length() > 0) {
                Grade g = new Grade();
                Exam e = new Exam();
                e.setId(eid);

                Student s = new Student();
                s.setId(sid);

                g.setExam(e);
                g.setStudent(s);
                g.setScore(Float.parseFloat(raw_score));

                grades.add(g);
            }
        }

        GradeDBContext db = new GradeDBContext();
        db.insertGradesForCourse(cid, grades);
        String url_param = "";
        for (Integer eid : eids) {
            url_param += "&eid=" + eid;
        }
        response.sendRedirect("take?cid=" + cid + url_param);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
