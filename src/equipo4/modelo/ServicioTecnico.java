package equipo4.modelo;

/**
 * Base entregada para el equipo 4: Cotización de servicio técnico.
 * Contiene datos y accesores; los comportamientos deben completarse.
 * Los constructores y setters iniciales no validan: el equipo debe incorporar
 * las validaciones del enunciado en una capa o clase adecuada.
 */
public abstract class ServicioTecnico {
    private String codigo;
    private String descripcion;
    private double tarifaHora;

    public ServicioTecnico(String codigo, String descripcion, double tarifaHora) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.tarifaHora = tarifaHora;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTarifaHora() {
        return tarifaHora;
    }

    public void setTarifaHora(double tarifaHora) {
        this.tarifaHora = tarifaHora;
    }

    /*
     * TODO: incorporar al contrato común el método:
     * double calcularImporte(int horas)
     * Cada clase hija debe resolverlo según sus reglas.
     * Utilizar referencias y una colección del tipo ServicioTecnico.
     * La selección NORMAL/BENEFICIO se aplica al importe base calculado.
     * No se entrega implementación de cálculo ni uso polimórfico.
     */
}
