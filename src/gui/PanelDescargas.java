package gui;

import tasks.DownloadWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import static java.lang.Thread.sleep;

/**
 * @author danie
 * Clase para el panel dinamico cuando se empieza una descarga
 */
public class PanelDescargas extends JPanel {
    JPanel panelColaDescarga;
    public JProgressBar progressBar1;
    private JButton pausarBtn;
    private JButton eliminarBtn;
    private DownloadWorker download;
    public JLabel label;


    /**
     * Constructor
     * @param url introduce un parametro de tipo String
     * @param destino introduce un parametro de tipo File
     */
    public PanelDescargas(String url, File destino) {

        download = new DownloadWorker(url, destino, progressBar1, eliminarBtn, pausarBtn);

        setLayout(new BorderLayout());
        add(panelColaDescarga, BorderLayout.CENTER);
        setMaximumSize(new Dimension(23462346, 70));


        download.execute();

        download.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                boolean cancel = false;
                if (evt.getPropertyName().equals("state")) {
                    if (evt.getNewValue().equals(SwingWorker.StateValue.DONE) && (!cancel)) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                label.setText("Descarga finalizada");
                                eliminarBtn.setText("Eliminar archivo");
                                panelColaDescarga.repaint();
                                panelColaDescarga.revalidate();
                                pausarBtn.setVisible(false);
                            }
                        });
                    }
                }
            }
        });


        eliminarBtn.addActionListener(new ActionListener() {
            boolean cancelado = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                download.cancel(true);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        label.setText("");
                        pausarBtn.setEnabled(false);
                        if(e.getActionCommand().equals("Eliminar archivo")){
                            int x = JOptionPane.showConfirmDialog(null, "Desea eliminar el archivo", "Aviso", JOptionPane.YES_NO_OPTION);

                            if (x == JOptionPane.YES_NO_OPTION) {
                                label.setText("Archivo eliminado");
                                download.eliminarArchivo();
                                panelColaDescarga.removeAll();
                                panelColaDescarga.repaint();
                                panelColaDescarga.revalidate();
                                panelColaDescarga.setVisible(false);

                            } else if (x == JOptionPane.NO_OPTION) {
                                label.setText("Descarga eliminada de la cola");
                                panelColaDescarga.removeAll();
                                panelColaDescarga.repaint();
                                panelColaDescarga.revalidate();
                                panelColaDescarga.setVisible(false);
                            }
                        }
                    }
                });


            }
        });

        pausarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarBtnPausar("Reanudar");
                try {
                    download.pausarDescarga(true);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            label.setText("Descarga pausada");
                        }
                    });
                    if (e.getActionCommand().equals("Reanudar")) {
                        download.pausarDescarga(false);
                        modificarBtnPausar("Pausar");
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                download.execute();
                                label.setText("Descargando...");
                            }
                        });
                        //download.execute();
                    }

                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void modificarBtnPausar(String texto) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                pausarBtn.setText(texto);
            }
        });
    }

    public void modificarBtnEliminar(String texto) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                eliminarBtn.setText(texto);
            }
        });
    }




}
