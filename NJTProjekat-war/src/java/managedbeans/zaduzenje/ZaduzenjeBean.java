/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans.zaduzenje;

import domen.Korisnik;
import domen.Zadatak;
import domen.Zaduzenje;
import domen.ZaduzenjePK;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import session.korisnik.KorisnikSession;

/**
 *
 * @author aleksandar
 */
@Named(value = "zaduzenje")
@SessionScoped
public class ZaduzenjeBean implements Serializable {

    @EJB
    private KorisnikSession korisnikSession;

    private Zaduzenje zaduzenje;
    private String korisnikId;
    private String zadatakId;

    /**
     * Creates a new instance of ZaduzenjeBean
     */
    public ZaduzenjeBean() {
    }

    @PostConstruct
    public void init() {
        zaduzenje = new Zaduzenje();
        zaduzenje.setDatumdodele(new Date());
    }

    public List<Korisnik> vratiSveKorisnike() {
        return korisnikSession.vratiSveKorisnike();
    }

    public List<Zadatak> vratiSveZadatke() {
        return korisnikSession.vratiSveZadatke();
    }

    public Zaduzenje getZaduzenje() {
        return zaduzenje;
    }

    public void setZaduzenje(Zaduzenje zaduzenje) {
        this.zaduzenje = zaduzenje;
    }

    public String getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(String korisnikId) {
        this.korisnikId = korisnikId;
    }

    public String getZadatakId() {
        return zadatakId;
    }

    public void setZadatakId(String zadatakId) {
        this.zadatakId = zadatakId;
    }

    public void sacuvajZaduzenje() {
        ZaduzenjePK zpk = new ZaduzenjePK(Integer.parseInt(korisnikId), Integer.parseInt(zadatakId));
        zaduzenje.setZaduzenjePK(zpk);
        try {
            korisnikSession.zapamtiZaduzenje(zaduzenje);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Zaduzenje uspesno dodato!", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zaduzenje nije dodato!", e.getMessage()));
        }

    }
}
