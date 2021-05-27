package com.furad;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void test(){
        CifaChuli cifaChuli=new CifaChuli("D:\\大学课程\\编译原理\\实验1\\C语言关键字.txt","D:\\大学课程\\编译原理\\实验1\\test.txt");
        cifaChuli.run();
    }
}
