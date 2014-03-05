/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package markovchain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import problem.Alea;

/**
 *
 * @author nikolai
 * @param <E>
 */
public class MarkovChain<E> {
    
    private Double[][] transitionMatrix;
    private E[] states;
    private int currentState; 
    
    private final Random rand = new Random();
    
    /** constante pour l'initialisation de la matrice de transition */
    public final static int EASY_TO_SIMULATE = 0;
    
   
    
    public MarkovChain(Double[][] transitionMatrix, E[] states){
        this.transitionMatrix = transitionMatrix;
        this.states = states;
    }
    
    public MarkovChain(E[] states){
        this.states = states;
        transitionMatrix = new Double[states.length][states.length];
    }
    
    public void intializeTransitionMatrix(int type) throws Exception{
        switch(type){
            case EASY_TO_SIMULATE :
                // TODO
                break;
            default :
                throw new Exception("type : "+type+" inconnu");
        }
    }
    
    
    
    /**
     * 
     * @return l'indice du nouvel état courant 
     */
    public int walk(){
        Double[]probas = new Double[transitionMatrix[0].length];
        System.arraycopy(transitionMatrix[currentState], 0, probas, 0, probas.length);
        System.out.println("colonne : "+ Arrays.asList(probas));
        return currentState = Alea.uniforme(probas);
    }

    /**
     * @return the currentState
     */
    public int getCurrentState() {
        return currentState;
    }

    /**
     * @param currentState the currentState to set
     */
    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    /**
     * @return the transitionMatrix
     */
    public Double[][] getTransitionMatrix() {
        return transitionMatrix;
    }

    /**
     * @param transitionMatrix the transitionMatrix to set
     */
    public void setTransitionMatrix(Double[][] transitionMatrix) {
        this.transitionMatrix = transitionMatrix;
    }
       
}
