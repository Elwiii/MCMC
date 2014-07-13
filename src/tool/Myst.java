/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

import Jama.Matrix;

/**
 * Ensemble hétérogène de fonctions utiles
 * @author CARRARA Nicolas
 */
public class Myst {

    /**
     * Print une matrice partiellement sur la sortie standard
     * @param tableau
     * @param x longeur 
     * @param y largueur
     */
    public static void afficherMatrice(double[][] tableau, int x, int y) {
        for (int j = 0; j < x; j++) {
            System.out.print("\n|");
            for (int k = 0; k < y; k++) {
                System.out.print(tableau[j][k] + "|");
            }
        }
        System.out.println("");
    }
    
    /**
     * Print une matrice complete sur la sortie standard
     * @param tableau 
     */
    public static void afficherMatrice(double[][] tableau) {
        for (int j = 0; j < tableau.length; j++) {
            System.out.print("\n|");
            for (int k = 0; k < tableau[0].length; k++) {
                System.out.print(tableau[j][k] + "|");
            }
        }
        System.out.println("");
    }
    
    /**
     * Retourne une répresentation String de la matrice.
     * @param tableau
     * @return 
     */
    public static String toStringMatrix(double[][] tableau){
        String res = "";
         for (int j = 0; j < tableau.length; j++) {
            res+= "\n|";
            for (int k = 0; k < tableau[0].length; k++) {
                res+=tableau[j][k] + "|";
            }
        }
        
        return res + "\n";
    }
    
    
    /**
     * Print un tableau sur la sortie standard
     * @param tab 
     */
    public static void afficherTableau(double[] tab){
        System.out.print("|");
        for (int i = 0; i < tab.length; i++) {
            System.out.print(""+tab[i]+"|");
        }
        System.out.println("");
    }
    
    /**
     * Print un tableau sur la sortie standard
     * @param tab 
     */
    public static void afficherTableau(int[] tab){
        System.out.print("|");
        for (int i = 0; i < tab.length; i++) {
            System.out.print(""+tab[i]+"|");
        }
        System.out.println("");
    }
    
    /**
     * Compare deux matrices
     * @param a
     * @param b
     * @return la différence en moyenne par element de la matrice
     */
    public static double compareMatrix(Matrix a, Matrix b){
        double diff = 0;
        for (int i = 0; i < a.getRowDimension(); i++) {
            for (int j = 0; j < a.getColumnDimension(); j++) {
                diff += Math.abs(a.get(i, j)-b.get(i, j));
            }
        }
        return diff/(a.getColumnDimension() * a.getRowDimension());
    }
    
    
    /**
     * 
     * @param matrix
     * @return
     * @throws UnconvertibleMatrixException 
     */
    public static double[][] DoubleTodoubleMatrix(Double[][] matrix) throws UnconvertibleMatrixException{
        double[][] res = new double[matrix.length][matrix[0].length];
        
        for (int i=0;i< matrix.length;i++){
            for(int j=0;j < matrix[0].length;j++){
                if(matrix[i][j]==null){
                    throw new UnconvertibleMatrixException();
                }else{
                    res[i][j] = matrix[i][j];
                }
            }
        }
        
        
        return res;
    }
}
