package equipo4.DAO;

import equipo4.DAO.Dao;
import equipo4.factory.ServicioFactory;
import equipo4.modelo.ServicioCorrectivo;
import equipo4.modelo.ServicioPreventivo;
import equipo4.modelo.ServicioTecnico;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author LBC_14
 */
public class PatronDaoClass implements Dao {
    
    // Ruta donde se lee y guarda el archivo de texto
    private static final String RUTA = "datos/datos.txt";
    
    @Override
    public String[] CargarServicioTecnico(String idB) throws Exception {
        try (BufferedReader lineas = new BufferedReader(new FileReader(RUTA, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = lineas.readLine()) != null) {
                String[] dato = linea.split(";");
                if (dato.length > 1 && dato[1].trim().equalsIgnoreCase(idB.trim())) {
                    return dato;
                }
            }
            return null;
        }
    }
    
    @Override
    public List<String[]> obtenerTodosLosServicios() throws Exception {
        List<String[]> listaServicios = new ArrayList<>();
        File archivo = new File(RUTA);
        if (!archivo.exists()) return listaServicios;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datosServicio = linea.split(";", -1);
                if (datosServicio.length == 5) {
                    listaServicios.add(datosServicio);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
        }
        return listaServicios;
    }

    // Método que llama la VentanaPrincipal para llenar la tabla
    public List<ServicioTecnico> cargarServicios() {
        List<ServicioTecnico> lista = new ArrayList<>();
        try {
            List<String[]> brutos = obtenerTodosLosServicios();
            for (String[] p : brutos) {
                try {
                    String tipo = p[0].trim();
                    String codigo = p[1].trim();
                    String desc = p[2].trim();
                    double tarifa = Double.parseDouble(p[3].trim());
                    double datoEsp = Double.parseDouble(p[4].trim().replace(",", "."));

                    ServicioTecnico s = ServicioFactory.crearServicio(tipo, codigo, desc, tarifa, datoEsp);
                    if (s != null) {
                        lista.add(s);
                    }
                } catch (Exception e) {
                    // Ignora líneas con formato incorrecto
                }
            }
        } catch (Exception e) {
            System.err.println("Error al procesar servicios: " + e.getMessage());
        }
        return lista;
    }

    // Método que llama la VentanaPrincipal al Agregar o Eliminar para persistir
    public void guardarServicios(List<ServicioTecnico> lista) throws IOException {
        File archivo = new File(RUTA);
        if (archivo.getParentFile() != null) {
            archivo.getParentFile().mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, StandardCharsets.UTF_8))) {
            for (ServicioTecnico s : lista) {
                String tipo = (s instanceof ServicioPreventivo) ? "PREVENTIVO" : "CORRECTIVO";
                double datoEsp = (s instanceof ServicioPreventivo)
                        ? ((ServicioPreventivo) s).getCostoInsumos()
                        : ((ServicioCorrectivo) s).getCostoRepuesto();

                String linea = String.format(java.util.Locale.US, "%s;%s;%s;%.2f;%.2f",
                        tipo, s.getCodigo(), s.getDescripcion(), s.getTarifaHora(), datoEsp);
                bw.write(linea);
                bw.newLine();
            }
        }
    }
}
