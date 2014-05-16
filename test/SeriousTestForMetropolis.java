/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import algorithme.AlgorithmFactory;
import algorithme.AlgorithmMCMC;
import java.util.Random;
import markovchain.MarkovChain;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tool.Alea;
import tool.Myst;

/**
 *
 * @author Nikolai
 */
public class SeriousTestForMetropolis {

    static Random rand;

    public SeriousTestForMetropolis() {
    }

    @BeforeClass
    public static void setUpClass() {
        rand = new Random();
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
    @Test
    public void hello() throws Exception {
        Integer[] states;
        double[] distributionTheorique;
        AlgorithmMCMC<Integer> algo;
        MarkovChain mc ;
        double[] distributionExperimentale;
//        
        int i = 0;
        while (i < 100) {
            int N = rand.nextInt(10) + 1;
            states = new Integer[N];
            for (int j = 0; j < N; j++) {
                states[j] = j;
            }
//            distributionTheorique = Alea.createRandomDistribution(N, N*100);
            distributionTheorique = Alea.createRandomDistributionProbabilistWithNoZero(N, N*100);
            Myst.afficherTableau(distributionTheorique);
//            System.out.println("ok");
            algo = AlgorithmFactory.getInstance().getAlgorithm(AlgorithmFactory.METROPOLIS_HASTING, states, distributionTheorique);
            mc = algo.constructChain();
            mc.computeStationaryDistributionUntilStabilityFitness(0.000001, 0.001);
            distributionExperimentale = mc.getStationary_distribution();
            for (int j = 0; j < N; j++) {
                //assertEquals(distributionExperimentale[j],distributionTheorique[j],0.05);
            }
            i++;
        }
    }
}
