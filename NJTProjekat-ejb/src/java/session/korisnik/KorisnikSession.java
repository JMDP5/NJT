/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.korisnik;

import domen.Korisnik;
import domen.Mesto;
import domen.Zadatak;
import domen.Zaduzenje;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author aleksandar
 */
@Stateless
@LocalBean
public class KorisnikSession {

    @PersistenceContext(unitName = "NJTProjekat-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
        em.flush();
    }

    public List<Korisnik> vratiSveRadnike() {
        Query query = em.createNamedQuery("Korisnik.findByTipkorisnika");
        query.setParameter("tipkorisnika", 1);
        return query.getResultList();
    }

    public Korisnik vratiKorisnikaizBaze(String username) {
        Query q = em.createNamedQuery("Korisnik.findByKorisnickoime");
        q.setParameter("korisnickoime", username);
        Object result = null;
        try {
            result = q.getSingleResult();
            return (Korisnik) result;
        } catch (NoResultException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public Korisnik izmeni(Korisnik k) {
        Korisnik promenjen = em.merge(k);
        return promenjen;
    }

    public Korisnik ubaci(Korisnik k) {
        System.out.println("ID:" + k.getKorisnikid() + " ime: " + k.getIme());
        try {
            em.persist(k);
            em.flush();
        } catch (Exception e) {
            throw e;
        }
        return k;
    }

    public List<Mesto> vratiSvaMesta() {
        Query query = em.createNamedQuery("Mesto.findAll");
        return query.getResultList();
    }

    public Mesto vratiMesto(String id) {
        return (Mesto) em.createNamedQuery("Mesto.findByPttbroj").setParameter("pttbroj", Integer.parseInt(id)).getSingleResult();
    }

    public List<Zadatak> vratiSveZadatke() {
        Query query = em.createNamedQuery("Zadatak.findAll");
        return query.getResultList();
    }

    public void zapamtiZaduzenje(Zaduzenje z) {
        em.persist(z);
        em.flush();
    }

    public void aktivirajNalog(String key) {
        //em.merge(k); k je korisnik sa promenjenim id-ijem.
        Query query = em.createQuery("UPDATE Korisnik k SET k.status = 1 WHERE k.aktivacionikod = :aktivacionikod");
        query.setParameter("aktivacionikod", key);
        query.executeUpdate();
    }

    public void promeniKorisnika(Korisnik k) {
        em.merge(k);
    }

    public Korisnik pronadjiKorisnikaPoKodu(String kod) {
        Query q = em.createNamedQuery("Korisnik.findByAktivacioniKod");
        q.setParameter("aktivacionikod", kod);
        List l = q.getResultList();
        if (l != null && l.size() != 0) {
            return (Korisnik) l.get(0);
        }
        return null;
    }

}
