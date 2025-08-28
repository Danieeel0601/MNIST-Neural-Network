package Vista;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author pablo
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Neuralnetwork.Matrix; 
import Neuralnetwork.Backpropagation;
import java.util.Arrays;


public class FrmPixelPaintApp extends JPanel {
    private static final int PIXEL_SIZE = 20; // Size of each cell
    private static final int GRID_SIZE = 28; // 28x28 grid
    private Color[][] pixels = new Color[GRID_SIZE][GRID_SIZE];
    private boolean isDrawing = false; // Track if the mouse is pressed

    private JFrame parentFrame; // Referencia al JFrame que contiene este panel
    


    public FrmPixelPaintApp(JFrame parentFrame) {
       this.parentFrame = parentFrame;
        //jButton1 = new javax.swing.JButton();
        setPreferredSize(new Dimension(GRID_SIZE * PIXEL_SIZE, GRID_SIZE * PIXEL_SIZE));
        setBackground(Color.WHITE);
          setLayout(new BorderLayout());

        // Initialize all pixels to white
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                pixels[i][j] = Color.WHITE;
            }
        }

        // Combined mouse listener to handle both pressing and dragging
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isDrawing = true;
                fillPixel(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDrawing = false;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDrawing) {
                    fillPixel(e.getX(), e.getY());
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        
        JPanel buttonPanel = new JPanel(new FlowLayout()); // Usar FlowLayout para centrar el botón
        JButton limpiar = new JButton("Limpiar");
        JButton guardar= new JButton("Guardar");
        JButton cerrar= new JButton("Cerrar");
        
        
        limpiar.addActionListener(e -> {
           
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) 
                    pixels[i][j] = Color.WHITE;
                
            }
            repaint(); // Repinta el área de dibujo
        });
        buttonPanel.add(limpiar); // Agregar el botón al panel
        buttonPanel.add(guardar); // Agregar el botón al panel
        buttonPanel.add(cerrar);
        add(buttonPanel, BorderLayout.SOUTH); // Agregar el panel de botones en la parte inferior
        
        
        
         guardar.addActionListener(e -> {
            //int array [];

            int k=0;//Contador de array
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    Color colorDePixel = getPixelColor(j,i);
                    if(colorDePixel.equals(Color.black)){
                        Inicio.input.setValue(k,0, 1);
                    }else{
                        Inicio.input.setValue(k,0,0);
                    }
                    k++;
                }
            }
            repaint(); // Repinta el área de dibujo
            

            Matrix X = Inicio.input;

            Matrix Z1 = Inicio.neurons.get(0).Dot(X).SumVector(Inicio.neurons.get(1));     
            Matrix A1 = Z1.ReLU();
            Matrix Z2 = Inicio.neurons.get(2).Dot(A1).SumVector(Inicio.neurons.get(3)); 
            Matrix A2 = Z2.Softmax(); 

            
            int[] predictionVector = Backpropagation.getPredictions(A2);

            int respuesta = predictionVector[0];
            
            JOptionPane.showMessageDialog(parentFrame, "La red neuronal lee un : " + respuesta, "RESPUESTA", JOptionPane.INFORMATION_MESSAGE );
            //image processing aqui
            
            

            
         });
         cerrar.addActionListener(e -> {
            if (parentFrame != null) {
            parentFrame.dispose(); // Cierra la ventana que contiene el panel
        }
         });
    }
    
    
    
    

    // Method to color a specific pixel based on mouse coordinates
    private void fillPixel(int x, int y) {
        int gridX = x / PIXEL_SIZE;
        int gridY = y / PIXEL_SIZE;

        // Debugging print to verify when pixels are being filled
        
        //System.out.println("Filling pixel at (" + gridX + ", " + gridY + ")");

        if (gridX >= 0 && gridY >= 0 && gridX < GRID_SIZE && gridY < GRID_SIZE) {
            try{
                 //if(gridY>=0||gridX>=0||gridX<=27||gridY<=27)pixels[gridX][gridY-1] = Color.BLACK; // Set to black for now
                if(gridY>=0||gridX>=0||gridX<=27||gridY<=27)pixels[gridX][gridY] = Color.BLACK; // Set to black for now
                //if(gridY>=0)pixels[gridX][gridY+1] = Color.BLACK; // Set to black for now
                if(gridY<=27)pixels[gridX][gridY-1] = Color.BLACK; // Set to black for now
                if(gridX>=0)pixels[gridX+1][gridY] = Color.BLACK; // Set to black for now
                //if(gridX<=27)pixels[gridX-1][gridY] = Color.BLACK; // Set to black for now
                repaint();
            }catch(ArrayIndexOutOfBoundsException e1){
                repaint();
            }   
        }
    }
    
    public Color getPixelColor(int x, int y) {
        if (x >= 0 && y >= 0 && x < GRID_SIZE && y < GRID_SIZE) {
            return pixels[x][y];
        } else {
            return null; // Retorna null si las coordenadas están fuera del rango
        }
    }
    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the grid
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                g.setColor(pixels[i][j]);
                g.fillRect(i * PIXEL_SIZE, j * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
                g.setColor(Color.GRAY);
                g.drawRect(i * PIXEL_SIZE, j * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE); // Optional grid lines
            }
        }
        
        
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("28x28 Pixel Paint");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new FrmPixelPaintApp(frame));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());

    }
    
    
     //public javax.swing.JButton jButton1;
     //jButton1.setVisible();
}   
