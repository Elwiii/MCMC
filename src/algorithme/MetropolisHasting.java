/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithme;

import markovchain.MarkovChain;
import tool.Alea;

/**
 *
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
        MarkovChain mcResultante = new MarkovChain(stats);
        Double[][] transitionResultante = new Double[stats.length][stats.length];
        MarkovChain mcSimulation = new MarkovChain(stats);

        // on crée une matrice de transition tel que mcSimulation soit facile à simuler
        mcSimulation.intializeTransitionMatrix(MarkovChain.RANDOM /* EASY_TO_SIMULATE */);
//        Double[] probas = new Double[stats.length];

        // on choisit le premier état au hasard
//        for (int i = 0; i < probas.length; i++) {
//            probas[i] = 1. / probas.length;
//        }
//        int x = Alea.uniforme(probas);
        int x = Alea.getRand().nextInt(stats.length);
        mcSimulation.setCurrentState(x);

        // i <- 0
        /*int */i = 0;

        // on boucle pour remplire la matrice de transition de mcResultante
        while (!conditionArret()) {
            int xtilde = mcSimulation.walk();
            // on calcul alpha
            double d = (distribution[xtilde] / (double)distribution[x]) * (mcSimulation.getTransitionMatrix()[x][xtilde] / (double)mcSimulation.getTransitionMatrix()[xtilde][x]);
            double alpha = Math.min(1., d);
            // acceptation ou rejet ?
            boolean bernouilli = Alea.bernouilli(alpha);
            // on met à jouer la matrice de transition de mcResultante (i.e. on construit la châine de markov)
            if (bernouilli) {
                //acceptation
                transitionResultante[x][xtilde] = alpha;
                x = xtilde;
            } else {
                //rejet on fait rien
            }
            i++;
        }
        mcResultante.setTransitionMatrix(transitionResultante);
        return mcResultante;
    }

    @Override
    public boolean conditionArret() {
        // TODO
        return i < 10;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
