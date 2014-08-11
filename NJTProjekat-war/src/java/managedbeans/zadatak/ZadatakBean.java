/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans.zadatak;

import domen.Korisnik;
import domen.Slika;
import domen.SlikaPK;
import domen.Zadatak;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import session.zadatak.ZadatakSession;

/**
 *
 * @author aleksandar
 */
@Named(value = "zadatak")
@SessionScoped
public class ZadatakBean implements Serializable {

    @EJB
    private ZadatakSession zadatakSession;

    int numImages;
    private Zadatak zadatak;
    List<Slika> slike;
    String zadatakId;

    /**
     * Creates a new instance of ZadatakBean
     */
    public ZadatakBean() {
    }

    @PostConstruct
    public void init() {
        zadatak = new Zadatak();
        numImages = 0;
        slike = new ArrayList<>();
        postaviZadatakID();
    }

    public Zadatak getZadatak() {
        return zadatak;
    }

    public void setZadatak(Zadatak zadatak) {
        this.zadatak = zadatak;
    }

    public void dodajSliku() {
        SlikaPK pk = new SlikaPK(Integer.parseInt(zadatakId), ++numImages);
        Slika s = new Slika(pk);
        slike.add(s);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, "Dodata " + numImages + ". slika!"));

    }

    public void sacuvajZadatak() {
        zadatak.setSlikaList(slike);
        zadatakSession.sacuvajZadatak(zadatak);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, "Zadatak sacuvan!"));
    }

    private void postaviZadatakID() {
        zadatakId = String.valueOf(zadatakSession.vratiSledeciIdZadatka());
    }

}
