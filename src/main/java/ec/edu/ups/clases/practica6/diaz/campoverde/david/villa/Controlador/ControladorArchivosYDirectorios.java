/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.clases.practica6.diaz.campoverde.david.villa.Controlador;


import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author HP
 */
public class ControladorArchivosYDirectorios {

    public boolean crearArchivo(String url, String nombre) {
        try {
            String urlFinal = url + "/" + nombre + ".txt";
            File archivo = new File(urlFinal);
            return archivo.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void eliminarArchivo(String url) {
        File archivo = new File(url);
        if (archivo.isDirectory()) {
            File[] archivos = archivo.listFiles();
            if (archivos != null) {
                for (File ar : archivos) {
                    eliminarArchivo(ar.getAbsolutePath());
                }
            }
        }
        if (!archivo.delete()) {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el archivo");
        }
    }

    public void renombrarArchivo(String url, String urlren, String renombrar) {
        File archivoR = new File(urlren);
        if (archivoR.isDirectory()) {
            File archivoD = new File(url + "/" + renombrar);
            archivoR.renameTo(archivoD);
        } else if (archivoR.isFile()) {
            File archivoD = new File(url + "/" + renombrar + ".txt");
            archivoR.renameTo(archivoD);
        }
    }

    public void crearDirectorio(String ruta, String nombreDirectorio) throws IOException {
        String rutaCompleta = ruta + SEPARADOR_RUTA + nombreDirectorio;

        File directorio = new File(rutaCompleta);

        if (directorio.exists()) {
            throw new IOException("El directorio ya existe: " + directorio.getAbsolutePath());
        } else {
            boolean exito = directorio.mkdir();

            if (exito) {
                System.out.println("Directorio creado: " + directorio.getAbsolutePath());
            } else {
                throw new IOException("No se pudo crear el directorio: " + directorio.getAbsolutePath());
            }
        }
    }

    public void listarDirectorios(File directorio, DefaultMutableTreeNode nodoPadre) throws IOException {
        if (directorio.isDirectory()) {
            File[] archivos = directorio.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.isDirectory()) {
                        DefaultMutableTreeNode nodoDirectorio = new DefaultMutableTreeNode(archivo.getName());
                        nodoPadre.add(nodoDirectorio);
                        listarDirectorios(archivo, nodoDirectorio);
                    }
                }
            }
        } else {
            throw new IOException("La ruta no es un directorio: " + directorio.getAbsolutePath());
        }
    }

    public void listarDirectoriosOcultos(File directorio, DefaultMutableTreeNode nodoPadre) {
        if (directorio.isDirectory()) {
            File[] archivos = directorio.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.isDirectory() && archivo.isHidden()) {
                        DefaultMutableTreeNode nodoDirectorio = new DefaultMutableTreeNode(archivo.getName());
                        nodoPadre.add(nodoDirectorio);
                        listarDirectoriosOcultos(archivo, nodoDirectorio);
                    }
                }
            }
        }
    }

    public void listarArchivosOcultos(File directorio, DefaultMutableTreeNode nodoPadre) {
        if (directorio.isDirectory()) {
            File[] archivos = directorio.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.isFile() && archivo.isHidden()) {
                        DefaultMutableTreeNode nodoArchivo = new DefaultMutableTreeNode(archivo.getName());
                        nodoPadre.add(nodoArchivo);
                    }
                }
            }
        }
    }

    public void listarTodo(File directorio, DefaultMutableTreeNode nodoPadre) {
        if (directorio.isDirectory()) {
            File[] archivos = directorio.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.isDirectory()) {
                        DefaultMutableTreeNode nodoDirectorio = new DefaultMutableTreeNode(archivo.getName());
                        nodoPadre.add(nodoDirectorio);
                        listarTodo(archivo, nodoDirectorio);
                    } else {
                        DefaultMutableTreeNode nodoArchivo = new DefaultMutableTreeNode(archivo.getName());
                        nodoPadre.add(nodoArchivo);
                    }
                }
            }
        }
    }

    public void eliminarArchivoODirectorio(String rutaArchivoODirectorio) throws IOException {
        File archivoODirectorio = new File(rutaArchivoODirectorio);
        if (archivoODirectorio.exists()) {
            if (archivoODirectorio.isDirectory()) {
                eliminarDirectorio(archivoODirectorio);
            } else {
                if (archivoODirectorio.delete()) {
                    System.out.println("Archivo eliminado: " + rutaArchivoODirectorio);
                } else {
                    throw new IOException("No se pudo eliminar el archivo: " + rutaArchivoODirectorio);
                }
            }
        } else {
            throw new IOException("El archivo o directorio no existe: " + rutaArchivoODirectorio);
        }
    }

    private void eliminarDirectorio(File directorio) throws IOException {
        File[] archivos = directorio.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    eliminarDirectorio(archivo);
                } else {
                    if (!archivo.delete()) {
                        throw new IOException("No se pudo eliminar el archivo: " + archivo.getAbsolutePath());
                    }
                }
            }
        }
        if (!directorio.delete()) {
            throw new IOException("No se pudo eliminar el directorio: " + directorio.getAbsolutePath());
        }
        System.out.println("Directorio eliminado: " + directorio.getAbsolutePath());
    }

    public void renombrarArchivoODirectorio(String ruta, String nuevoNombre) throws IOException {
        File archivoODirectorio = new File(ruta);
        String nuevaRuta = archivoODirectorio.getParent() + File.separator + nuevoNombre;
        File archivoODirectorioNuevo = new File(nuevaRuta);
        if (!archivoODirectorio.renameTo(archivoODirectorioNuevo)) {
            throw new IOException("No se pudo renombrar el archivo o directorio: " + ruta);
        }
    }
    private static final String SEPARADOR_RUTA = "/";
}
