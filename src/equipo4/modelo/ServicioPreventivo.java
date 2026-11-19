package equipo4.modelo;

/**
 * Clase hija inicial. Tipo en datos.txt: PREVENTIVO.
 * El cálculo está pendiente y debe implementarlo el equipo.
 */
public class ServicioPreventivo extends ServicioTecnico {
    private double costoInsumos;

    public ServicioPreventivo(String codigo, String descripcion, double tarifaHora, double costoInsumos) {
        super(codigo, descripcion, tarifaHora);
        this.costoInsumos = costoInsumos;
    }

    public double getCostoInsumos() {
        return costoInsumos;
    }

    public void setCostoInsumos(double costoInsumos) {
        this.costoInsumos = costoInsumos;
    }

    /*
     * TODO: implementar calcularImporte(int horas) con retorno double.
     * Aplicar la regla de ServicioPreventivo descrita en ENUNCIADO.txt.
     * Sobrescribir el contrato que el equipo agregue a la clase padre.
     * Este comentario no implementa el método.
     */
}
