package com.luv2code.junitdemo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

public class ConditionalTest {
    @Test
    @Disabled("Don't run until we fix JIRA #123")
    void basicTest() {
        System.out.println("inside basicTest");
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testForWindowsOnly(){
        System.out.println("inside testForWindowsOnly");
    }

    @Test
    @EnabledOnOs({OS.WINDOWS, OS.LINUX})
    void testForWindowsAndLinuxOnly(){
        System.out.println("inside testForWindowsAndLinuxOnly");
    }

    @Test
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_11})
    void testForJre8And11(){
        System.out.println("inside testForJre8And11");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_17)
    void testForJre17(){
        System.out.println("inside testForJre17");
    }

    @Test
    @EnabledForJreRange(min=JRE.JAVA_13, max=JRE.JAVA_18)
    void testForJreBetween13And18(){
        System.out.println("inside testForJreBetween13And18");
    }

    @Test
    @EnabledForJreRange(min=JRE.JAVA_11)
    void testForJreMin11(){
        System.out.println("inside testForJreMin11");
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "LUV2CODE_ENV", matches = "DEV")
    void testOnlyForDevEnvironment(){
        System.out.println("inside testOnlyForDevEnvironment");
    }

    @Test
    @EnabledIfSystemProperty(named = "LUV2CODE_SYS_PROP", matches = "CI_CD_DEPLOY")
    void testOnlyForSystemProperty(){
        System.out.println("inside testOnlyForSystemProperty");
    }
}
