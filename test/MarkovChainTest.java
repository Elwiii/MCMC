/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Jama.Matrix;
import markovchain.MarkovChain;
import markovchain.PeriodiqueOrReductibleMatrix;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tool.Alea;
import tool.UnconvertibleMatrixException;

/**
 *
 * @author Nikolai
 */
public class MarkovChainTest {

    public MarkovChainTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    private final double[][] transtionMatrixDeterministe
            //            = {       /*0   1     2    3 */
            //                /*0*/ {0.0, 0.5, 0.2, 0.3},
            //                /*1*/ {0.0, 0.7, 0.3, 0.0},
            //                /*2*/ {0.0, 0.7, 0.0, 0.3},
            //                /*3*/ {0.2, 0.0, 0.8, 0.0}
            //            };
            = { /*0   1     2    3 */
                /*0*/{0.0, 1.0, 0.0, 0.0},
                /*1*/ {0.0, 0.0, 1.0, 0.0},
                /*2*/ {0.0, 0.0, 0.0, 1.0},
                /*3*/ {1.0, 0.0, 0.0, 0.0}
            };

    private final double[][] transtionMatrixAutre
            = { /*0   1     2    3 */
                /*0*/{0.0, 0.3, 0.7},
                /*1*/ {0.2, 0.0, 0.8},
                /*2*/ {1.0, 0.0, 0.0},};

    private final Integer[] statesBouche = {0, 1};

    private final Integer[] statesAutre = {0, 1, 2};

    private final double[][] transtionMatrixBouche
            = { /*0   1     2    3 */
                /*0*/{1. - 0.01, 0.01},
                /*1*/ {0.01, 1. - 0.01},};

    private MarkovChain<Integer> mc;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() {
    }

    public double[] getColonne(Matrix matrix, int i) {
        double[][] tableauMatrix = matrix.getArray();
        int nbLigne = matrix.getRowDimension();
        double[] colonne = new double[nbLigne];
        for (int j = 0; j < nbLigne; j++) {
            colonne[j] = tableauMatrix[j][i];
        }
        return colonne;
    }

    /**
     * Fonctionne Bien :)
     *
     * @param matrix
     * @return
     */
    public double fitnessMatrix(Matrix matrix) {
        double sommeVariance = 0;
        for (int j = 0; j < matrix.getColumnDimension(); j++) {
            double[] col = getColonne(matrix, j);
            System.out.println("\nColonne " + j);
            for (int i = 0; i < col.length; i++) {
                System.out.println(col[i]);
            }
            System.out.println("Esperance Col " + j + " : " + Alea.esperance(col));
            System.out.println("Variance Col " + j + " : " + Alea.variance(col));
            sommeVariance = sommeVariance + Alea.variance(col);
        }
        return sommeVariance;
    }

    //@Test
    public void testFitness() {
        Matrix matrix = new Matrix(transtionMatrixAutre);
        System.out.println("\nVariance de la Matrice = " + fitnessMatrix(matrix));
    }

//    @Test
    public void testDistributionStationaireBoucheAOreille() throws UnconvertibleMatrixException, PeriodiqueOrReductibleMatrix {
        mc = new MarkovChain(transtionMatrixBouche, statesBouche);
        mc.computeStationaryDistribution(200, 0.001);
        double[] stationaryDistribution = mc.getStationary_distribution();
        assertEquals(0.509, stationaryDistribution[0], 0.001);
        assertEquals(0.491, stationaryDistribution[1], 0.001);

    }

//    @Test
    public void testDistributionStationaireAutre() throws UnconvertibleMatrixException, PeriodiqueOrReductibleMatrix {
        mc = new MarkovChain(transtionMatrixAutre, statesAutre);
        mc.computeStationaryDistribution(15, 0.01);
        double[] stationaryDistribution = mc.getStationary_distribution();
        assertEquals(0.446, stationaryDistribution[0], 0.001);
        assertEquals(0.134, stationaryDistribution[1], 0.001);
        assertEquals(0.42, stationaryDistribution[2], 0.001);

    }

//    @Test
    public void testComputeUntilStationaryBoucheAOreille() throws PeriodiqueOrReductibleMatrix {
        mc = new MarkovChain(transtionMatrixBouche, statesBouche);
        mc.computeStationaryDistributionUntilStability(0.000001);
        double[] stationaryDistribution = mc.getStationary_distribution();
        assertEquals(0.5, stationaryDistribution[0], 0.001);
        assertEquals(0.5, stationaryDistribution[1], 0.001);
    }

