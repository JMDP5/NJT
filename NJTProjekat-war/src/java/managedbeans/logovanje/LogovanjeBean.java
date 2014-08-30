/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans.logovanje;

import domen.Korisnik;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import managedbeans.navigacija.NavigacijaBean;
import session.korisnik.KorisnikSession;

/**
 *
 * @author aleksandar
 */
@Named(value = "logovanje")
@SessionScoped
public class LogovanjeBean implements Serializable {

    @EJB
    private KorisnikSession korisnikSession;

    private String korisnickoime;
    private String loznika;

    /**
     * Creates a new instance of LogovanjeBean
     */
    public LogovanjeBean() {
    }

    public String getKorisnickoime() {
        return korisnickoime;
    }

    public void setKorisnickoime(String korisnickoime) {
        this.korisnickoime = korisnickoime;
    }

    public String getLoznika() {
        return loznika;
    }

    public void setLoznika(String loznika) {
        this.loznika = loznika;
    }

    public String ulogujKorisnika() {

        Korisnik k = korisnikSession.vratiKorisnikaizBaze(korisnickoime);
        if (k == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Ne postoji korisnik sa unesenim korisnickim imenom. "));
        } else {
            if (!loznika.equals(k.getLozinka())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, "Pogresna sifra!"));
            } else if (k.getStatus() != 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Vas nalog nije aktivan. Prvo aktivirajte nalog."));
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
                session.setAttribute("korisnik", k);
                if (k.getTipkorisnika() == 1) { // Radnik -- tipKorisnika = 1
                    return "obradazadataka.xhtml?faces-redirect=true";
                } else {
                    return "dodajzadatak.xhtml?faces-redirect=true";

                }
            }
        }
        return null;
    }

    public String odjaviKorisnika() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        return "logovanje.xhtml?faces-redirect=true";
    }

}
