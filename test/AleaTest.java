/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
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

    //@Test
    public void testBernouilli() {
        for (double i = 0; i < 1; i = i + 0.001) {
            System.out.println("" + i + " : " + Alea.bernouilli(i));
        }
    }

    @Test
    /**
     * D'après les resultats obtenus, on peut affirmer que l'alea mis en place est correct.
     */
    public void testDistribution() {
        double[] matrix;
        int size = 100000;
        int taille = 8;
        double[] somme = new double[taille];
        for (int i = 0; i < taille; i++) {
            somme[i] = 0;
        }
        for (int i = 0; i < size; i++) {
            matrix = Alea.createRandomProbabilistDistribution(taille, 1000);
            for (int j = 0; j < taille; j++) {
                somme[j] = somme[j] + matrix[j];
            }
        }
        for (int i = 0; i < taille; i++) {
            double moyenne = somme[i] / size;
            System.out.println("En moyenne, " + i + " : " + moyenne);
            Assert.assertEquals(1./taille, moyenne, 0.02);
        }
    }

}
