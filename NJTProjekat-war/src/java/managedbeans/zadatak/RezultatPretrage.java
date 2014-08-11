/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans.zadatak;

import domen.Korisnik;
import domen.Zadatak;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aleksandar
 */
@Named(value = "rezultat")
@SessionScoped
public class RezultatPretrage implements Serializable {
    private String zadatakID;
    
    /**
     * Creates a new instance of RezultatPretrage
     */
    public RezultatPretrage() {
    }

    public List<Zadatak> vratiNadjeneZadatke() {
        List<Zadatak> zadaci = new ArrayList<>();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        List l =  (List) session.getAttribute("rezultat");
        for (Object z : l) {
            zadaci.add((Zadatak) z);
        }
        return zadaci;
    }

    public String getZadatakID() {
        return zadatakID;
    }

    public void setZadatakID(String zadatakID) {
        this.zadatakID = zadatakID;
    }
}
