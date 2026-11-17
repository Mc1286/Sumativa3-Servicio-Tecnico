/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipo4.Logica;

import equipo4.DAO.PatronDaoClass;
import equipo4.factory.ServicioFactory;
import equipo4.modelo.ServicioTecnico;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author juancortez
 */
public class ServicioControlador {
    private final PatronDaoClass dao;
    private List<ServicioTecnico> listaServicios;

    public ServicioControlador() {
        this.dao = new PatronDaoClass();
        this.listaServicios = new ArrayList<>();
        cargarDesdeDao();
    }

    public void cargarDesdeDao() {
        listaServicios.clear();
        try {
            List<String[]> brutos = dao.obtenerTodosLosServicios();
            for (String[] p : brutos) {
                ServicioTecnico s = ServicioFactory.crearServicio(
                    p[0].trim(), p[1].trim(), p[2].trim(), 
                    Double.parseDouble(p[3].trim()), 
                    Double.parseDouble(p[4].trim().replace(",", "."))
                );
                if (s != null) listaServicios.add(s);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }
    }

    public List<ServicioTecnico> getListaServicios() {
        return listaServicios;
    }

    public void agregarServicio(String tipo, String codigo, String desc, double tarifa, double datoEsp) throws Exception {
        if (codigo.trim().isEmpty() || desc.trim().isEmpty() || codigo.contains(";") || desc.contains(";")) {
            throw new IllegalArgumentException("Complete código y descripción (No se permite ';').");
        }

        for (ServicioTecnico s : listaServicios) {
            if (s.getCodigo().equalsIgnoreCase(codigo.trim())) {
                throw new IllegalArgumentException("El código ya existe en el catálogo.");
            }
        }

        if (tarifa <= 0 || datoEsp < 0) {
            throw new IllegalArgumentException("La tarifa debe ser mayor a 0 y el costo adicional mayor o igual a 0.");
        }

        ServicioTecnico nuevo = ServicioFactory.crearServicio(tipo, codigo.trim(), desc.trim(), tarifa, datoEsp);
        if (nuevo != null) {
            listaServicios.add(nuevo);
        }
    }

    public void eliminarServicio(String codigo) {
        listaServicios.removeIf(s -> s.getCodigo().equalsIgnoreCase(codigo));
    }

    public List<ServicioTecnico> filtrar(String criterio) {
        String q = criterio.trim().toLowerCase();
        if (q.isEmpty()) return listaServicios;
        
        List<ServicioTecnico> filtrados = new ArrayList<>();
        for (ServicioTecnico s : listaServicios) {
            if (s.getCodigo().toLowerCase().contains(q) || s.getDescripcion().toLowerCase().contains(q)) {
                filtrados.add(s);
            }
        }
        return filtrados;
    }

    public double[] calcularCotizacion(String codigo, int horas, boolean esBeneficio) throws Exception {
        if (horas < 1 || horas > 24) {
            throw new IllegalArgumentException("Las horas deben ser entre 1 y 24.");
        }

        ServicioTecnico servicio = null;
        for (ServicioTecnico s : listaServicios) {
            if (s.getCodigo().equalsIgnoreCase(codigo)) {
                servicio = s;
                break;
            }
        }

        if (servicio == null) {
            throw new IllegalArgumentException("Servicio no encontrado.");
        }

        double importeBase = servicio.calcularImporte(horas); // Polimorfismo
        double descuento = esBeneficio ? importeBase * 0.10 : 0.0;
        double total = importeBase - descuento;

        return new double[]{importeBase, descuento, total};
    }
}
