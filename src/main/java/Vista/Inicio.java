/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;
import java.util.ArrayList;
import Neuralnetwork.Matrix;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
/**
 *
 * @author danie
 */
public class Inicio {
    
    public static Matrix input = new Matrix(784, 1);
    public static ArrayList<Matrix> neurons = new ArrayList<>();
    
    public static void BDread(){
        String nombreArchivo = "Neuronas.dat"; 
        try{
            FileInputStream archivo = new FileInputStream(nombreArchivo);
            ObjectInputStream entrada = new ObjectInputStream(archivo);
            
            neurons = (ArrayList<Matrix>) entrada.readObject();
            entrada.close();
        }
        catch (IOException e){
            System.out.println("Imposible abrir el archivo par aleer");
        }
        catch (ClassNotFoundException e){
            System.out.println("El tipo de dato a convertir es incorrecto");
        }
    }
    
    
    public static void main(String[] args){
        
        BDread();
        FrmInicio inicio = new FrmInicio(); 
        inicio.setVisible(true);
        
    }
}
