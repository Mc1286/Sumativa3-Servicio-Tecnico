/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package equipo4.interfaz;

import equipo4.DAO.PatronDaoClass; // Importas la clase DAO de tu amigo
import equipo4.modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private List<ServicioTecnico> catalogo;
    private JTable tablaServicios;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;

    public VentanaPrincipal() {
        setTitle("Sistema de Cotización - Servicio Técnico (Equipo 4)");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. CARGAMOS LOS DATOS USANDO EL DAO DE TU AMIGO
        catalogo = cargarCatalogoDesdeDao();

        // Configuración del layout principal
        setLayout(new BorderLayout());

        // Panel Superior: Búsqueda y filtrado
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelSuperior.add(new JLabel("Filtrar / Buscar:"));
        txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        panelSuperior.add(txtBuscar);
        panelSuperior.add(btnBuscar);
        add(panelSuperior, BorderLayout.NORTH);

        // Tabla Central para mostrar los servicios del catálogo
        String[] columnas = {"Tipo", "Código", "Descripción", "Tarifa Hora ($)", "Insumo / Repuesto ($)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaServicios = new JTable(modeloTabla);
        add(new JScrollPane(tablaServicios), BorderLayout.CENTER);

        // Llenar la tabla inicialmente con los datos que trajo el DAO
        actualizarTabla(catalogo);

        // Panel Inferior
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnSalir = new JButton("Salir");
        panelInferior.add(btnSalir);
        add(panelInferior, BorderLayout.SOUTH);

        btnSalir.addActionListener(e -> System.exit(0));
    }

    // Método auxiliar que usa el DAO de tu amigo y lo convierte a tus objetos del Modelo
    private List<ServicioTecnico> cargarCatalogoDesdeDao() {
        List<ServicioTecnico> listaTemporal = new ArrayList<>();
        PatronDaoClass dao = new PatronDaoClass();

        try {
            // Pedimos todos los registros en formato String[] al DAO
            List<String[]> datosCrudos = dao.obtenerTodosLosServicios();

            for (String[] d : datosCrudos) {
                String tipo = d[0].trim().toUpperCase();
                String codigo = d[1].trim();
                String descripcion = d[2].trim();
                double tarifaHora = Double.parseDouble(d[3].trim());
                double datoEspecifico = Double.parseDouble(d[4].trim());

                // Creamos los objetos usando tus clases del Modelo
                if (tipo.equals("PREVENTIVO")) {
                    listaTemporal.add(new ServicioPreventivo(codigo, descripcion, tarifaHora, datoEspecifico));
                } else if (tipo.equals("CORRECTIVO")) {
                    listaTemporal.add(new ServicioCorrectivo(codigo, descripcion, tarifaHora, datoEspecifico));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos desde el DAO: " + e.getMessage());
        }

        return listaTemporal;
    }

    private void actualizarTabla(List<ServicioTecnico> lista) {
        modeloTabla.setRowCount(0);
        for (ServicioTecnico s : lista) {
            String tipo = (s instanceof ServicioPreventivo) ? "PREVENTIVO" : "CORRECTIVO";
            String datoEsp = (s instanceof ServicioPreventivo)
                    ? String.valueOf(((ServicioPreventivo) s).getCostoInsumos())
                    : String.valueOf(((ServicioCorrectivo) s).getCostoRepuesto());

            Object[] fila = {tipo, s.getCodigo(), s.getDescripcion(), s.getTarifaHora(), datoEsp};
            modeloTabla.addRow(fila);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}
