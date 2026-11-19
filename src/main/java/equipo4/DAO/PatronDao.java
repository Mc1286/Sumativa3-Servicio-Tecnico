/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package equipo4.DAO;

/**
 *
 * @author LBC_14
 */
public class PatronDao {

    public static void main(String[] args) {
        PatronDaoClass patron = new PatronDaoClass();

        try {
            String[] res = patron.CargarServicioTecnico("SC02");
            
            System.out.println(res[0]  + "  ---  " + res[1] + "  ---  "+ res[2]);
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }
}
