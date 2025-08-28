package Neuralnetwork; 

public class MnistMatrix extends Matrix {
    
    //Matriz de etiquetas Ejemplo: (si en la imagen de data[0][] hay un 5, label[0] sera un 5 
    
    private int [] label;

    private int nRows ;
    private int nCols;

    public MnistMatrix(int nRows, int nCols) {
        super(nRows, nCols);
        this.nRows = nRows; 
        this.nCols = nCols;
       
    }

    
    //devuelve un elemento de label
    public int getLabel(int i ) {
        return label[i] ;
    }
    
    //Establece el arreglo label 
    public void setLabel(int [] labels, int numberOfLabels ) {
        this.label = new int[numberOfLabels]; 
        this.label  = labels; 
    }

    public int getNumberOfRows() {
        return nRows;
    }

    public int getNumberOfColumns() {
        return nCols;
    }
    
    //Devuelve el vector label 
    public int [] getLabelVector(){
        return label;
    }
    
    public Matrix OneHot(){
        Matrix result = new Matrix(10, getLabelVector().length);
        
            for (int i = 0; i < result.getData().length; i++) {
                for (int j = 0; j < result.getData()[0].length; j++) {
                    result.setValue(i, j, 0);
            }
        }

        // Asignar 1 en la posición correspondiente a la etiqueta
        for (int i = 0; i < getLabelVector().length; i++) {
            result.setValue(getLabel(i), i , 1); // getLabel(i) retorna el índice de la clase
        }

        return result;
    }

}
