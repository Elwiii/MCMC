/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package problem;

import java.util.Random;

/**
 *
 * @author nikolai
 */
public class Alea {
    
    public static final Random rand = new Random();
    
    /**
     * somme probas = 1 needed
     * @param probas
     * @return 
     */
    public static int uniforme(Double... probas){
        double p = rand.nextDouble();
        boolean stop = false;
        int i = 0;
        double currentProba;
        while(!stop){
            currentProba = probas[i];
            if(i< probas.length){
                if(currentProba < probas[i+1])
                    stop = true;
            }
            i++;
        }
        return i;
    }
    
    public static boolean bernouilli(double proba){
        return uniforme(proba)==0;
    }
    
}
