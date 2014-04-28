/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markovchain;

import Jama.Matrix;
import java.util.Random;
import tool.Alea;
import tool.Myst;
import tool.UnconvertibleMatrixException;

/**
 *
 * @author nikolai
 * @param <E>
 */
public class MarkovChain<E> {

    private double[][] transitionMatrix;
    private E[] states;
    private int currentStateIndice;
    private double[] stationary_distribution;

    public static final double UNDEF_PROBA = -1.0;

    private final Random rand = new Random();

    /**
     * constante pour l'initialisation de la matrice de transition
     *
     * transitionMatrix[i][j] = "probabilité de passer de l'état d'indice i à
     * l'état d'indice j
     *
     */
    public final static int EASY_TO_SIMULATE = 0;
    public final static int RANDOM = 1;

    public MarkovChain(double[][] transitionMatrix, E[] states) {
        this.transitionMatrix = transitionMatrix;
        this.states = states;
    }

    public MarkovChain(E[] states) {
        this.states = states;
        transitionMatrix = new double[states.length][states.length];
        fillWithUndefTransitionMatrix();
    }

    private void fillWithUndefTransitionMatrix() {
        for (int i = 0; i < transitionMatrix.length; i++) {
            for (int j = 0; j < transitionMatrix[0].length; j++) {
                transitionMatrix[i][j] = UNDEF_PROBA;
            }
        }
    }

    public void intializeTransitionMatrix(int type) throws Exception {
        switch (type) {
            case EASY_TO_SIMULATE:
                // TODO
                break;
            case RANDOM:
                for (int i = 0; i < states.length; i++) {
                    transitionMatrix[i] = Alea.createRandomDistribution(states.length, 1000);
                }
                break;
            default:
                throw new Exception("type : " + type + " inconnu");
        }
    }

    
    public void computeStationaryDistribution(int puissance, double precision) throws PeriodiqueOrReductibleMatrix {
        Matrix m = new Matrix(transitionMatrix);
        Matrix avantDerniereMatrice;

        Matrix infinite = m.copy();
        
        for (int i = 0; i < puissance - 1; i++) {
           infinite = infinite.times(m);
        }

        avantDerniereMatrice = infinite.copy();
        infinite = infinite.times(m);

        /**
         * on vérifie qu'on a bien un comportement limite
         */
        for (int i = 0; i < transitionMatrix.length; i++) {
            for (int j = 0; j < transitionMatrix.length; j++) {
                if (Math.abs(infinite.get(i, j) - avantDerniereMatrice.get(i, j)) > precision) {
                    throw new PeriodiqueOrReductibleMatrix();
                }
            }
        }

        /**
         * on récupère la distribution stationnaire (une des lignes de la
         * matrice)
         */
        double[] res = new double[transitionMatrix.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = infinite.get(0, i);
        }

        stationary_distribution = res;
    }

    /**
     * calcul le carré de la matrice de transition autant de fois que necessaire
     * jusqu'a trouver une distribution stationaire au critère
     * precision_stability près.
     *
     * @param precision_stability
     * @return
     * @throws PeriodiqueOrReductibleMatrix
     */
    public void computeStationaryDistributionUntilStability(double precision_stability) throws PeriodiqueOrReductibleMatrix {

        Matrix m = new Matrix(transitionMatrix);

        boolean stability = false;
        Matrix temp;
        Matrix infinite = m.copy();

        int max_time = 100000;
        int i = 0;

        /**
         * on boucle tant qu'on atteint pas la stabilité
         */
        while (!stability && i < max_time) {
            System.out.println("i : "+i);
            Myst.afficherMatrice(infinite.getArray());
            temp = infinite.copy();
            infinite = infinite.times(m);//arrayTimesEquals(m);
//            System.out.println("Myst.compareMatrix(m, temp) : "+Myst.compareMatrix(m, temp));
            stability = (precision_stability > Myst.compareMatrix(infinite, temp));
            i++;
        }

        if (i == max_time) {
            throw new PeriodiqueOrReductibleMatrix();
        }else{
//            System.out.println("Nombre de carré avant stabilité : "+(i+1));
        }

        System.out.println("res : ");
        Myst.afficherMatrice(infinite.getArray());

        /**
         * on récupère la distribution stationnaire (une des lignes de la
         * matrice)
         */
        double[] res = new double[transitionMatrix.length];
        for (int j = 0; j < res.length; j++) {
            res[j] = infinite.get(0, j);
        }

        stationary_distribution = res;
    }

    /**
     * @todo test
     * @return l'indice du nouvel état courant
     */
    public int walk() {
        double[] probas = new double[transitionMatrix[0].length];
        System.arraycopy(transitionMatrix[currentStateIndice], 0, probas, 0, probas.length);
//        System.out.println("colonne : " + Arrays.asList(probas));
        return currentStateIndice = Alea.uniforme(probas);
    }

    @Override
    public String toString() {
        String res = "\n";
//        for (int i = 0; i < states.length; i++) {
//            res += states[i]+"";
//        }
        res += "\n";
        for (int i = 0; i < transitionMatrix.length; i++) {
            res += states[i];
            for (int j = 0; j < transitionMatrix[0].length; j++) {
                res += "\t" + transitionMatrix[i][j];
//                res += String.format("%5d", transitionMatrix[i][j]);
            }
            res += "\n";
        }
        return res;
    }

    public E getCurrentState() {
        return states[currentStateIndice];
    }

    /**
     * @return the currentState
     */
    public int getCurrentStateIndice() {
        return currentStateIndice;
    }

    /**
     * @param currentStateIndice the currentState to set
     */
    public void setCurrentStateIndice(int currentStateIndice) {
        this.currentStateIndice = currentStateIndice;
    }

    /**
     * @return the transitionMatrix
     */
    public double[][] getTransitionMatrix() {
        return transitionMatrix;
    }

    /**
     * @param transitionMatrix the transitionMatrix to set
     */
    public void setTransitionMatrix(double[][] transitionMatrix) {
        this.transitionMatrix = transitionMatrix;
    }

    /**
     * @return the stationary_distribution
     */
    public double[] getStationary_distribution() {
        return stationary_distribution;
    }

    /**
     * @param stationary_distribution the stationary_distribution to set
     */
    public void setStationary_distribution(double[] stationary_distribution) {
        this.stationary_distribution = stationary_distribution;
    }

}
