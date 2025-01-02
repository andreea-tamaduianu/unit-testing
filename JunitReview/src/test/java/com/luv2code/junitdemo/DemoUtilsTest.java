package com.luv2code.junitdemo;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
class DemoUtilsTest {
    DemoUtils demoUtils;

    @BeforeEach
    void setUpBeforeEach(){
        demoUtils = new DemoUtils();
        System.out.println("@BeforeEach executes before the execution of each test method.");
    }

    @Test
    @Order(1)
    @DisplayName("Equals and not equals")
    void testEqualsAndNotEquals() {
        assertEquals(6, demoUtils.add(2, 4), "2+4 must be 6");
        assertNotEquals(6, demoUtils.add(1, 9), "1+9 must not be 6");
    }

    @Test
    @Order(0)
    @DisplayName("Null and not null")
    void testNullAndNotNull() {
        String str1 = null;
        String str2 = "luv2code";

        assertNull(demoUtils.checkNull(str1), "Object should be null");
        assertNotNull(demoUtils.checkNull(str2), "Object should not be null");

    }

    @Test
    @DisplayName("Same and not same")
    void testSameAndNotSame() {
        String str = "luv2code";
        assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate(), "Both objects should refer to the same object");
        assertNotSame(str,demoUtils.getAcademy(), "Object do not refer to the same object");
    }

    @Test
    @Order(30)
    @DisplayName("True and false")
    void testTrueAndFalse(){
        int gradeOne = 10;
        int gradeTwo = 5;

        assertTrue(demoUtils.isGreater(gradeOne, gradeTwo), "Grade one should be greater than grade two");
        assertFalse(demoUtils.isGreater(gradeTwo, gradeOne), "Grade two should not be greater than grade one");
    }

    @Test
    @Order(50)
    @DisplayName("Array Equals")
    void testArrayEquals(){
        String[] stringArray = {"A", "B", "C"};
        assertArrayEquals(stringArray,demoUtils.getFirstThreeLettersOfAlphabet(),"Arrays should be equal");
    }

    @Test
    @DisplayName("Iterable equals")
    void testIterableEquals(){
        List<String> theList = List.of("luv", "2", "code");
        assertIterableEquals(theList, demoUtils.getAcademyInList(), "Iterables should be equal");
    }

    @Test
    @DisplayName("Lines match")
    void testLinesMatch(){
        List<String> theList = List.of("luv", "2", "code");

        assertLinesMatch(theList,demoUtils.getAcademyInList(), "Lines should match");
    }

    @Test
    @DisplayName("Throws and does not throw exception")
    void testThrowsAndDoesNotThrowException() {
        assertThrows(Exception.class, () -> demoUtils.throwException(-1), "Should throw exception");
        assertDoesNotThrow(() -> demoUtils.throwException(8), "Should not throw exception");
    }

    @Test
    @DisplayName("Timeout")
    void testTimeout(){
        assertTimeoutPreemptively(Duration.ofSeconds(3),() -> demoUtils.checkTimeout(), "Should return within 3 seconds");
    }

    @Test
    @DisplayName("Multiply")
    void testMultiply(){
        assertEquals(6,demoUtils.multiply(2,3),"2*3 = 6");
    }

//    @AfterEach
//    void tearDownAfterEach(){
//        demoUtils = new DemoUtils();
//        System.out.println("@AfterEach executes after the execution of each test method.");
//    }
//
//    @BeforeAll
//    static void setupBeforeEachClass(){
//        System.out.println("@BeforeAll executes before the execution of all test methods.");
//    }
//
//    @AfterAll
//    static void tearDownAfterEachClass(){
//        System.out.println("@AfterAll executes after the execution of all test methods.");
//    }

}