/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Neuralnetwork;
import java.util.Random;
import java.io.Serializable; 
/**
 *
 * @author danie
 */
public class Matrix implements MatrixOperations, Serializable{
    
    //Matriz de datos
    private double [][] data; 
    
    //Se define el tamanio de la matriz de datos
    public Matrix (int nRows, int nCols) {
        this.data = new double [nRows][nCols]; 
    }
    
    //Devuelve la matriz de datos completa
    public double [][] getData(){ 
        return data; 
    }
    
    //Set para la matriz de datos completa
    public void setData(double [][] data) {
        this.data = data; 
    }
    
    //devuelve un elemento de la matriz
    public double getValue(int nRow, int nCol){
        return data[nRow][nCol]; 
    }
    
    //set para un elemento de la matriz
    public void setValue(int nRow, int nCol, double value){
        data[nRow][nCol] = value; 
    }
    
    public Matrix T(){
        Matrix newMatrix = new Matrix(getData()[0].length, getData().length); 
          
        for (int i = 0; i < newMatrix.getData().length; i++){
            for (int k = 0; k < newMatrix.getData()[0].length; k++){
                
                newMatrix.setValue(i, k, getValue(k, i)); 
            }
        }
        return newMatrix; 
    }
    
    //Metodo para e producto de matrices
    public Matrix Dot(Matrix matrix){
        //Asignamos las dimensiones de la matriz resultante
        
        if (getData()[0].length != matrix.getData().length) {
            throw new IllegalArgumentException("Las matrices no cumplen con las dimensiones");
        }
        
        Matrix result = new Matrix(getData().length, matrix.getData()[0].length); 
        double [][] dot = new double [getData().length][matrix.getData()[0].length]; 
        
        for (int i = 0; i < getData().length; i++) {
            for (int j = 0; j < matrix.getData()[0].length; j++) {
                for (int k = 0; k <getData()[0].length; k++) {
                    // aquí se multiplica la matriz
                    dot[i][j] += getValue(i,k) * matrix.getValue(k,j);
                }
            }
        }
        
        result.setData(dot); 
        return result; 
    }
    
    //Metodo para la suma de matrices
    public Matrix Sum(Matrix matrix) {
        if (getData().length != matrix.getData().length || getData()[0].length != matrix.getData()[0].length) {
            throw new IllegalArgumentException("Las matrices deben tener el mismo tamaño");
        }
        Matrix result = new Matrix(getData().length, getData()[0].length);
        for (int i = 0; i < result.getData().length; i++) {
            for (int j = 0; j < result.getData()[0].length; j++) {
                result.setValue(i, j, getValue(i, j) + matrix.getValue(i, j));  // Asigna la suma
            }
        }
        return result;
    }

    
    //metodo para el producto escalar de una matriz 
    public Matrix Scalar(Double scalar){
        double [][] Scalarprod = new double [getData().length][getData()[0].length];
        Matrix result = new Matrix (getData().length, getData()[0].length); 
        
        try {
            for (int i = 0; i < Scalarprod.length; i++){
                for (int k = 0; k < Scalarprod[0].length; k++){
                    Scalarprod[i][k] = scalar * getValue(i,k);
                }
            }
        } 
        catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
        result.setData(Scalarprod); 
        return result; 
    }
    
//    Definimos la suma escalar, al sumar un escalar la matriz, se le suma este escalar a todo elemento
    public Matrix ScalarSum(Double scalarsum){
        Matrix result = new Matrix (getData().length, getData()[0].length);
        
        for (int i =0; i < getData().length; i++){
            for (int k = 0; k < getData()[0].length; k++){
                    result.setValue(i, k, (getValue(i,k) + scalarsum));
            }
        }
        
        return result; 
    }
//    este metodo sirve como el broadcasting de python, puedes sumar un vector a una martiz
    
    public Matrix SumVector(Matrix vector) {
        Matrix result = new Matrix (getData().length, getData()[0].length);
        
        for (int i = 0; i < getData().length; i++){
            for (int k = 0; k < getData()[0].length; k++){
                result.setValue(i, k, getValue(i,k) + vector.getValue(i, 0));
            }
        }
        
        return result; 
    }
    
//    Este metodo genera una matriz con numeros al azar en el intervalo [0,1)
    public Matrix ReLU(){
        double [][] newMatrix = new double [getData().length][getData()[0].length];
        Matrix result = new Matrix (getData().length, getData()[0].length);
        for (int i = 0; i < getData().length; i++) {
            for (int j = 0; j < getData()[0].length; j++) {
                newMatrix[i][j] = Math.max(getValue(i,j), 0); // Aplica ReLU elemento por elemento
            }
        }
        
        result.setData(newMatrix);
        return result;  
    }
    
    public Matrix ReLU_deriv(){ 
        Matrix result = new Matrix(getData().length, getData()[0].length); 
        
        for (int i = 0; i < getData().length; i++){
            for (int k = 0; k < getData()[0].length; k++){
                if (getValue(i,k) < 0.0) {
                    result.setValue( i, k, 0.0);
                }
                else {
                    result.setValue(i,k,1.0);
                }
            }
        }
        return result; 
    }
    
    public Matrix Softmax() {
        Matrix result = new Matrix(getData().length, getData()[0].length);
        double[][] matrix = result.getData();

        for (int i = 0; i < getData()[0].length; i++) {
            double sumExp = 0.0;
            // Calcula la suma de las exponenciales para la fila i
            for (int j = 0; j < getData().length; j++) {
                sumExp += Math.exp(getData()[j][i]);
            }

            // Calcula la función softmax para cada elemento en la fila i
            for (int j = 0; j < getData().length; j++) {
                matrix[j][i] = Math.exp(getData()[j][i]) / sumExp;
            }
        }

        result.setData(matrix);
        return result;
    }

    
    
    public Matrix DataProduct(Matrix matrix){
        Matrix result = new Matrix (getData().length, getData()[0].length); 
            for (int i = 0; i < getData().length; i++){
                for (int k = 0; k < getData()[0].length; k++){
                    result.setValue(i, k, getValue(i,k) * matrix.getValue(i,k));  
                }
            }
        return result; 
    }
    
    public double DataSum(){
        double result = 0; 
        
        for (int i = 0; i < getData().length; i++){
            for (int k = 0; k < getData()[0].length; k++){
                result += getValue(i, k); 
            }
        }
        return result; 
    }
    
    public static Matrix Random(int nRows, int nCols){ 
        Random random = new Random(); 
        Matrix data = new Matrix(nRows, nCols); 
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                data.setValue(i,j, random.nextDouble()); // Genera un número aleatorio entre 0 y 1
            }
        }
        return data; 
    }
        public static Matrix RandomConstant(int nRows, int nCols){ 
        Random random = new Random(); 
        Matrix data = new Matrix(nRows, nCols); 
        double num;
        for (int i = 0; i < nRows; i++) {
            num = random.nextDouble();
            for (int j = 0; j < nCols; j++) {
                data.setValue(i,j, num); // Genera un número aleatorio entre 0 y 1
            }
        }
        return data; 
    }
    
    
       
}
