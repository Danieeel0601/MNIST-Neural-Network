package Neuralnetwork; 
import java.util.Arrays;
import java.util.ArrayList; 
import  java.util.Scanner; 
import java.io.*; 

public class Backpropagation {
    
    public static ArrayList<Matrix> neurons = new ArrayList<>(); 
    
    public static void main(String[] args) throws IOException {
        //Se lee un archivo con imagenes y un archivo con etiquetas de entrenamiento
        MnistMatrix mnistMatrix = new MnistDataReader().readData(6000, "data/train-images.idx3-ubyte", "data/train-labels.idx1-ubyte");
        //Print 
        printMnistMatrix(mnistMatrix);
        double alpha = 0.1;
 
        //Se lee un archivo con imagenes de entrenamiento y sus etiquetas 
        gradient_descent(mnistMatrix, alpha , 3000, neurons);
        Scanner scanner = new Scanner(System.in); 
        
        System.out.println("La red esta entrenada, presione enter para probar en un nuevo set de datos");
        scanner.nextLine();
        
        mnistMatrix = new MnistDataReader().readData(500, "data/t10k-images.idx3-ubyte", "data/t10k-labels.idx1-ubyte");
        test(mnistMatrix, neurons); 
        System.out.println("Guardar?");
        if (scanner.nextLine().equals("1")){
            BDwrite("Neuronas.dat");
        }
        
    }
    
