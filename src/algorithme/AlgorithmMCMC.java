/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithme;

import markovchain.MarkovChain;

/**
 *
 * @author nikolai
 * @param <E>
 */
public abstract class AlgorithmMCMC<E> {

    protected E[] stats;
    protected double[] distribution;

    public abstract MarkovChain<E> constructChain() throws Exception;

    protected abstract boolean conditionArret();

}
