/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package markovchain;

/**
 *
 * @author Nikolai
 */
public class PeriodiqueOrReductibleMatrix extends Exception{
    public PeriodiqueOrReductibleMatrix() {
        super("il semblerait que la matrice de transition n'admette pas de limite");
    }
}
