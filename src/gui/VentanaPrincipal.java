package gui;

import com.formdev.flatlaf.FlatIntelliJLaf;
import tasks.Controlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VentanaPrincipal {
    private JPanel panel1;
    public JButton guardarBtn;
    public JTextField urlTxf;
    public JButton descargarBtn;
    private JPanel pnl;
    public JButton pararBtn;
    public JButton detenerBtn;
    public JPanel panelDinamico;

    /**
     * Clase principal que inicia el programa
     */
    private VentanaPrincipal(){

        JFrame frame = new JFrame("Download Manger 1.0");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Definir tamano inicial
        frame.setSize(new Dimension(700,400));
        //centrar ventana inicial
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        urlTxf.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                urlTxf.setText("");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                urlTxf.setText("");
            }

        });
    }

    /**
     * Metodo main
     * @param args
     */
    public static void main(String[] args) {

        SplashScreen splashScreen = new SplashScreen();
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        new Controlador(new VentanaPrincipal());
    }

    private void createUIComponents() {
        panelDinamico = new JPanel();
        BoxLayout layout = new BoxLayout(panelDinamico, BoxLayout.Y_AXIS);
        panelDinamico.setLayout(layout);
    }
}
