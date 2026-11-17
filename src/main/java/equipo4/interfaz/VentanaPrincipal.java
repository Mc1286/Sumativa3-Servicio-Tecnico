/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package equipo4.interfaz;

import equipo4.Logica.ServicioControlador;
import equipo4.modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private ServicioControlador controlador;
    private DefaultTableModel modeloTabla;
    private JTable tabla;

    private JTextField txtCodigo, txtDescripcion, txtTarifaHora, txtDatoEspecifico, txtFiltro, txtHoras;
    private JComboBox<String> cbTipo;
    private JRadioButton rbNormal, rbBeneficio;
    private JLabel lblResultado;

    public VentanaPrincipal() {
        setTitle("Cotización de Servicio Técnico - Equipo 4");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        controlador = new ServicioControlador();

        initComponents();
        actualizarTabla(controlador.getListaServicios());
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // Panel Norte: Filtro
        JPanel pnlNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlNorte.setBorder(BorderFactory.createTitledBorder("Buscar o Filtrar"));
        txtFiltro = new JTextField(20);
        JButton btnFiltrar = new JButton("Filtrar");
        JButton btnMostrarTodos = new JButton("Ver Todos");
        pnlNorte.add(new JLabel("Código / Descripción:"));
        pnlNorte.add(txtFiltro);
        pnlNorte.add(btnFiltrar);
        pnlNorte.add(btnMostrarTodos);
        add(pnlNorte, BorderLayout.NORTH);

        // Panel Central: Tabla + Formulario
        modeloTabla = new DefaultTableModel(new Object[]{"Tipo", "Código", "Descripción", "Tarifa/Hora", "Insumo/Repuesto"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Catálogo de Servicios"));

        JPanel pnlDerecho = new JPanel(new GridBagLayout());
        pnlDerecho.setBorder(BorderFactory.createTitledBorder("Gestión y Cotización"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; pnlDerecho.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1; cbTipo = new JComboBox<>(new String[]{"PREVENTIVO", "CORRECTIVO"}); pnlDerecho.add(cbTipo, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; pnlDerecho.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1; txtCodigo = new JTextField(12); pnlDerecho.add(txtCodigo, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; pnlDerecho.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; txtDescripcion = new JTextField(12); pnlDerecho.add(txtDescripcion, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; pnlDerecho.add(new JLabel("Tarifa Hora ($):"), gbc);
        gbc.gridx = 1; txtTarifaHora = new JTextField(12); pnlDerecho.add(txtTarifaHora, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; pnlDerecho.add(new JLabel("Insumo/Repuesto ($):"), gbc);
        gbc.gridx = 1; txtDatoEspecifico = new JTextField(12); pnlDerecho.add(txtDatoEspecifico, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; pnlDerecho.add(new JLabel("Horas (1-24):"), gbc);
        gbc.gridx = 1; txtHoras = new JTextField(12); pnlDerecho.add(txtHoras, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; pnlDerecho.add(new JLabel("Modalidad:"), gbc);
        JPanel pnlMod = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rbNormal = new JRadioButton("Normal", true);
        rbBeneficio = new JRadioButton("Beneficio (10%)");
        ButtonGroup bgMod = new ButtonGroup();
        bgMod.add(rbNormal); bgMod.add(rbBeneficio);
        pnlMod.add(rbNormal); pnlMod.add(rbBeneficio);
        gbc.gridx = 1; pnlDerecho.add(pnlMod, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JButton btnAgregar = new JButton("Adicionar Registro");
        pnlDerecho.add(btnAgregar, gbc);

        // --- AQUÍ ESTABA EL DETALLE DE LA FILA ---
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JButton btnEliminar = new JButton("Eliminar Seleccionado");
        pnlDerecho.add(btnEliminar, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JButton btnCalcular = new JButton("Cotizar / Calcular Importe");
        pnlDerecho.add(btnCalcular, gbc);

        JSplitPane splitCenter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollTabla, pnlDerecho);
        splitCenter.setDividerLocation(560);
        add(splitCenter, BorderLayout.CENTER);

        // Panel Sur: Estado
        JPanel pnlSur = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        lblResultado = new JLabel("Estado: Listo. Registros cargados: " + controlador.getListaServicios().size());
        pnlSur.add(lblResultado);
        add(pnlSur, BorderLayout.SOUTH);

        // Eventos correctamente asignados después de instanciar los componentes
        btnAgregar.addActionListener(e -> agregarServicio());
        btnEliminar.addActionListener(e -> eliminarServicio());
        btnCalcular.addActionListener(e -> calcularCotizacion());
        btnFiltrar.addActionListener(e -> filtrarServicios());
        btnMostrarTodos.addActionListener(e -> actualizarTabla(controlador.getListaServicios()));
    }

    private void actualizarTabla(List<ServicioTecnico> datos) {
        modeloTabla.setRowCount(0);
        for (ServicioTecnico s : datos) {
            String tipoTexto = (s instanceof ServicioPreventivo) ? "PREVENTIVO" : "CORRECTIVO";
            double datoEsp = (s instanceof ServicioPreventivo) ? 
                ((ServicioPreventivo) s).getCostoInsumos() : 
                ((ServicioCorrectivo) s).getCostoRepuesto();

            modeloTabla.addRow(new Object[]{
                tipoTexto,
                s.getCodigo(),
                s.getDescripcion(),
                String.format(java.util.Locale.US, "%.2f", s.getTarifaHora()),
                String.format(java.util.Locale.US, "%.2f", datoEsp)
            });
        }
    }

    private void agregarServicio() {
        try {
            String tipo = (String) cbTipo.getSelectedItem();
            double tarifaHora = Double.parseDouble(txtTarifaHora.getText().trim());
            double datoEspecifico = Double.parseDouble(txtDatoEspecifico.getText().trim());

            controlador.agregarServicio(tipo, txtCodigo.getText(), txtDescripcion.getText(), tarifaHora, datoEspecifico);
            
            actualizarTabla(controlador.getListaServicios());
            limpiarFormulario();
            lblResultado.setText("Servicio agregado y guardado en archivo.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos en tarifa e insumos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarServicio() {
        int filaSel = tabla.getSelectedRow();
        if (filaSel == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigoSel = (String) modeloTabla.getValueAt(filaSel, 1);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar registro " + codigoSel + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controlador.eliminarServicio(codigoSel);
                actualizarTabla(controlador.getListaServicios());
                lblResultado.setText("Registro eliminado y guardado en archivo.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void calcularCotizacion() {
        int filaSel = tabla.getSelectedRow();
        if (filaSel == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio para cotizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int horas = Integer.parseInt(txtHoras.getText().trim());
            String codigoSel = (String) modeloTabla.getValueAt(filaSel, 1);
            
            double[] res = controlador.calcularCotizacion(codigoSel, horas, rbBeneficio.isSelected());
            double importeBase = res[0];
            double descuento = res[1];
            double total = res[2];

            String mensaje = String.format("--- COTIZACIÓN TÉCNICA ---\n" +
                    "Horas: %d\n" +
                    "Importe Base: $%,.2f\n" +
                    "Descuento: $%,.2f\n" +
                    "TOTAL A PAGAR: $%,.2f",
                    horas, importeBase, descuento, total);

            lblResultado.setText(String.format("Total: $%,.2f (Base: $%,.2f, Desc: $%,.2f)", total, importeBase, descuento));
            JOptionPane.showMessageDialog(this, mensaje, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número entero válido para las horas.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarServicios() {
        List<ServicioTecnico> filtrados = controlador.filtrar(txtFiltro.getText());
        actualizarTabla(filtrados);
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtTarifaHora.setText("");
        txtDatoEspecifico.setText("");
        txtHoras.setText("");
        cbTipo.setSelectedIndex(0);
        txtCodigo.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
