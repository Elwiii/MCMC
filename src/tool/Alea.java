/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

import java.util.Random;

/**
 *
 * @author nikolai
 */
public class Alea {

    private static final Random rand;

    static {
        rand = new Random();
    }

    /**
     * somme probas = 1 needed
     *
     * @todo test
     * @param probas
     * @return l'indice i du tableau correspond à l'evenement realisé de proba
     * probas[i]
     */
    public static int uniforme(Double... probas) {
        // TODO à revoir
        double p = rand.nextDouble();
//        System.out.println("p : " + p);
        boolean stop = false;
        int i = 0;
        double proba = 0;
        while (!stop && i < probas.length) {
//            System.out.println("i : " + i);
            proba += probas[i];
//            System.out.println("proba : "+proba);
            if (p < proba) {
                stop = true;
            }
            i++;
        }
        return i - 1;
    }

    /**
     * @todo test
     * @param proba
     * @return
     */
    public static boolean bernouilli(double proba) {
        return uniforme(proba, 1 - proba) == 0;
    }

    public static Double[] createRandomDistribution(int size, int precision) {
        Double[] distribution = new Double[size];
        double somme = precision;
        while (precision > 0) {
            // on selectionne un indice au hasard qu'on incrémente d'un nombre au hasard
            int increment = rand.nextInt(precision+1);
            precision -= increment;
            System.out.println("precision : "+precision);
            int ir = rand.nextInt(size);
            distribution[ir] = distribution[ir] == null ? increment : distribution[ir] + increment;
        }

        for (int i = 0; i < distribution.length; i++) {
            distribution[i] = distribution[i] == null ? 0 : distribution[i] / somme;
        }

//        precision = size;
//        Double[] distribution = new Double[size];
//        for (int i = 0; i < precision; i++) {
//            int ir = rand.nextInt(size);
//            distribution[ir] = distribution[ir] == null ? 1 : distribution[ir] + 1;
//        }
//        for (int i = 0; i < distribution.length; i++) {
//            distribution[i] = distribution[i] == null ? 0 : distribution[i] / (double) precision;
//
//        }
//        return distribution;
//        Double[] distribution = new Double[size];
//        double somme = 0;
//        for (int i = 0; i < distribution.length; i++) {
//            double ir = rand.nextDouble();
//            somme += ir;
//            distribution[i] = ir;
//        }
//        for (int i = 0; i < distribution.length; i++) {
//            distribution[i] /= somme;
//        }
//        
        return distribution;
    }

    /**
     * @return the rand
     */
    public static Random getRand() {
        return rand;
    }

}
