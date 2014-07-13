///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package algorithme;
//
//import java.util.ArrayList;
//import markovchain.MarkovChain;
//import tool.Alea;
//import tool.Myst;
//
///**
// * Meme chose que MetropolisHasting à la différence que la matrice de transition
// * de la chaine de markov de simulation est symétrique.
// * Surement moyen de factoriser les deux classes car la c'est un peu moche.
// * (en faisant par exemple une superclasse abstraire qui factorise le code des 
// * deux algo)
// * @author CARRARA Nicolas et CHAYEM Samy
// * @param <E>
// */
//public class MetropolisHastingSymetric<E> extends AlgorithmMCMC<E> {
//
//    int i; // todo test condistion d'arrêt
//
//    private ArrayList<Integer> sampleValueIndices;
//
//    private double[][] transitionResultante;
//
//    private double[] tempsParEtat;
//
//    public MetropolisHastingSymetric(E[] stats, double[] distribution) {
//        this.stats = stats;
//        this.distribution = distribution;
//        this.tempsParEtat = new double[stats.length];
//    }
//
//    @Override
//    public MarkovChain constructChain() throws Exception {
//        sampleValueIndices = new ArrayList<>();
//        MarkovChain mcResultante = new MarkovChain(stats);
//        transitionResultante = new double[stats.length][stats.length];
//
//        MarkovChain mcSimulation = new MarkovChain(stats);
//
//        // on crée une matrice de transition tel que mcSimulation soit facile à simuler
//        mcSimulation.intializeTransitionMatrix(MarkovChain.RANDOM_SYMETRIC);
////        System.out.println("Matrice initialisation McSimul : " + mcSimulation.toString());
//
//        // on choisit l'état de départ au hasard
//        int x_previous = Alea.getRand().nextInt(stats.length);
//        mcSimulation.setCurrentStateIndice(x_previous);
//        sampleValueIndices.add(x_previous);
//
//        // i <- 0
//        /*int */ i = 0;
//
//        double[][] transitionMatrixSimulation = mcSimulation.getTransitionMatrix();
//        int totalWalk = 0;
//        // on boucle pour remplire la matrice de transition de mcResultante
//        while (!conditionArret()) {
////            System.out.println("----------------  " + i + "  -------------------");
//            // On simule xtilte <- q(x|xi-1)
//            int xtilde = mcSimulation.walk();
//            tempsParEtat[xtilde]++;
//            totalWalk++;
//            // on calcul alpha
////            double diviseur = (transitionMatrixSimulation[x_previous][xtilde] / (double) transitionMatrixSimulation[xtilde][x_previous]);
////            if (Math.abs(diviseur - 1) > 0.001) {
////                System.err.println("Diviseur : " + diviseur);
////                System.err.println("Matrice de simulation non symetrique");
////                System.exit(-1);
////            }
////
////            if (diviseur < 0.00001) {
////                System.err.println("Dans la mesure où la matrice est symétrique, il n'y a pas de raison qu'on ait un d == 0");
////                System.exit(-4);
////            }
//
//            double d = (distribution[xtilde] / (double) distribution[x_previous])
//                    /* diviseur*/; // == 1 si symétrique (ou zero)
//
//            double alpha = Math.min(1., d);
//            // acceptation ou rejet ?
//            // on met à jouer la matrice de transition de mcResultante (i.e. on construit la châine de markov)
//            if (Alea.bernouilli(alpha)) {
//                //acceptation
//                transitionResultante[x_previous][xtilde]++;// = alpha; // pas sur de ça en faite 
//                // transitionResultante[xtilde][x_previous] = alpha;
//                x_previous = xtilde;
//                sampleValueIndices.add(x_previous);
//            } else {
//                //rejet on fait rien
//            }
//            i++;
//        }
//
//        /**
//         * normalisation 
//         */
//        for (int j = 0; j < transitionResultante.length; j++) {
//            double somme = 0.0;
//
//            for (int k = 0; k < transitionMatrixSimulation[j].length; k++) {
//                somme += transitionResultante[j][k];
//            }
//
//            for (int k = 0; k < transitionMatrixSimulation[j].length; k++) {
//                transitionResultante[j][k] /= somme;
//            }
//        }
//
//        // on définit la matrice de transition de la mc généré avec la matrice transition resultante
//        mcResultante.setTransitionMatrix(transitionResultante);
//        if (!Alea.isProbabilistMatrix(transitionResultante, 0.01)) {
//            System.err.print("La matrice générée n'est pas une matrice probabiliste");
//            Myst.afficherMatrice(transitionResultante);
//            System.exit(-2);
//        }
//        
//        for (int j = 0; j < tempsParEtat.length; j++) {
//            tempsParEtat[j] /= totalWalk;
//        }
//
//        return mcResultante;
//    }
//
//    @Override
//    public boolean conditionArret() {
//        boolean isEqualToZero = false;
//        int somme;
//        for (int j = 0; j < transitionResultante.length; j++) {
//            somme = 0;
//            for (int k = 0; k < transitionResultante[0].length; k++) {
//                somme += transitionResultante[j][k];
//            }
//            if (somme == 0) {
//                isEqualToZero = true;
//                break;
//            }
//        }
//
//        return i > 1000000 || (i > 10000 && !isEqualToZero);//10000000;
////        return i > 100000;
//    }
//
//    public static void main(String[] args) {
//        Integer[] states = {0, 1, 2};//,3,4};
//        double[] distribution = {0.3,0.2,05};//{0.05, 0.9, 0.05};//, 0.4, 0.1};
//        int k = 0;
//        double[] d_moyenne = new double[3];
//        double[] d_moyenne_temps = new double[3];
//        final int TAILLE_BOUCLE = 100;
//        while (k < TAILLE_BOUCLE) {
////            System.out.println("k " + k);
//            AlgorithmMCMC algo = AlgorithmFactory.getInstance().getAlgorithm(AlgorithmFactory.METROPOLIS_HASTING, states, distribution);
//
//            try {
//                MarkovChain mc = algo.constructChain();
//                mc.computeStationaryDistributionUntilStabilityFitness(0.000001, 0.001);
//                double[] stationaryDistribution = mc.getStationary_distribution();
////                System.out.println(" Distrib : ");
////                Myst.afficherTableau(stationaryDistribution);
//                for (int i = 0; i < stationaryDistribution.length; i++) {
//                    d_moyenne[i] += stationaryDistribution[i];
//                }
//                for (int i = 0; i < ((MetropolisHasting)algo).getTempsParEtat().length; i++) {
//                    d_moyenne_temps[i]+=((MetropolisHasting)algo).getTempsParEtat()[i];
//                }
////                System.out.println("mc resultante : " + mc);
////            mc.computeStationaryDistributionUntilStability(0.001);
////            System.out.println(Arrays.asList(mc.getStationary_distribution()).toString());
//            } catch (Exception ex) {
//                System.err.println(ex);
//                System.exit(1);
//            }
//            k++;
//        }
//        for (int i = 0; i < d_moyenne.length; i++) {
//            d_moyenne[i] /= (double) TAILLE_BOUCLE;
//            d_moyenne_temps[i] /= (double) TAILLE_BOUCLE;
//        }
//        System.out.println("Distrib moyenne : ");
//        Myst.afficherTableau(d_moyenne);
//        System.out.println("Temps par etat moyen : ");
//        Myst.afficherTableau(d_moyenne_temps);
//    }
//
//    /**
//     * @return the tempsParEtat
//     */
//    public double[] getTempsParEtat() {
//        return tempsParEtat;
//    }
//
//}
