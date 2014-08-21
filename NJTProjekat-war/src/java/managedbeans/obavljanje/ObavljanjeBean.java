/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans.obavljanje;

import domen.Korisnik;
import domen.Slika;
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
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import session.zadatak.ZadatakSession;

/**
 *
 * @author aleksandar
 */
@Named(value = "obavljanjezadatka")
@SessionScoped
public class ObavljanjeBean implements Serializable {

    @EJB
    private ZadatakSession zadatakSession;

    private List<Zadatak> zadaci;
    private Zadatak zadatak;
    private String zadatakID;
    private List<String> slike;
    private String slika;
    private DualListModel<String> filteri;
    private Integer progress;

    public Integer getProgress() {
        if (progress == null) {
            progress = 0;
        } else {
            progress = progress + (int) (Math.random() * 60);

            if (progress > 100) {
                progress = 100;
            }
        }

        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public void onComplete() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("filterDialog.hide();");
        cancel();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Progress Completed"));

    }

    public void cancel() {
        progress = null;
    }

    /**
     * Creates a new instance of ObavljanjeBean
     */
    public ObavljanjeBean() {
    }

    @PostConstruct
    public void init() {
        List<String> izvorniFilteri = new ArrayList<String>();
        List<String> odabraniFilteri = new ArrayList<String>();

        izvorniFilteri.add("Grayscale");
        izvorniFilteri.add("Binarizacija");
        izvorniFilteri.add("Median filter");
        izvorniFilteri.add("Histogram equalization");

        filteri = new DualListModel<>(izvorniFilteri, odabraniFilteri);
    }

    public List<Zadatak> vratiZadatkeKorisnika() {
        this.zadaci = new ArrayList<>();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        Korisnik k = (Korisnik) session.getAttribute("korisnik");
        try {
            System.out.println("Id ulogovanog korisnika: " + k.getKorisnikid());
        } catch (Exception e) {
            throw new RuntimeException("Niste ulogovani!!");
        }

        this.zadaci = zadatakSession.vratiZadatkeKorisnika(k.getKorisnikid());
        return zadaci;

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
            slike.add("Nema slika");
            slika = "noimage.jpg";
        }
        else{
            slika = slike.get(0);   
        }
    }

    public List<Zadatak> getZadaci() {
        return zadaci;
    }

    public void setZadaci(List<Zadatak> zadaci) {
        this.zadaci = zadaci;
    }

    public Zadatak getZadatak() {
        return zadatak;
    }

    public void setZadatak(Zadatak zadatak) {
        this.zadatak = zadatak;
    }

    public String getZadatakID() {
        return zadatakID;
    }

    public void setZadatakID(String zadatakID) {
        this.zadatakID = zadatakID;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public List<String> getSlike() {
        return slike;
    }

    public void setSlike(List<String> slike) {
        this.slike = slike;
    }

    public DualListModel<String> getFilteri() {
        return filteri;
    }

    public void setFilteri(DualListModel<String> filteri) {
        this.filteri = filteri;
    }

}
