/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithme;

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

        // on initialize la matrice resultante avec des -1
        for (int j = 0; j < transitionResultante.length; j++) {
            for (int k = 0; k < transitionResultante.length; k++) {
                transitionResultante[j][k] = MarkovChain.UNDEF_PROBA;
            }
        }

        MarkovChain mcSimulation = new MarkovChain(stats);

        // on crée une matrice de transition tel que mcSimulation soit facile à simuler
        mcSimulation.intializeTransitionMatrix(MarkovChain.RANDOM /* EASY_TO_SIMULATE */);
        System.out.println("Matrice initialisation McSimul : " + mcSimulation.toString());

        // on choisit l'état de départ au hasard
        int x_previous = Alea.getRand().nextInt(stats.length);
        mcSimulation.setCurrentStateIndice(x_previous);

        System.out.println("Etat de départ : " + x_previous);

        // i <- 0
        /*int */ i = 0;

        // on boucle pour remplire la matrice de transition de mcResultante
        while (!conditionArret()) {
            System.out.println("----------------  " + i + "  -------------------");
            // On simule xtilte <- q(x|xi-1)
            int xtilde = mcSimulation.walk();

            // on calcul alpha
            double diviseur = (mcSimulation.getTransitionMatrix()[x_previous][xtilde] / (double) mcSimulation.getTransitionMatrix()[xtilde][x_previous]);
            if(Math.abs(diviseur -1)>0.001){
                System.err.println("Matrice de simulation non symetrique");
                System.exit(-1);
            }
            double d = (distribution[xtilde] / (double) distribution[x_previous])
                    * diviseur; // == 1 si symétrique
            double alpha = Math.min(1., d);
            // acceptation ou rejet ?
            System.out.println("d : " + d);
            System.out.println("alpha : " + alpha);
            // on met à jouer la matrice de transition de mcResultante (i.e. on construit la châine de markov)
            if (Alea.bernouilli(alpha)) {
                //acceptation
                transitionResultante[x_previous][xtilde] = alpha;
                // transitionResultante[xtilde][x_previous] = alpha;
                x_previous = xtilde;
            } else {
                //rejet on fait rien
            }
            i++;
            Myst.afficherMatrice(transitionResultante);
        }

        // on définit la matrice de transition de la mc généré avec la matrice transition resultante
        mcResultante.setTransitionMatrix(transitionResultante);

        return mcResultante;
    }

    @Override
    public boolean conditionArret() {
        return i > 10;//10000000;
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
