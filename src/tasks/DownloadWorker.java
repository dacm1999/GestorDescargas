package tasks;

import gui.PanelDescargas;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author danie
 */
public class DownloadWorker extends SwingWorker<Void, Integer> {

    // La URL del fichero a descargar
    private String urlFichero;
    // La ruta del disco donde se descargará
    private File destino;
    private JProgressBar progressBar;
    private boolean estado;

    /**
     * Constructor
     * @param urlFichero recibe un parametro de tipo String
     * @param rutaFichero recibe un parametro de tipo File
     * @param progressBar recibe un parametro de tipo JProgressBar
     * @param detenerBtn recibe un parametro de tipo JButton
     * @param eliminarBtn recibe un parametro de tipo JButton
     */
    public DownloadWorker(String urlFichero, File rutaFichero, JProgressBar progressBar, JButton detenerBtn, JButton eliminarBtn) {
        this.urlFichero = urlFichero;
        this.destino = rutaFichero;
        this.progressBar = progressBar;

        if(isCancelled()){
            firePropertyChange("Eliminar", false,false);
        }
    }

    /**
     * Metodo encargado de ejecutar las descargas en segundo plano
     * @return
     * @throws MalformedURLException
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Override
    protected Void doInBackground() throws MalformedURLException, FileNotFoundException, IOException {

        downloadFiles(urlFichero, destino);
        if (isCancelled()) {
            firePropertyChange("Eliminar", false, true);
        }
        return null;
    }

    /**
     * Metodo encargado de eliminar archivos descargados
     */
    public void eliminarArchivo() {
        destino.delete();
    }

    /**
     * Metodo encargado de pausar las descargas
     * @param pausa
     * @throws InterruptedException
     */
    public void pausarDescarga(boolean pausa) throws InterruptedException {

        estado = pausa;
        if (!pausa) {
            reanudar();
        }
    }

    /**
     *
     * @param valores intermediate results to process
     *
     */
    @Override
    protected void process(List<Integer> valores) {
        if (!valores.isEmpty()) {
            progressBar.setValue((Integer) valores.get(valores.size() - 1));
        }
    }

    /**
     * Metodo para reanudar las descargas
     */
    private synchronized void reanudar() {
        notify();
    }

    /**
     * Metodo para pausar descargas
     * @throws InterruptedException
     */
    private synchronized void pausar() throws InterruptedException {
        wait();
    }


    /**
     *
     * @param URL
     * @param destino
     * @throws MalformedURLException
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void downloadFiles(String URL, File destino) throws MalformedURLException, FileNotFoundException, IOException {

        String ruta = destino.toString();

        URL url = new URL(URL);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

        long fileSize = conexion.getContentLengthLong();
        BufferedInputStream is = new BufferedInputStream(conexion.getInputStream());
        FileOutputStream os = new FileOutputStream(ruta);
        BufferedOutputStream bout = new BufferedOutputStream(os, 1024);
        byte[] buffer = new byte[1024];
        long download = 0;
        int read = 0;
        int descarga = 0;
        while ((read = is.read(buffer, 0, 1024)) >= 0 && !isCancelled()) {
            while(estado){
                try {
                    pausar();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            bout.write(buffer, 0, read);
            download += read;
            long porcentaje = download * 100 / fileSize;
            publish((int) porcentaje);

        }
        bout.close();
        is.close();

    }
}