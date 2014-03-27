/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import algorithme.AlgorithmFactory;
import algorithme.AlgorithmMCMC;
import java.util.logging.Level;
import java.util.logging.Logger;
import markovchain.MarkovChain;

/**
 *
 * @author nikolai
 */
public class Main {
    public static void main(String[] args){
        Integer[] states = {0,1,2};
        double[] distribution = {1./3.,1./2.,1./5.};
        AlgorithmMCMC<Integer> algo = AlgorithmFactory.getInstance().getAlgorithm(AlgorithmFactory.METROPOLIS_HASTING,states,distribution);
        try {
            MarkovChain mc = algo.constructChain();
            System.out.println("mc : "+mc);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
