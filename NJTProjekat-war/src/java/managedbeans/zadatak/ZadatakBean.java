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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;
import session.zadatak.ZadatakSession;

/**
 *
 * @author aleksandar
 */
@Named(value = "zadatak")
@ViewScoped
public class ZadatakBean implements Serializable {

    @EJB
    private ZadatakSession zadatakSession;

    int numImages;
    private Zadatak zadatak;
    List<Slika> slike;
    private UploadedFile file;

    /**
     * Creates a new instance of ZadatakBean
     */
    public ZadatakBean() {
    }

    @PostConstruct
    public void init() {
        //Kad je viewScoped ovo se ne poziva nakon ajax poziva...
        zadatak = new Zadatak();
        //slike = new ArrayList<>();
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
        this.zadatak.setZadatakid(zadatakSession.vratiSledeciIdZadatka());
    }

    public void upload() {
        if (slike == null) {
            slike = new ArrayList<>();
        }
        if (file != null) {
            SlikaPK pk = new SlikaPK(this.zadatak.getZadatakid(), ++numImages);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dodata " + numImages + " slika!"));
            Slika s = new Slika(pk);
            s.setNaziv(file.getFileName());
            slike.add(s);
            FacesMessage message = new FacesMessage("Slika " + file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nije dodata slika!"));
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
