/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithme;

/**
 *
 * @author nikolai
 * @param <E>
 */
public class AlgorithmFactory<E> {
    
    
    public static final int METROPOLIS_HASTING = 0;
    public static final int GIBBS = 1;
    
    public static AlgorithmFactory instance = null;
    
    private AlgorithmFactory(){
        
    }
    
    public static AlgorithmFactory getInstance(){
        if(instance == null){
            instance = new AlgorithmFactory();
        }
        return instance;
    }
    
    public AlgorithmMCMC getAlgorithm(int typeAlgo,E[] states, double[] distribution){
        switch(typeAlgo){
            case METROPOLIS_HASTING :
                return new MetropolisHasting(states, distribution);
            case GIBBS :
                // TODO
                return null;
            default:
                return null;
        }
    }
}