    /**
     * http://icwww.epfl.ch/~chappeli/it/courseFR/C3subsec_statdist.php?shownav=yes
     *
     * @throws PeriodiqueOrReductibleMatrix
     */
//    @Test
    public void testUntil() throws PeriodiqueOrReductibleMatrix {
        double[][] t
                = { /*0   1     2    3 */
                    /*0*/{0.823, 0.087, 0.045, 0.044},
                    /*1*/ {0.058, 0.908, 0.032, 0.001},
                    /*2*/ {0.030, 0.032, 0.937, 0.001},
                    /*3*/ {0.044, 0.002, 0.001, 0.952}
                };

        Integer[] s = {0, 1, 2, 3};

        mc = new MarkovChain(t, s);
        mc.computeStationaryDistributionUntilStability(0.00001);
        double[] stationaryDistribution = mc.getStationary_distribution();
        assertEquals(0.2, stationaryDistribution[0], 0.01);
        assertEquals(0.3, stationaryDistribution[1], 0.01);
        assertEquals(0.3, stationaryDistribution[2], 0.01);
        assertEquals(0.2, stationaryDistribution[3], 0.01);

    }

    @Test
    public void testDistributionStationaireUntilStationaryAutre() throws UnconvertibleMatrixException, PeriodiqueOrReductibleMatrix {
        System.out.println("******************DEBUT AUTRE*********************");
        mc = new MarkovChain(transtionMatrixAutre, statesAutre);
        mc.computeStationaryDistributionUntilStability(0.000001);
        double[] stationaryDistribution = mc.getStationary_distribution();
        assertEquals(0.446, stationaryDistribution[0], 0.001);
        assertEquals(0.134, stationaryDistribution[1], 0.001);
        assertEquals(0.42, stationaryDistribution[2], 0.001);
        System.out.println("******************FIN AUTRE*********************\n");

    }

    @Test
    public void testDistributionStationaireUntilStationaryFitness() throws UnconvertibleMatrixException, PeriodiqueOrReductibleMatrix {
        System.out.println("******************DEBUT FITNESS*********************");
        mc = new MarkovChain(transtionMatrixAutre, statesAutre);
        mc.computeStationaryDistributionUntilStabilityFitness(0.000001, 0.001);
        double[] stationaryDistribution = mc.getStationary_distribution();
        assertEquals(0.446, stationaryDistribution[0], 0.001);
        assertEquals(0.134, stationaryDistribution[1], 0.001);
        assertEquals(0.42, stationaryDistribution[2], 0.001);
        System.out.println("******************FIN FITNESS*********************");

    }

    //@Test
    public void nonDeterministeMC() {
        final double[][] transtionMatrixNonDeterministe
                = { /*0   1     2     */
                    /*0*/{0.0, 0.3, 0.7},
                    /*1*/ {0.2, 0.0, 0.8},
                    /*2*/ {1.0, 0.0, 0.0},};

        final Integer[] states = {0, 1, 2};

        final double[] cible_distrib = {0.446, 0.134, 0.42};

        mc = new MarkovChain(transtionMatrixNonDeterministe, states);
        double[] etats_parcours = new double[states.length];
        int i = 0;
        while (i < (1 << 16)) {
            mc.walk();
            etats_parcours[mc.getCurrentStateIndice()]++;
            i++;
        }

        for (int j = 0; j < etats_parcours.length; j++) {
            etats_parcours[j] /= (i - 1);
        }

        for (int j = 0; j < etats_parcours.length; j++) {
            System.out.println(etats_parcours[j]);
            System.out.println(cible_distrib[j]);
            assertEquals(etats_parcours[j], cible_distrib[j], 0.001);
        }

    }
}
