/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.zaduzenje;

import domen.Zaduzenje;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author aleksandar
 */
@Stateless
@LocalBean
public class ZaduzenjeSession {

    @PersistenceContext(unitName = "NJTProjekat-ejbPU")
    private EntityManager em;

    public void sacuvajZaduzenje(Zaduzenje z) {
        em.persist(z);
        em.flush();
    }

}
