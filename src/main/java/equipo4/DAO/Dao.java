/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipo4.DAO;

import java.util.List;

/**
 *
 * @author LBC_14
 */
public interface Dao {
    String[] CargarServicioTecnico(String datos) throws Exception;

    List<String[]> obtenerTodosLosServicios() throws Exception;
}
