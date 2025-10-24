package com.example;


import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        StudentDAOTest.class,
        StudentParameterizedTest.class
})
public class AllTestsSuite { }
