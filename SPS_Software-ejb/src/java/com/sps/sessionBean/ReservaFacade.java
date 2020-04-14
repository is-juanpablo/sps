package com.sps.sessionBean;

import com.sps.entity.Cliente;
import com.sps.entity.Reserva;
import com.sps.entity.Usuario;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Juan Pablo
 */
@Stateless
public class ReservaFacade extends AbstractFacade<Reserva> implements ReservaFacadeLocal {

    @PersistenceContext(unitName = "SPS_Software-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservaFacade() {
        super(Reserva.class);
    }

    @Override
    public Number findSelector(Cliente idCliente) {
        Query query = getEntityManager().createNamedQuery("Reserva.findBySelector");
        query.setParameter("idCliente", idCliente);
        Number select = (Number) query.getSingleResult();
        return select;
    }

    @Override
    public Reserva findByUsuario(Usuario persona) {
        Query query = getEntityManager().createNamedQuery("Reserva.findByUsuario");
        query.setParameter("idUsuario", persona);

        if (query.getResultList().size() < 1) {
            return null;
        }
        Reserva reserva = (Reserva) query.getResultList().get(0);
        return reserva;
    }

    @Override
    public List<Reserva> findAllByUsuario(Usuario persona) {
        Query query = getEntityManager().createNamedQuery("Reserva.findByUsuario");
        query.setParameter("idUsuario", persona);
        List<Reserva> reservas = query.getResultList();
        return reservas;
    }

    @Override
    public void getReservasPorHora() {
        Query query = getEntityManager().createNativeQuery("SELECT SUBSTR(CHAR(TIME(r.entrada)),1,2), COUNT(SUBSTR(CHAR(TIME(r.entrada)),1,2)) FROM RESERVA r WHERE r.dia = current_date GROUP BY SUBSTR(CHAR(TIME(r.entrada)),1,2) ORDER BY 1");

        List<Object[]> list = query.getResultList();
        for (Object[] result : list) {
            String name = (String) result[0];
            int count = (int) result[1];
            System.out.println(name + "   :   " + count);
        }

    }

    @Override
    public String graficoReserva(Usuario persona) {
        Query query = getEntityManager().createNativeQuery("SELECT MONTH(dia), count(*) FROM RESERVA WHERE id_usuario = '" + persona.getPlaca() + "' GROUP BY MONTH(dia) ");
        List<Object[]> list = query.getResultList();
        String grafico = "";
        if (list.size() > 0) {

            grafico = "https://quickchart.io/chart?c={type:'line',data:{labels:[";

            String[] mes = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
            for (int i = 0; i < 12; i++) {
                grafico += "'" + mes[i] + "',";
            }

            grafico = grafico.substring(0, grafico.length() - 1) + "], datasets:[{label:'Reservas Mes', data: [";

            int cont = 0;
            for (int i = 0; i < 12; i++) {
                if (list.size() > cont) {
                    int valor = (int) list.get(cont)[0];
                    if (valor == (i + 1)) {
                        grafico += ((int) list.get(cont)[1]) + ",";
                        cont++;
                    } else {
                        grafico += "0,";
                    }
                } else {
                    grafico += "0,";
                }
            }

            grafico = grafico.substring(0, grafico.length() - 1) + "], fill:false,borderColor:'blue'}]}}";
        }

        return grafico;
    }

}
