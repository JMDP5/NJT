/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans.obavljanje;

import domen.Korisnik;
import domen.Slika;
import domen.SlikaPK;
import domen.Zadatak;
import imageprocessing.EdgeDetection;
import imageprocessing.HistogramEqualizationFilter;
import imageprocessing.ImageFilter;
import imageprocessing.MedianFilter;
import imageprocessing.OtsuBinarizeFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
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
        vratiZadatkeKorisnika();
        List<String> izvorniFilteri = new ArrayList<>();
        List<String> odabraniFilteri = new ArrayList<>();

        izvorniFilteri.add("Otsu Binarizacija");
        izvorniFilteri.add("Median filter");
        izvorniFilteri.add("Grayscale");
        izvorniFilteri.add("Edge Detection");

        filteri = new DualListModel<>(izvorniFilteri, odabraniFilteri);
    }

    public void vratiZadatkeKorisnika() {
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
    }

    public void primeniFiltere() {
        List<String> odabraniFilteri = filteri.getTarget();
        for (String imageFilter : odabraniFilteri) {
            try {
                //***** Promeni ovu putanju!!!
                BufferedImage s = ImageIO.read(new File("/home/aleksandar/Documents/NetBeansProjects/NJTProjekat/NJTProjekat-war/web/resources/Images/" + slika));
                BufferedImage processedImage = new EdgeDetection().processImage(s);
                String naziv = slika + "_" + imageFilter;
                File outputfile = new File("/home/aleksandar/Documents/NetBeansProjects/NJTProjekat/NJTProjekat-war/web/resources/Images/" + naziv);
                ImageIO.write(processedImage, "png", outputfile);

                SlikaPK pk = new SlikaPK(zadatak.getZadatakid(), zadatak.getSlikaList().size() + 1);
                Slika sl = new Slika();
                sl.setSlikaPK(pk);
                sl.setNaziv(naziv);
                zadatak.getSlikaList().add(sl);
                postaviSlikeZadataka();
                System.out.println("Gotovo");
                this.progress = 101;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

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
            slike.add("noimage.jpg");
            slika = "noimage.jpg";
        } else {
            slika = slike.get(0);
        }
    }

    public void sacuvajZadatak() {
        try {
            zadatakSession.izmeniZadatak(this.zadatak);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, "Zadatak uspesno sacuvan!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, "Greska " + e.getMessage()));
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
