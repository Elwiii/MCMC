
import algorithme.AlgorithmFactory;
import algorithme.AlgorithmMCMC;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import markovchain.MarkovChain;
import tool.Alea;
import tool.Myst;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Nikolai
 */
public class main {

    public static void main(String[] args) {
        Random rand = new Random();
        Integer[] states;
        double[] distributionTheorique;
        AlgorithmMCMC<Integer> algo;
        MarkovChain mc;
        double[] distributionExperimentale;
        final int ITERATION = 100;
        double pourcentages = 0;
        int i = 0;
        while (i < ITERATION) {
            try {
                int N = rand.nextInt(10) + 1;
                states = new Integer[N];
                for (int j = 0; j < N; j++) {
                    states[j] = j;
                }
//            distributionTheorique = Alea.createRandomDistribution(N, N*100);
                distributionTheorique = Alea.createRandomDistributionProbabilistWithNoZero(N, N * 100);
//                Myst.afficherTableau(distributionTheorique);
//            System.out.println("ok");
                algo = AlgorithmFactory.getInstance().getAlgorithm(AlgorithmFactory.METROPOLIS_HASTING, states, distributionTheorique);
                mc = algo.constructChain();
                mc.computeStationaryDistributionUntilStabilityFitness(0.000001, 0.001);
                distributionExperimentale = mc.getStationary_distribution();
                double difference_ditrib = 0.0;
                for (int j = 0; j < N; j++) {
                    //assertEquals(distributionExperimentale[j],distributionTheorique[j],0.05);
                    difference_ditrib += Math.abs(distributionExperimentale[j] - distributionTheorique[j]);
                }
                difference_ditrib /= N;
                pourcentages += difference_ditrib;
                i++;
            } catch (Exception ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        pourcentages /= ITERATION;
        System.out.println("taux d'erreur : " + pourcentages * 100 + "%");

    }
}
