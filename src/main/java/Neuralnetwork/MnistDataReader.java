package  Neuralnetwork; 

import java.io.*;

public class MnistDataReader  {

    public MnistMatrix readData(int numExamples, String dataFilePath, String labelFilePath) throws IOException {
        //Se crea un lector de entradas al archivo de los datos
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFilePath)));
        
        //No preocuparse por el numero magico
        
        int magicNumber = dataInputStream.readInt();
        //numero de imagenes en el dataset
        int numberOfItems = dataInputStream.readInt();
        //numero de filas y columnas por imagen
        int nRows = dataInputStream.readInt();
        int nCols = dataInputStream.readInt();

        System.out.println("magic number is " + magicNumber);
        System.out.println("number of items is " + numberOfItems);
        System.out.println("number of rows is: " + nRows);
        System.out.println("number of cols is: " + nCols);
        
        //Lector de entradas para la entrada de labels (etiquetas) de cada imagen
        DataInputStream labelInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(labelFilePath)));
        
        int labelMagicNumber = labelInputStream.readInt();
        int numberOfLabels = labelInputStream.readInt();

        System.out.println("labels magic number is: " + labelMagicNumber);
        System.out.println("number of labels is: " + numberOfLabels);
        
        //creamos la matriz construida
        MnistMatrix data = new MnistMatrix(numExamples, nRows * nCols);
        
        assert numberOfItems == numberOfLabels;
        
        int [] labels = new int [numExamples]; 
        
        //A la matriz de datos se le asigna un label al lector, y los datos de
        //una imagen por cada fila del arreglo data[][]
        for(int i = 0; i < numExamples; i++) {
            labels[i] = labelInputStream.readUnsignedByte(); 

            for (int c = 0; c < nRows * nCols; c++) {
                if (dataInputStream.readUnsignedByte() > 0){
                    data.setValue(i, c, (double) 1);
                }
                else {
                    data.setValue(i, c, 0);
                }
            }      
        }
        data.setLabel(labels, numberOfLabels);
        dataInputStream.close();
        labelInputStream.close();
        return data;
    }
}
