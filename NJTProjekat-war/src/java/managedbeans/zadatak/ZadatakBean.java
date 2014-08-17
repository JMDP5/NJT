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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Part;
import org.primefaces.model.UploadedFile;
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
    private UploadedFile file;

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

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, "Dodata " + numImages + ". slika!"));

    }

    public void sacuvajZadatak() {

        zadatak.setSlikaList(slike);
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            zadatakSession.sacuvajZadatak(zadatak);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Zadatak sacuvan!"));
            postaviZadatakID();

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Greska zadatak nije sacuvan!" + ex.getMessage()));
        }
    }

    private void postaviZadatakID() {
        zadatakId = String.valueOf(zadatakSession.vratiSledeciIdZadatka());
    }

    public void upload() {
        if (file != null) {
            SlikaPK pk = new SlikaPK(Integer.parseInt(zadatakId), ++numImages);
            Slika s = new Slika(pk);
            s.setNaziv(file.getFileName());
            slike.add(s);
            FacesMessage message = new FacesMessage("Slika ", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);

        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
