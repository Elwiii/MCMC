/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * renvoi une distribution de probabilité de manière uniforme(todo pas sur
     * que ce soit vraiment uniforme)
     *
     * @param size
     * @param precision
     * @return
     */
    public static double[] createRandomProbabilistDistribution(int size, int precision) {
//        double[] distribution = new double[size];
//        double somme = precision;
//        while (precision > 0) {
//            // on selectionne un indice au hasard qu'on incrémente d'un nombre au hasard
//            int increment = rand.nextInt(precision + 1);
//            precision -= increment;
////            System.out.println("precision : "+precision);
//            int ir = rand.nextInt(size);
//            distribution[ir] += increment;
//        }
//
//        for (int i = 0; i < distribution.length; i++) {
//            distribution[i] /= somme;
//        }
//
//        return distribution;
        return createRandomDistribution(size, precision, 1);
    }

    /**
     * tire uniformement des probabilités tel que la somme soit egale à
     * somme_distrib
     *
     * @param size
     * @param precision
     * @param somme_distrib
     * @return
     */
    public static double[] createRandomDistribution(int size, int precision, double somme_distrib) {
        double[] distribution = new double[size];

        if (somme_distrib > 0) {
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
                distribution[i] = somme_distrib * distribution[i] / somme;
            }
        }
        return distribution;
    }

    
    
    public static double[] createRandomDistributionProbabilistWithNoZero(int size, int precision) {
        double[] distribution = new double[size];
        int minimum = (int)Math.pow(precision, 0.2);
        for (int i = 0; i < distribution.length; i++) {
            distribution[i] += minimum;
        }
        
        double somme = precision;
        precision -= minimum * size;
        while (precision > 0) {
            // on selectionne un indice au hasard qu'on incrémente d'un nombre au hasard
            int increment = rand.nextInt(precision + 1);
            precision -= increment;
//            System.out.println("precision : "+precision);
            int ir = rand.nextInt(size);
            distribution[ir] += increment;
        }

        for (int i = 0; i < distribution.length; i++) {
            distribution[i] = distribution[i] / somme;
        }
        return distribution;
    }

    public static final double EPSILON = 0.01;

    /**
     * fail, renvoi des valeurs négatives
     *
     * @deprecated
     * @param size
     * @param precision
     * @param somme_distrib
     * @return
     */
    public static double[] createRandomDistributionWithNoZero(int size, int precision, double somme_distrib) {
        double[] distribution = createRandomDistribution(size, precision, somme_distrib);
        if (somme_distrib > 0) {
            int nb_remplacement;
            List<Integer> donotmodifie = new ArrayList<>();
            /* on remplace les zeros par des epsilons */
            nb_remplacement = 0;
            for (int j = 0; j < distribution.length; j++) {
                if (distribution[j] == 0) {
                    nb_remplacement++;
                    distribution[j] = EPSILON;
                    donotmodifie.add(j);
                }
            }
            if (nb_remplacement > 0) {
                double added = donotmodifie.size() * EPSILON;
                double toSoustract = added / (double) distribution.length;

                for (int j = 0; j < distribution.length; j++) {
                    if (!donotmodifie.contains(j)) {
                        distribution[j] -= toSoustract;
                    }
                }
            }
            donotmodifie.clear();
        }
        return distribution;
    }

    public static double esperance(double[] vecteur) {
        double esperance = 0.0;
        for (int i = 0; i < vecteur.length; i++) {
            esperance += vecteur[i];
        }
        return esperance / vecteur.length;
    }

    public static double variance(double[] vecteur) {
        double variance = 0.0;
        double esperance = esperance(vecteur);
        for (int i = 0; i < vecteur.length; i++) {
            variance += Math.pow(vecteur[i] - esperance, 2);
        }
        return variance / vecteur.length;
    }

    public static double ecart_type(double[] vecteur) {
        return Math.sqrt(variance(vecteur));
    }

    //@todo tester
    public static boolean isProbabilistMatrix(double[][] matrix, double precision) {
        boolean isProbabilist = true;
        int i = 0;
        while (isProbabilist && i < matrix.length) {
            double somme = 0.0;
            for (int j = 0; j < matrix[i].length; j++) {
                somme += matrix[i][j];

            }
            isProbabilist = (Math.abs(somme - 1) < precision);
            i++;
        }

        return isProbabilist;
    }

    public static double[][] generateSymetricProbabilisMatrix(int n, int max_par_ligne) {
        double[][] matrix = new double[n][n];
        // la valeur maximum que peut prendre la somme des colonnes pour chaque ligne
        int[] max_ligne = new int[n];
        List<Integer> lignesNonVide = new ArrayList<>();
        for (int i = 0; i < max_ligne.length; i++) {
            max_ligne[i] = max_par_ligne;
            lignesNonVide.add(i);
        }

        while (!lignesNonVide.isEmpty()) {

            int min = 0;
            Integer i = lignesNonVide.get(rand.nextInt(lignesNonVide.size()));
            Integer j = lignesNonVide.get(rand.nextInt(lignesNonVide.size()));
            min = Math.min(max_ligne[i], max_ligne[j]);
            int increment = rand.nextInt(min + 1);
//            System.out.println("increment : " + increment);
            max_ligne[i] -= increment;
            if (i != j) {
                max_ligne[j] -= increment;
            }

            if (max_ligne[i] == 0) {
                lignesNonVide.remove(i);
            }
            if (max_ligne[j] == 0) {
                lignesNonVide.remove(j);
            }
            matrix[i][j] += increment;
            if (i != j) {
                matrix[j][i] += increment;
            }
//            System.out.println("");
//            Myst.afficherTableau(max_ligne);
//            System.out.println("\n" + lignesNonVide + "\n");
//            Myst.afficherMatrice(matrix);
//            System.out.println("----------------------");
        }

        // verification , à commenter si ça fonctionne
//        for (int i = 0; i < n; i++) {
//            double somme = 0;
//            for (int j = 0; j < n; j++) {
//                somme += matrix[i][j];
//
//            }
//            if (somme != max_par_ligne) {
//                System.err.println("la somme de la ligne n'est pas egale à max_par_ligne : " + somme + " != " + max_par_ligne);
////                    System.exit(-9);
//            }
//        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] /= (double) max_par_ligne;
            }
        }

        return matrix;
    }

    public static double[][] generateSymetricProbabilisMatrixNotNull(int n, int max_par_ligne) {
        double[][] matrix = new double[n][n];
        int valueInit = (int) Math.pow(max_par_ligne, 1. / (double) max_par_ligne); // 1
        valueInit = valueInit == 0 ? 1 : valueInit;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = valueInit;
            }

        }
        max_par_ligne++;
        // la valeur maximum que peut prendre la somme des colonnes pour chaque ligne
        int[] max_ligne = new int[n];
        List<Integer> lignesNonVide = new ArrayList<>();
        for (int i = 0; i < max_ligne.length; i++) {
            max_ligne[i] = max_par_ligne;
            lignesNonVide.add(i);
        }

        while (!lignesNonVide.isEmpty()) {

            int min = 0;
            Integer i = lignesNonVide.get(rand.nextInt(lignesNonVide.size()));
            Integer j = lignesNonVide.get(rand.nextInt(lignesNonVide.size()));
            min = Math.min(max_ligne[i], max_ligne[j]);
            int increment = rand.nextInt(min + 1);
//            System.out.println("increment : " + increment);
            max_ligne[i] -= increment;
            if (i != j) {
                max_ligne[j] -= increment;
            }

            if (max_ligne[i] == 0) {
                lignesNonVide.remove(i);
            }
            if (max_ligne[j] == 0) {
                lignesNonVide.remove(j);
            }
            matrix[i][j] += increment;
            if (i != j) {
                matrix[j][i] += increment;
            }
//            System.out.println("");
//            Myst.afficherTableau(max_ligne);
//            System.out.println("\n" + lignesNonVide + "\n");
//            Myst.afficherMatrice(matrix);
//            System.out.println("----------------------");
        }

        // verification , à commenter si ça fonctionne
//        for (int i = 0; i < n; i++) {
//            double somme = 0;
//            for (int j = 0; j < n; j++) {
//                somme += matrix[i][j];
//
//            }
//            if (somme != max_par_ligne) {
//                System.err.println("la somme de la ligne n'est pas egale à max_par_ligne : " + somme + " != " + max_par_ligne);
////                    System.exit(-9);
//            }
//        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] /= (double) max_par_ligne;
            }
        }

        return matrix;
    }

    /**
     * @return the rand
     */
    public static Random getRand() {
        return rand;
    }

}
