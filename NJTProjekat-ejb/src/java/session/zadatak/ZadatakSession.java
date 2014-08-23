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

    public void persist(Object object) {
        em.persist(object);
        em.flush();
    }

    public void sacuvajZadatak(Zadatak z) {
        em.persist(z);
        em.flush();
    }

    public Integer vratiSledeciIdZadatka() {
        Query query = em.createQuery("SELECT max(z.zadatakid) FROM Zadatak z");
        int max = (Integer) query.getResultList().get(0);
        return ++max;
    }

    public Object vratiZadatak(String nazivzadatka, String status, Date rok) {
        String uslov = "";
        Query query;
        if (nazivzadatka != null && !nazivzadatka.isEmpty()) {
            if (!uslov.isEmpty()) {
                uslov += " AND ";
            }
            uslov += "z.nazivzadatka='" + nazivzadatka + "'";
        }
        if (status != null && !status.isEmpty()) {
            if (!uslov.isEmpty()) {
                uslov += " AND ";
            }
            uslov += "z.status='" + status + "'";
        }
//        if (rok != null) {
//            if (!uslov.isEmpty()) {
//                uslov += " AND ";
//            }
//            uslov += "status='" + status + "'";
//        }
        if (uslov.isEmpty()) {
            query = em.createNamedQuery("Zadatak.findAll");
        } else {
            query = em.createQuery("SELECT z FROM Zadatak z WHERE " + uslov);
        }
        return query.getResultList();
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

}
