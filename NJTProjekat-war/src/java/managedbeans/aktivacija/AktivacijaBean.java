/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedbeans.aktivacija;

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
    /**
     * Creates a new instance of AktivacijaBean
     */
    public AktivacijaBean() {
    }

    public String getKey() {
       FacesContext facesContext = FacesContext.getCurrentInstance();
        this.key = (String) facesContext.getExternalContext().
                getRequestParameterMap().get("key");
        korisnikSession.aktivirajNalog(key);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aktivacija", "Korisnik " + key + " je uspesno aktiviran"));
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    
}
