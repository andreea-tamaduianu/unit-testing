package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class MockAnnotationTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CollegeStudent collegeStudent;

    @Autowired
    StudentGrades studentGrades;

    @MockBean
    private ApplicationDao applicationDao;

    @Autowired
    private ApplicationService applicationService;

    @BeforeEach
    public void setup(){
        collegeStudent.setFirstname("Eric");
        collegeStudent.setLastname("Roby");
        collegeStudent.setEmailAddress("eric.roby@luv2code_school.com");
        collegeStudent.setStudentGrades(studentGrades);
    }

    @Test
    @DisplayName("When & verify")
    public void testAddGrades(){
        when(applicationDao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults())).thenReturn( 100.0);

        assertEquals(100.0, applicationService.addGradeResultsForSingleClass(collegeStudent.getStudentGrades().getMathGradeResults()));

        verify(applicationDao,times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }

    @Test
    @DisplayName("Find GPA")
    public void assertEqualsTestFindGPA(){
        when(applicationDao.findGradePointAverage((studentGrades.getMathGradeResults()))).thenReturn(88.31);
        assertEquals(88.31, applicationService.findGradePointAverage(collegeStudent.getStudentGrades().getMathGradeResults()));
    }

    @Test
    @DisplayName("Not null")
    public void testAssertNotNull(){
        when(applicationDao.checkNull(studentGrades.getMathGradeResults())).thenReturn(true);
        assertNotNull(applicationService.checkNull(collegeStudent.getStudentGrades().getMathGradeResults()),"Object should not be null");
    }

    @Test
    @DisplayName("Throw runtime error")
    public void testThrowRuntimeException(){
        CollegeStudent nullStudent = (CollegeStudent) applicationContext.getBean("collegeStudent");

        doThrow(new RuntimeException()).when(applicationDao).checkNull(nullStudent);

        assertThrows(RuntimeException.class, () -> {
            applicationService.checkNull(nullStudent);
        });

        verify(applicationDao, times(1)).checkNull(nullStudent);
    }

    @Test
    @DisplayName("Multiple stubbing")
    public void stubbingConsecutiveCalls(){
        CollegeStudent nullStudent = applicationContext.getBean("collegeStudent", CollegeStudent.class);

        when(applicationDao.checkNull(nullStudent))
                .thenThrow(new RuntimeException())
                .thenReturn("Do not throw exception the second time.");

        assertThrows(RuntimeException.class, () -> {
            applicationService.checkNull(nullStudent);
        });

        assertEquals("Do not throw exception the second time.", applicationService.checkNull(nullStudent));

        verify(applicationDao,times(2)).checkNull(nullStudent);
    }
}
