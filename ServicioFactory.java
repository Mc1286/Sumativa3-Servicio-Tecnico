/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipo4.factory;

/**
 *
 * @author juancortez
 */
import equipo4.modelo.*;

public class ServicioFactory {
    public static ServicioTecnico crearServicio(String tipo, String codigo, String descripcion, double tarifaHora, double datoEspecifico) {
        if (tipo.equalsIgnoreCase("PREVENTIVO")) {
            return new ServicioPreventivo(codigo, descripcion, tarifaHora, datoEspecifico);
        } else if (tipo.equalsIgnoreCase("CORRECTIVO")) {
            return new ServicioCorrectivo(codigo, descripcion, tarifaHora, datoEspecifico);
        }
        return null;
    }
}