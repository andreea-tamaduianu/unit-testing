package com.luv2code.springmvc.service;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.repository.HistoryGradesDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradesDao;
import com.luv2code.springmvc.repository.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    @Qualifier("mathGrades")
    private MathGrade mathGrade;

    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    @Qualifier("scienceGrades")
    private ScienceGrade scienceGrade;

    @Autowired
    private ScienceGradesDao scienceGradesDao;

    @Autowired
    @Qualifier("historyGrades")
    private HistoryGrade historyGrade;

    @Autowired
    private HistoryGradesDao historyGradesDao;

    @Autowired
    private StudentGrades studentGrades;


    public void createStudent(String firstName, String lastName, String email){
        CollegeStudent collegeStudent = new CollegeStudent(firstName,lastName,email);
        collegeStudent.setId(0);
        studentDAO.save(collegeStudent);
    }

    public boolean checkIfStudentIsNull(int i) {
        Optional<CollegeStudent> student = studentDAO.findById(i);
        if (student.isPresent()){
            return true;
        } else{
            return false;
        }
    }

    public void deleteStudent(int i) {
        if(checkIfStudentIsNull(i)){
            studentDAO.deleteById(i);
            mathGradesDao.deleteByStudentId(i);
            scienceGradesDao.deleteByStudentId(i);
            historyGradesDao.deleteByStudentId(i);
        }

    }

    public Iterable<CollegeStudent> getGradebook() {
        return studentDAO.findAll();
    }

    public boolean createGrade(double grade, int studentId, String gradeType){
        if(!checkIfStudentIsNull(studentId)){
            return false;
        }
        if(grade>= 0 && grade<=100){
            if(gradeType.equals("math")){
                mathGrade.setId(0);
                mathGrade.setGrade(grade);
                mathGrade.setStudentId(studentId);
                mathGradesDao.save(mathGrade);
                return true;
            }
            if(gradeType.equals("science")){
                scienceGrade.setId(0);
                scienceGrade.setGrade(grade);
                scienceGrade.setStudentId(studentId);
                scienceGradesDao.save(scienceGrade);
                return true;
            }
            if(gradeType.equals("history")){
                historyGrade.setId(0);
                historyGrade.setGrade(grade);
                historyGrade.setStudentId(studentId);
                historyGradesDao.save(historyGrade);
                return true;
            }

        }
        return false;
    }

    public int deleteGrade(int id, String gradeType) {
        int studentId=0;
        if(gradeType.equals("math")){
            Optional<MathGrade> grade = mathGradesDao.findById(id);
            if(!grade.isPresent()){
                return studentId;
            }
            studentId=grade.get().getStudentId();
            mathGradesDao.deleteById(id);
        }
        if(gradeType.equals("history")){
            Optional<HistoryGrade> grade = historyGradesDao.findById(id);
            if(!grade.isPresent()){
                return studentId;
            }
            studentId=grade.get().getStudentId();
            historyGradesDao.deleteById(id);
        }
        if(gradeType.equals("science")){
            Optional<ScienceGrade> grade =scienceGradesDao.findById(id);
            if(!grade.isPresent()){
                return studentId;
            }
            studentId=grade.get().getStudentId();
            scienceGradesDao.deleteById(id);
        }
        return studentId;
    }

    public GradebookCollegeStudent studentInformation(int id) {
        Optional<CollegeStudent> student = studentDAO.findById(id);

        if(!student.isPresent()){
            return null;
        }

        Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(id);
        Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradeByStudentId(id);
        Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradeByStudentId(id);

        List<Grade> mathGradesList = new ArrayList<>();
        mathGrades.forEach(mathGradesList::add);

        List<Grade> scienceGradesList = new ArrayList<>();
        scienceGrades.forEach(scienceGradesList::add);

        List<Grade> historyGradesList = new ArrayList<>();
        historyGrades.forEach(historyGradesList::add);

        studentGrades.setMathGradeResults(mathGradesList);
        studentGrades.setScienceGradeResults(scienceGradesList);
        studentGrades.setHistoryGradeResults(historyGradesList);

        GradebookCollegeStudent gradebookCollegeStudent = new GradebookCollegeStudent(student.get().getId(),student.get().getFirstname(),student.get().getLastname(),student.get().getEmailAddress(),studentGrades);
        return gradebookCollegeStudent;


    }

    public void configureStudentInformationModel(int studentId, Model model){
        GradebookCollegeStudent studentEntity = studentInformation(studentId);

        model.addAttribute("student",studentEntity);
        if(studentEntity.getStudentGrades().getMathGradeResults().size()>0){
            model.addAttribute("mathAverage",studentEntity.getStudentGrades().findGradePointAverage(studentEntity.getStudentGrades().getMathGradeResults()));
        }
        else {
            model.addAttribute("mathAverage","N/A");
        }

        if(studentEntity.getStudentGrades().getScienceGradeResults().size()>0){
            model.addAttribute("scienceAverage",studentEntity.getStudentGrades().findGradePointAverage(studentEntity.getStudentGrades().getScienceGradeResults()));
        }
        else{
            model.addAttribute("scienceAverage","N/A");
        }

        if(studentEntity.getStudentGrades().getHistoryGradeResults().size()>0){
            model.addAttribute("historyAverage", studentEntity.getStudentGrades().findGradePointAverage(studentEntity.getStudentGrades().getHistoryGradeResults()));
        }
        else{
            model.addAttribute("historyAverage","N/A");
        }
    }
}
