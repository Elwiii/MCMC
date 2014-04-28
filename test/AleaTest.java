/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tool.Alea;

/**
 *
 * @author Nikolai
 */
public class AleaTest {

    public AleaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBernouilli() {
        for (double i = 0; i < 1; i = i + 0.001) {
            System.out.println("" + i + " : " + Alea.bernouilli(i));
        }
    }
}
