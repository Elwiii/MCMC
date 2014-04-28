/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithme;

import java.util.logging.Level;
import java.util.logging.Logger;
import markovchain.MarkovChain;
import tool.Alea;
import tool.Myst;

/**
 * @toto test
 * @author nikolai
 * @param <E>
 */
public class MetropolisHasting<E> extends AlgorithmMCMC<E> {

    int i; // todo test condistion d'arrêt

    public MetropolisHasting(E[] stats, double[] distribution) {
        this.stats = stats;
        this.distribution = distribution;
    }

    @Override
    public MarkovChain constructChain() throws Exception {
        System.out.println("Constructing chain ...");
        MarkovChain mcResultante = new MarkovChain(stats);
        double[][] transitionResultante = new double[stats.length][stats.length];
        MarkovChain mcSimulation = new MarkovChain(stats);

        // on crée une matrice de transition tel que mcSimulation soit facile à simuler
        mcSimulation.intializeTransitionMatrix(MarkovChain.RANDOM /* EASY_TO_SIMULATE */);
        System.out.println("Matrice initialisation McSimul : " + mcSimulation.toString());

        // on choisit le premier état au hasard
        int x = Alea.getRand().nextInt(stats.length);
        mcSimulation.setCurrentStateIndice(x);

        // i <- 0
        /*int */ i = 0;

        // on boucle pour remplire la matrice de transition de mcResultante
        while (!conditionArret()) {
            System.out.println("----------------  "+i+"  -------------------");
            int xtilde = mcSimulation.walk();
            // on calcul alpha
            double d = (distribution[xtilde] / (double) distribution[x]) * (mcSimulation.getTransitionMatrix()[x][xtilde] / (double) mcSimulation.getTransitionMatrix()[xtilde][x]);
            double alpha = Math.min(1., d);
            // acceptation ou rejet ?
            boolean bernouilli = Alea.bernouilli(alpha);
            System.out.println("d : "+d);
            System.out.println("alpha : "+alpha);
            // on met à jouer la matrice de transition de mcResultante (i.e. on construit la châine de markov)
            if (bernouilli) {
                //acceptation
                transitionResultante[x][xtilde] = alpha;
                x = xtilde;
            } else {
                //rejet on fait rien
            }
            i++;
            Myst.afficherMatrice(transitionResultante);
        }
        mcResultante.setTransitionMatrix(transitionResultante);
        return mcResultante;
    }

    @Override
    public boolean conditionArret() {
        return i > 10000000;
    }

    
    public static void main(String[] args) {
        Integer[] states = {0, 1, 2};//,3,4};
        double[] distribution = {0.2, 0.5, 0.3};//, 0.4, 0.1};
        AlgorithmMCMC<Integer> algo = AlgorithmFactory.getInstance().getAlgorithm(AlgorithmFactory.METROPOLIS_HASTING, states, distribution);
        try {
            MarkovChain mc = algo.constructChain();
            System.out.println("mc resultante : " + mc);
//            mc.computeStationaryDistributionUntilStability(0.001);
//            System.out.println(Arrays.asList(mc.getStationary_distribution()).toString());
        } catch (Exception ex) {
            System.err.println(ex);
            System.exit(1);
        }
    }
    
    
    
    
}
