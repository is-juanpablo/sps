package com.sps.session;

import com.sps.entity.Cliente;
import com.sps.entity.Persona;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Juan Pablo
 */
@Stateless
public class ClienteFacade extends AbstractFacade<Cliente> implements ClienteFacadeLocal {

    @PersistenceContext(unitName = "SPS_Software-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }

    @Override
    public List<Cliente> findByCedula(Persona persona) {
        Query query = getEntityManager().createNamedQuery("Cliente.findByCedula");
        query.setParameter("idPersona", persona);
        List<Cliente> list = query.getResultList();
        return list;
    }

    @Override
    public Cliente findByID(Integer id) {
        Query query = getEntityManager().createNamedQuery("Cliente.findById");
        query.setParameter("id", id);
        return (Cliente) query.getSingleResult();
    }

}