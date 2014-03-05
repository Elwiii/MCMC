/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problem;

import algorithme.AlgorithmFactory;
import algorithme.AlgorithmMCMC;
import markovchain.MarkovChain;

/**
 *
 * @author nikolai
 * @param <E>
 */
public class ProblemMCMC<E> {

    private final AlgorithmMCMC<E> algorithm;
    private final E[] states;
    private final double[] distribution;

    
    private static final ProblemMCMC instance = null; 


    public ProblemMCMC(E[] states, double[] distribution, int typeAlgorithm) {
        this.states = states;
        this.distribution = distribution;
        this.algorithm = AlgorithmFactory.getInstance().getAlgorithm(typeAlgorithm,states,distribution);
    }

    public MarkovChain resolve() throws Exception {
        return algorithm.constructChain();
    }
}
