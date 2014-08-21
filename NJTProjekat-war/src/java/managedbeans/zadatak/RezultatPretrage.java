/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans.zadatak;

import domen.Korisnik;
import domen.Slika;
import domen.Zadatak;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import session.zadatak.ZadatakSession;

/**
 *
 * @author aleksandar
 */
@Named(value = "rezultat")
@SessionScoped
public class RezultatPretrage implements Serializable {

    @EJB
    private ZadatakSession zadatakSession;

    private List<Zadatak> zadaci;
    private Zadatak zadatak;
    private String zadatakID;
    List<String> slike;

    /**
     * Creates a new instance of RezultatPretrage
     */
    public RezultatPretrage() {

    }

    @PostConstruct
    public void init() {
        zadatak = new Zadatak();
    }

    public List<Zadatak> vratiNadjeneZadatke() {
        zadaci = new ArrayList<>();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        List l = (List) session.getAttribute("rezultat");
        for (Object z : l) {
            zadaci.add((Zadatak) z);
        }
        return zadaci;
    }

    public String izmeni() {
        return "izmenabrisanje?faces-redirect=true";
    }

    public void obrisi() {
        int id = Integer.parseInt(zadatakID);
        if (zadatakSession.obrisiZadatak(id)) {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            ListIterator<Zadatak> itr = zadaci.listIterator();
            while(itr.hasNext()) {
                Zadatak z = itr.next();
                if (z.getZadatakid().equals(id)) {
                    zadaci.remove(z);
                }
            }
            session.setAttribute("rezultat", zadaci);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, "Zadatak uspesno obrisan!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, "Zadatak nije obrisan!!"));
        }
    }

    public void postaviSlikeZadataka() {
        System.out.println("postavi slike za zadatak " + zadatakID);
        int id = Integer.parseInt(zadatakID);
        for (Zadatak z : zadaci) {
            if (z.getZadatakid() == id) {
                this.zadatak = z;
            }
        }
        slike = new ArrayList<>();
        for (Slika s : zadatak.getSlikaList()) {
            slike.add(s.getNaziv());
        }
        if (slike.isEmpty()) {
            slike.add("noimage.jpg");

        }
    }

    public String getZadatakID() {
        return zadatakID;
    }

    public void setZadatakID(String zadatakID) {
        this.zadatakID = zadatakID;
    }

    public List<String> getSlike() {
        return slike;
    }

    public void setSlike(List<String> slike) {
        this.slike = slike;
    }

    public Zadatak getZadatak() {
        return zadatak;
    }

    public void setZadatak(Zadatak zadatak) {
        this.zadatak = zadatak;
    }

}
