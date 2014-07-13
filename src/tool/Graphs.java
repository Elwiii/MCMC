///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package tool;
//
//import org.jgrapht.WeightedGraph;
//import org.jgrapht.graph.DefaultDirectedWeightedGraph;
//import org.jgrapht.graph.ListenableDirectedWeightedGraph;
//
///**
// *
// * @author nikolai
// */
//public class Graphs {
//
//    public static void showMarkovChain(double[][] matriceTransition, String[] labelEtat) {
////public void setWeight(double weight)
////If this is indeed the case, then what you need to do is:
////
////Derive your own subclass from ListenableDirectedWeightedGraph (e.g., ListenableDirectedWeightedGraph). I would add both constructor versions, delegating to "super" to ensure compatibility with the original class.
////
////Create the graph as in your question, but using the new class
////
////ListenableDirectedWeightedGraph g = 
////    new CustomListenableDirectedWeightedGraph(
////        MyWeightedEdge.class);
////Override the method setEdgeWeight as follows:
////
////public void setEdgeWeight(E e, double weight) {
////    super.setEdgeWeight(e, weight);
////    ((MyWeightedEdge)e).setWeight(weight);
////}        
//ListenableDirectedWeightedGraph gl = new ListenableDirectedWeightedGraph(g);
//    
//    }
//
//}
