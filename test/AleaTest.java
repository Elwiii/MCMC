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
import static org.junit.Assert.*;
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
//     @Test
//     public void test() {
//         Double[] probas = {1./3.,1./3.,1./3.};
//         System.out.println("resultat  : "+Alea.uniforme(probas));
//     }

        @Test
     public void testBernouilli() {
        System.out.println("resultat  : "+Alea.bernouilli(0.1));
            for (double i = 0; i < 1; i=i+0.001) {
                System.out.println(""+i+" : "+Alea.bernouilli(i));
                
            }
     }

}
