/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithme;

import markovchain.MarkovChain;

/**
 * Singleton factory pour la création des MCMC. Voir le pattern Factory.
 * @author CARRARA Nicolas et CHAYEM Samy
 * @param <E>
 */
public class AlgorithmFactory<E> {
    
    
    public static final int METROPOLIS_HASTING = 0;
    public static final int GIBBS = 1;
    public static final int METROPOLIS_HASTING_SYMETRIC = 2;
    
    
    public static AlgorithmFactory instance = null;
    
    private AlgorithmFactory(){
        
    }
    
    /**
     * récupère le singleton de cette classe.
     * @return 
     */
    public static AlgorithmFactory getInstance(){
        if(instance == null){
            instance = new AlgorithmFactory();
        }
        return instance;
    }
    
    /**
     * Construit un algorithme du type désiré selon la distribution stationnaire
     * théorique choisie.
     * @param typeAlgo
     * @param states
     * @param distribution
     * @return 
     */
    public AlgorithmMCMC getAlgorithm(int typeAlgo,E[] states, double[] distribution){
        switch(typeAlgo){
            case METROPOLIS_HASTING :
                /**
                 * par defaut on init la matrice de transiton à random_symetric
                 * mais faudrait améliorer ça (gaussian_symetric ...).
                 */
                return new MetropolisHasting(states, distribution,MarkovChain.RANDOM_SYMETRIC);
            case GIBBS :
                // TODO
                return null;
            default:
                return null;
        }
    }
}
