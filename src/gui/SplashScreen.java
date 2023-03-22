package gui;

import javax.swing.*;

/**
 * @author danie
 * Clase para pantalla de inicio
 */
public class SplashScreen extends JDialog {

    private JLabel logo;
    private JPanel panelLogo;
    private JProgressBar progressBar1;
    private int tiempoCarga =2;
    private  JFrame frame;

    public SplashScreen() {

        setContentPane(panelLogo);
        setModal(false);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        run();
    }

    public void run(){
        int contador = 0;
        while (contador < tiempoCarga){
            contador++;
            progressBar1.setValue(contador * 100 / tiempoCarga);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        setVisible(false);
    }

    public void setProgressBar1(JProgressBar progressBar1) {
        this.progressBar1 = progressBar1;
        progressBar1.setValue(0);
    }

}
