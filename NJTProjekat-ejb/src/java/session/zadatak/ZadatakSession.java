/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.zadatak;

import domen.Zadatak;
import domen.Zaduzenje;
import java.util.ArrayList;
import java.util.Date;
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
public class ZadatakSession {

    @PersistenceContext(unitName = "NJTProjekat-ejbPU")
    private EntityManager em;

    public void sacuvajZadatak(Zadatak z) {
        em.persist(z);
        em.flush();
    }

    public Integer kreirajSledeciID() {
        Query query = em.createQuery("SELECT max(z.zadatakid) FROM Zadatak z");
        int max = (Integer) query.getResultList().get(0);
        return ++max;
    }

    public List<Zadatak> vratiZadatkeKorisnika(Integer idKorisnika) {
        Query q = em.createNamedQuery("Zaduzenje.findByKorisnikid");
        q.setParameter("korisnikid", idKorisnika);
        List<Zadatak> zadaci = new ArrayList<>();
        List<Zaduzenje> zaduzenja = q.getResultList();
        System.out.println("Br. zaduzenja: " + zaduzenja.size());
        for (Zaduzenje zaduzenje : zaduzenja) {
            zadaci.add(zaduzenje.getZadatak());
        }
        return zadaci;
    }

    public boolean obrisiZadatak(int id) {
        Query query = em.createQuery("DELETE FROM Zadatak z WHERE z.zadatakid='" + id + "'");
        int res = query.executeUpdate();
        return res > 0;

    }

    public void izmeniZadatak(Zadatak z) {
        em.merge(z);
    }

    public List<Zadatak> pronadjiZadatke() {
        return em.createNamedQuery("Zadatak.findAll").getResultList();
    }

    public Zadatak vratiZadatak(Integer zadatakID) {
        Query q = em.createNamedQuery("Zadatak.findByZadatakid");
        q.setParameter("zadatakid", zadatakID);
        List result = q.getResultList();
        if (result.size() > 0) {
            return (Zadatak) result.get(0);
        }
        return null;

    }

}
