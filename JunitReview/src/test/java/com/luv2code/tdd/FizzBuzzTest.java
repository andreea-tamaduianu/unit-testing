package com.luv2code.tdd;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FizzBuzzTest {
    @Test
    @DisplayName("Divisible by three")
    @Order(1)
    void testForDivisibleByThree(){
        String expected = "Fizz";

        assertEquals(expected,FizzBuzz.compute(3), "Should return Fizz");
    }

    @Test
    @DisplayName("Divisible by five")
    @Order(2)
    void testForDivisibleByFive(){
        String expected = "Buzz";

        assertEquals(expected, FizzBuzz.compute(5),"Should return Buzz");
    }

    @Test
    @DisplayName("Divisible by three and five")
    @Order(3)
    void testForDivisibleByThreeAndFive(){
        String expected = "FizzBuzz";
        assertEquals(expected,FizzBuzz.compute(15), "Should return FizzBuzz");
    }

    @Test
    @DisplayName("Divisible by neither three nor five")
    @Order(4)
    void testForUndivisibleByThreeOrFive(){
        String expected = "4";
        assertEquals(expected,FizzBuzz.compute(4), "Should return 4");
    }

    @DisplayName("Testing with small data file")
    @ParameterizedTest
    @CsvFileSource(resources="/small-test-data.csv")
    @Order(5)
    void testSmallDataFile(int value, String expected){
        assertEquals(expected,FizzBuzz.compute(value), "Should return "+expected);
    }

    @ParameterizedTest
    @CsvFileSource(resources="/medium-test-data.csv")
    @DisplayName("Testing with medium data file")
    @Order(6)
    void testMediumDataFile(int value, String expected){
        assertEquals(expected,FizzBuzz.compute(value), "Should return "+expected);

    }

    @ParameterizedTest
    @CsvFileSource(resources="/large-test-data.csv")
    @DisplayName("Testing with large data file")
    @Order(7)
    void testLargeDataFile(int value, String expected){
        assertEquals(expected,FizzBuzz.compute(value), "Should return "+expected);

    }
}
