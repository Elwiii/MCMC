/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithme;

import markovchain.MarkovChain;
import problem.Alea;

/**
 *
 * @author nikolai
 */
class MetropolisHasting<E> extends AlgorithmMCMC<E> {

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
        mcSimulation.intializeTransitionMatrix(MarkovChain.EASY_TO_SIMULATE);
        Double[] probas = new Double[stats.length];

        // on choisit le premier état au hasard
        for (int i = 0; i < probas.length; i++) {
            probas[i] = 1. / probas.length;
        }
        int x = Alea.uniforme(probas);
        mcSimulation.setCurrentState(x);
        
        // i <- 0
        int i = 0;
        
        // on boucle pour remplire la matrice de transition de mcResultante
        while(!conditionArret()){
            int xtilde = mcSimulation.walk();
            // on calcul alpha
            double d = (distribution[xtilde]/distribution[x]) * (mcSimulation.getTransitionMatrix()[x][xtilde]/mcSimulation.getTransitionMatrix()[xtilde][x]);
            double alpha = Math.min(1, d);
            // acceptation ou rejet ?
            boolean bernouilli = Alea.bernouilli(alpha);
            // on met à jouer la matrice de transition de mcResultante (i.e. on construit la châine de markov)
            if(bernouilli){
                //acceptation
                transitionResultante[x][xtilde] = alpha;
            }else{
                //rejet on fait rien
            }
            x = bernouilli?xtilde:x;
            i++;
        }
        mcResultante.setTransitionMatrix(transitionResultante);
        return mcResultante;
    }
    

    @Override
    public boolean conditionArret() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
