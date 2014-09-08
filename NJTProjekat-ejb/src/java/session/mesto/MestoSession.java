/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.mesto;

import domen.Mesto;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author aleksandar
 */
@Stateless
@LocalBean
public class MestoSession {

    @PersistenceContext(unitName = "NJTProjekat-ejbPU")
    private EntityManager em;

    public List<Mesto> pronadjiMesta() {
        Query query = em.createNamedQuery("Mesto.findAll");
        return query.getResultList();
    }

    public Mesto vratiMesto(String id) {
        return (Mesto) em.createNamedQuery("Mesto.findByPttbroj").setParameter("pttbroj", Integer.parseInt(id)).getSingleResult();
    }

}
