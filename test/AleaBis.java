/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tool.Alea;
import tool.Myst;

/**
 *
 * @author nikolai
 */
public class AleaBis {
    
    public AleaBis() {
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
     public void hello() {
         double []distrib;
         int i = 1;
         while(i < 1000){
             double r = Math.random();
             distrib = Alea.createRandomDistribution(i, 100000, r);
             double somme = 0;
             for (int j = 0; j < distrib.length; j++) {
                 somme += distrib[j];
             }
//             Myst.afficherTableau(distrib);
             assertEquals(r,somme,0.001);
             i++;
         }
     }
     
     @Test
     public void testSymetricMatrix(){
         double[][] matrix = Alea.generateRandomSymetricProbabilistMatrix(5, 100000);
         Myst.afficherMatrice(matrix);
         assert(Alea.isProbabilistMatrix(matrix, 0.0001));
     }
     
     @Test
     public void testSymetricMatrixNotNull(){
         double[][] matrix = Alea.generateRandomSymetricProbabilisMatrixNotNull(5, 100000);
         Myst.afficherMatrice(matrix);
         assert(Alea.isProbabilistMatrix(matrix, 0.0001));
     }
}
