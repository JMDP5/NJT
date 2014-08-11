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
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        Object rez = korisnikSession.vratiKorisnikaizBaze(korisnickoime);
        if (rez != null) {
            Korisnik trenutniKorisnik = (Korisnik) rez;
            if (loznika.equals(trenutniKorisnik.getLozinka())) {
                //Proveri prvo da li je korisnik ili administrator!
                session.setAttribute("korisnik", trenutniKorisnik);
                return NavigacijaBean.redirect(1);
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Logovanje neuspesno!!"));
        }
        return null;
    }

    public String odjaviKorisnika() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        return "logovanje";
    }

}
