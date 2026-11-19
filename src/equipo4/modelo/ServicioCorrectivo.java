package equipo4.modelo;

/**
 * Clase hija inicial. Tipo en datos.txt: CORRECTIVO.
 * El cálculo está pendiente y debe implementarlo el equipo.
 */
public class ServicioCorrectivo extends ServicioTecnico {
    private double costoRepuesto;

    public ServicioCorrectivo(String codigo, String descripcion, double tarifaHora, double costoRepuesto) {
        super(codigo, descripcion, tarifaHora);
        this.costoRepuesto = costoRepuesto;
    }

    public double getCostoRepuesto() {
        return costoRepuesto;
    }

    public void setCostoRepuesto(double costoRepuesto) {
        this.costoRepuesto = costoRepuesto;
    }

    /*
     * TODO: implementar calcularImporte(int horas) con retorno double.
     * Aplicar la regla de ServicioCorrectivo descrita en ENUNCIADO.txt.
     * Sobrescribir el contrato que el equipo agregue a la clase padre.
     * Este comentario no implementa el método.
     */
}
