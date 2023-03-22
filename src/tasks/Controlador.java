package tasks;

import gui.PanelDescargas;
import gui.VentanaPrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

/**
 * @author danie
 * Clase que maneja todos los listeners de los componentes
 */
public class Controlador implements ActionListener {

    private DownloadWorker downloader;
    private VentanaPrincipal ventanaPrincipal;
    private File destinoDescarga;
    private String textURL;
    JPanel panelDescargas;


    /**
     * Constructor
     * @param ventanaPrincipal recibe por parametro el objeto VentanaPrincipal el cual contiene toda la GUI
     */
    public Controlador(VentanaPrincipal ventanaPrincipal) {

        this.ventanaPrincipal = ventanaPrincipal;
        vincularListeners(this);
    }

    /**
     * Vincula los listeners a los componentes
     * @param listener
     */
    private void vincularListeners(ActionListener listener) {
        ventanaPrincipal.guardarBtn.addActionListener(listener);
        ventanaPrincipal.descargarBtn.addActionListener(listener);
        ventanaPrincipal.urlTxf.addActionListener(listener);
        ventanaPrincipal.detenerBtn.addActionListener(listener);
    }

    /**
     * Realiza las instrucciones de los botones Guardar, Descargar y Eliminar descargas
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String comandos = e.getActionCommand();

        textURL = ventanaPrincipal.urlTxf.getText();

        switch (comandos){
            case "Guardar":{
                JFileChooser selector = new JFileChooser();
                int respuesta = selector.showSaveDialog(null);
                if (respuesta == JFileChooser.APPROVE_OPTION) {
                    destinoDescarga = selector.getSelectedFile();
                }

                ventanaPrincipal.urlTxf.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        ventanaPrincipal.urlTxf.setText("");
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        ventanaPrincipal.urlTxf.setText("");
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }

                });
                break;
            }
            case "Descargar":{
                if(destinoDescarga == null){
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una ruta para guardar la descarga ", "Error",   JOptionPane.ERROR_MESSAGE);
                }else if(ventanaPrincipal.urlTxf.getText().equals("") || ventanaPrincipal.urlTxf.getText().equals(null) || ventanaPrincipal.urlTxf.getText().equals("Introduce URL")){
                    JOptionPane.showMessageDialog(null, "Introduce URL ", "Error",   JOptionPane.ERROR_MESSAGE);
                }else{

                    PanelDescargas panel = new PanelDescargas(ventanaPrincipal.urlTxf.getText(), destinoDescarga);
                    ventanaPrincipal.panelDinamico.add(panel);
                    ventanaPrincipal.panelDinamico.revalidate();
                    //ventanaPrincipal.panelDinamico.remove(panel);
                    destinoDescarga = null;
                    ventanaPrincipal.urlTxf.setText("");
                }

                break;
            }

            case "Eliminar Descargas":{
                ventanaPrincipal.panelDinamico.removeAll();
                ventanaPrincipal.panelDinamico.repaint();
                ventanaPrincipal.panelDinamico.revalidate();
                break;
            }
        }
    }
}
