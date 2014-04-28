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
    public static int uniforme(double... probas) {
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

    public static double[] createRandomDistribution(int size, int precision) {
        double[] distribution = new double[size];
        double somme = precision;
        while (precision > 0) {
            // on selectionne un indice au hasard qu'on incrémente d'un nombre au hasard
            int increment = rand.nextInt(precision + 1);
            precision -= increment;
//            System.out.println("precision : "+precision);
            int ir = rand.nextInt(size);
            distribution[ir] += increment;
        }

        for (int i = 0; i < distribution.length; i++) {
            distribution[i] /= somme;
        }

        return distribution;
    }
    
    
    public static double esperance(double[] vecteur){
        double esperance = 0.0;
        for (int i = 0; i < vecteur.length; i++) {
            esperance += vecteur[i];
        }
        return esperance/vecteur.length;
    }
    
    public static double variance(double[] vecteur){
        double variance  = 0.0 ;
        double esperance = esperance(vecteur);
        for (int i = 0; i < vecteur.length; i++) {
            variance += Math.pow(vecteur[i] - esperance,2);
        }
        return variance/ vecteur.length;
    }
    
    public static double ecart_type(double [] vecteur){
        return Math.sqrt(variance(vecteur));
    }
    

    //@todo tester
    public static boolean isProbabilistMatrix(double[][] matrix, double precision){
        boolean isProbabilist = true;
        int i = 0;
        while(isProbabilist && i < matrix.length){
            double somme = 0.0;
            for (int j = 0; j < matrix[i].length; j++) {
                somme +=matrix[i][j];
                
            }
            isProbabilist = (Math.abs(somme-1) < precision);
            i++;
        }
        
        return isProbabilist;
    }
    
    
    
    
    
    /**
     * @return the rand
     */
    public static Random getRand() {
        return rand;
    }
    
    

}
