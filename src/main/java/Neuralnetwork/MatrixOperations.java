/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Neuralnetwork;

/**
 *
 * @author Corona Daniel / Canto Pablo / Villares Damian / Villarino Daniela / Molina Brisa     
 */

//En esta interfaz se definen diversas operaciones con matrices 
//Notese que una matriz de tamano n x 1 es un vector, entonces estos metodos funcionan
public interface MatrixOperations {

    //Este metodo traspone la matriz dada 
    public Matrix T();
    
    //Metodo para e producto de matrices
    public Matrix Dot(Matrix matrix);
    
    //Metodo para la suma de matrices
    public Matrix Sum(Matrix matrix);
    
    //metodo para el producto escalar de una matriz 
    public Matrix Scalar(Double scalar);

//    Metodo para la suma a un escalar
    public Matrix ScalarSum(Double scalar);
    
    public Matrix ReLU(); 
    
    public Matrix ReLU_deriv(); 
   
    public Matrix Softmax(); 
    
    public Matrix DataProduct(Matrix matrix); 
    
    public double DataSum(); 
    
    
    
    //Este metodo genera una matriz con numeros al azar en el intervalo [0,1)


    
}
