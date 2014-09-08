/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans.aktivacija;

import domen.Korisnik;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import session.korisnik.KorisnikSession;

/**
 *
 * @author aleksandar
 */
@Named(value = "aktivacija")
@RequestScoped
public class AktivacijaBean {

    @EJB
    private KorisnikSession korisnikSession;

    private String key;
    private String email;
    String ime;
    private boolean prikazi;

    /**
     * Creates a new instance of AktivacijaBean
     */
    public AktivacijaBean() {
    }

    public String getKey() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String kod = (String) facesContext.getExternalContext().
                getRequestParameterMap().get("key");
        Korisnik k = korisnikSession.pronadjiKorisnikaPoKodu(kod);
        if (k != null) {
            this.ime = k.getIme();
            if (k.getStatus() == 1) {
                prikazi = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aktivacija --> ", "Vas nalog je vec aktiviran."));
            } else {
                prikazi = false;
                k.setStatus(1);
                korisnikSession.aktivirajNalog(k);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aktivacija --> ", "Vas nalog " + k.getKorisnickoime() + " je uspesno aktiviran."));
            }
        } else {
            prikazi = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Aktivacija --> ", "Aktivacioni kod ne postoji, proverite aktivacioni link!"));
        }

        return this.ime;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPrikazi() {
        return prikazi;
    }

    public void setPrikazi(boolean prikazi) {
        this.prikazi = prikazi;
    }

}
