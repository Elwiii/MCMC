/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithme;

import markovchain.MarkovChain;

/**
 * Classe abstraite pour representer la classe des algorithmes de type 
 * "Chaine de Markov de Monte Carlo".
 * @author CARRARA Nicolas et CHAYEM Samy
 * @param <E> le type des données dans les états de la chaîne
 */
public abstract class AlgorithmMCMC<E> {

    /**
     * Les différents états qu'on veut voir apparaître dans la chaîne resultante.
     */
    protected E[] stats;
    
    /**
     * La distribution stationnaire théorique limite.
     */
    protected double[] distribution;

    /**
     * Cette fonction doit construit une chaîne de markov en fonction
     * de stats et distribution
     * @return une chaîne de Markov dont la distribution stationnaire
     * tend vers la distribution théorique lorsque l'algorithme itère sans limite.
     * @throws Exception 
     */
    public abstract MarkovChain<E> constructChain() throws Exception;

    /**
     * Définit la condifition d'arret de l'algorithme (nombre d'itération,
     * approche d'un epsilon ...).
     * @return vrai si l'algorithme doit s'arrêter d'itérer
     */
    protected abstract boolean conditionArret();

}