    //impresiones de prueba con el valor de los pixeles (0 es negro, 255 es blanco)
    private static void printMnistMatrix(MnistMatrix matrix) { 
        System.out.println("label: " + matrix.getLabel(0));
            for (int c = 0; c < matrix.getNumberOfColumns(); c++) {
                System.out.print(matrix.getValue(0,c) + " ");
                if (c % 28 == 27){
                    System.out.println();
                }
            }

        }
    public static void printMatrix(Matrix matrix) { 
            for (int c = 0; c < 784; c++) {
                System.out.print(matrix.getValue(c, 0) + " ");
                if (c % 28 == 27){
                    System.out.println();
                }
            }

        }
    
    
    public static void gradient_descent(MnistMatrix dataset, double alpha, 
            int iterations, ArrayList<Matrix> neurons){ 
//        Matrices de pesos y sesgos 

//        Se traspone el dataset para tener una muestra (iamgen) por columna 
        Matrix X = dataset.T();
//        Se inicializan las matrices que haran una prediccion con valores al azar entre [-5,5) 
//        Estas matrices se iran ajustando 
        Matrix W1 = Matrix.Random(10,784).ScalarSum(-0.5);
        Matrix b1 = Matrix.RandomConstant(10, 1).ScalarSum(-0.5);
        Matrix W2 = Matrix.Random(10,10).ScalarSum(-0.5); 
        Matrix b2 = Matrix.RandomConstant(10, 1).ScalarSum(-0.5);   
        
//        Inicializamos las matrices para llevar acabo la propagacion hacia
//        adelante y hacia atras
        Matrix Z1, A1, Z2, A2, one_hot_Y, dZ2, dW2, dZ1, dW1; 
        
//        Valores constantes (porque b1 y b2 son constantes hechas vectores) 
        double db2, db1; 
        
//        OneHot() devuelve una matrix 10 x m tal que en cada columna hay un 1
//        en la fila correspondiente al valor escrito en cada imagen
        one_hot_Y = dataset.OneHot(); 
        
//        m es el numero de muestras y a es -1 convertido a double 
        double m = X.getData()[0].length; 
        double a = -1;
        
        for (int i = 0; i < iterations; i++){
//            Aqui se lleva acabo una prediccion
//            Z1 = W1 x X + b1 
//            A1 = ReLU(Z1) 
//            Z1 = W2 x A1 + b2 
//            A2 = Softmax(Z2) 
            Z1 = W1.Dot(X).SumVector(b1);     
            A1 = Z1.ReLU();
            Z2 = W2.Dot(A1).SumVector(b2); 
            A2 = Z2.Softmax(); 

//            Aqui se lleva a cabo la propagacion hacia atras
//es una serie de derivadas parciales del error ( A2 - oneHot(Y) ) 
//con respecto a las matrices de la propagacion hacia adelante, se aplica recursividad 
//a las derivadas y se obtiene el cambio en el error con respecto a los pesos y biases (W1, W2, b1, b2)

//            dZ2 = A2 - oneHot(Y)
//            dW2 = 1/ m * dZ2 x A1.t 
//            db2 = 1/ m * suma(dZ2) es la suma de todos los elementos de dZ2
//            dZ1 = W2.t x dZ2 * ReLU_deriv(Z1) (* ES PRODUCTO ELEMENTO A ELEMENTO) 
//            dW1 = 1/ m *  dZ1 x X.t 
//            db1 = 1 /m * suma(dZ1)
            dZ2 = A2.Sum(one_hot_Y.Scalar(a)); 
            dW2 = dZ2.Dot(A1.T()).Scalar(1/m); 
            db2 = 1/m * dZ2.DataSum(); 
            dZ1 = W2.T().Dot(dZ2).DataProduct(Z1.ReLU_deriv());   
            dW1 = dZ1.Dot(dataset).Scalar(1/m);
            db1 = 1/m * dZ1.DataSum();  
            
//            Aqui se acualizan las matrices aplicando un factor de aprendizaje alpha y las derivadas calculadas
//Notemos que si el cambio en el error con respecto a los pesos o sesgos es muy grande, el cambio en las
//  matrices de pesos y sesgos sera grande
//            W1 = W1 - alpha * dW1
//            b1 = b1 - alpha * db1
//            W2 = W2 - alpha * dW2
//            b2 = b2 - alpha * db2   
//todo lo anterior considerando que tenemos una especie de broadcasting entre matrices y constantes
            W1 = W1.Sum(dW1.Scalar(-alpha));
            b1 = b1.ScalarSum(-alpha * db1);
            W2 = W2.Sum(dW2.Scalar(-alpha));
            b2 = b2.ScalarSum(-alpha * db2);

//           Se imprimen resultados cada 10 iteraciones
            if (i % 10 == 0){
                System.out.println("Iteration: " + (i+1));
                System.out.println("Prediction: " + getPredictions(A2)[i % 1000]);
                System.out.println("Accuracy: " + getAccuracy(getPredictions(A2), dataset.getLabelVector()) + "%");

            }
        }
        
        neurons.add(W1); 
        neurons.add(b1); 
        neurons.add(W2); 
        neurons.add(b2); 
        
        
    }

    
    public static int[] getPredictions(Matrix A2) {
       int numSamples = A2.getData()[0].length; // Número de columnas (muestras)
       int[] predictions = new int[numSamples]; // Array para almacenar las predicciones

       for (int col = 0; col < numSamples; col++) {
           int maxIndex = 0; // Índice del máximo valor en la columna
           double maxVal = A2.getValue(0, col); // Inicializar con el primer valor de la columna

           // Buscar el índice del valor máximo en la columna
           for (int row = 1; row < A2.getData().length; row++) {
               if (A2.getValue(row, col) > maxVal) {
                   maxVal = A2.getValue(row, col);
                   maxIndex = row;
               }
           }


           predictions[col] = maxIndex; // Almacenar la predicción
       }

       return predictions;
   }

    
    public static double getAccuracy(int[] predictions, int[] Y) {
        if (predictions.length != Y.length) {
            throw new IllegalArgumentException("El tamaño de las predicciones y de Y debe ser igual.");
        }

        int correct = 0; // Contador para predicciones correctas

        // Imprimir las predicciones y los valores reales (similar a print en Python)
        System.out.println("Predictions: " + Arrays.toString(predictions));
        System.out.println("Y: " + Arrays.toString(Y));

        // Contar cuántas predicciones coinciden con Y
        for (int i = 0; i < predictions.length; i++) {
                if (predictions[i] == Y[i]) {
                  correct++;
            }

            
        }
        correct *= 100;
        // Calcular la precisión
        return (double) correct / (Y.length) ;
    }
    
    public static void test(MnistMatrix dataset, ArrayList<Matrix> neurons ) {
        
        Matrix X = dataset.T();
                
        Matrix Z1 = neurons.get(0).Dot(X).SumVector(neurons.get(1));     
        Matrix A1 = Z1.ReLU();
        Matrix Z2 = neurons.get(2).Dot(A1).SumVector(neurons.get(3)); 
        Matrix A2 = Z2.Softmax(); 
        
        System.out.println(getAccuracy(getPredictions(A2), dataset.getLabelVector()) + "%");
    }
    
    

    public static void BDwrite(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            
            oos.writeObject(neurons); 
            oos.close();
// Serializa el objeto actual y lo guarda
            System.out.println("Objeto guardado correctamente en " + filename);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }
    }
    
    
}
    
