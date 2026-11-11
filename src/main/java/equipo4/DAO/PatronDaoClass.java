package equipo4.DAO;

import equipo4.DAO.Dao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    
    private static final String RUTA = "data/datos.txt";
    
    @Override
    public String[] CargarServicioTecnico(String idB) throws Exception {
        
        try (BufferedReader lineas = new BufferedReader(new FileReader(RUTA))) {
            String linea;
            
            while ((linea = lineas.readLine()) != null) {
                String[] dato = linea.split(";");
                
                if (dato[1].equals(idB)) {
                    return dato;
                }
            }
            return null;
        }
    }
    
    @Override
    public List<String[]> obtenerTodosLosServicios() throws  Exception {
        List<String[]> listaServicios = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA))){
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()){
                    continue;
                }
                String[] datosServicio = linea.split(";");
                if (datosServicio.length == 5) {
                    listaServicios.add(datosServicio);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de datos: " + e.getMessage());
        }
        
        return listaServicios;
    }
}
