/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import markovchain.MarkovChain;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tool.Myst;

/**
 *
 * @author Nikolai
 */
public class MarkovChain2 {

    private MarkovChain mc;

    public MarkovChain2() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        Integer[] s = {0, 1, 2, 3};
        mc = new MarkovChain(s);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void random_symetric() throws Exception {
        mc.intializeTransitionMatrix(MarkovChain.RANDOM_SYMETRIC);
        for (int i = 0; i < mc.getTransitionMatrix().length; i++) {
            for (int j = i; j < mc.getTransitionMatrix().length; j++) {
                assertEquals(mc.getTransitionMatrix()[i][j], mc.getTransitionMatrix()[j][i], 0);
            }
        }
    }
}
