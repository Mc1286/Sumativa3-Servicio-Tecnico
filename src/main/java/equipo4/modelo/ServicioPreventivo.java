package equipo4.modelo;

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
    
    @Override
    public double calcularImporte(int horas){
        return (getTarifaHora() * horas) + costoInsumos;
        
    }
    /*
     * TODO: implementar calcularImporte(int horas) con retorno double.
     * Aplicar la regla de ServicioPreventivo descrita en ENUNCIADO.txt.
     * Sobrescribir el contrato que el equipo agregue a la clase padre.
     * Este comentario no implementa el método.
     */
}
