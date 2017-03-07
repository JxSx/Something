package com.yolo.myapplication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author: jiaxin
 * @date: 2017-03-07 14:11
 */
public class ExampleUnitTestTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("setUP");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("tearDown");

    }

    @Test
    public void addition_isCorrect() throws Exception {
        System.out.println("addition_isCorrect");
    }

}