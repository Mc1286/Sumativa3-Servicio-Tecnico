package equipo4.modelo;

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
    
    public abstract double calcularImporte(int horas);
    }

