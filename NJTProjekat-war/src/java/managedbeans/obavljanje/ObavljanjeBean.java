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
import imageprocessing.FilterPickList;
import imageprocessing.GrayscaleFilter;
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
    private DualListModel<FilterPickList> filteri;
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
//        context.execute("filterDialog.hide();");
//        context.execute("pbAjax.hide();");
        cancel();
        FacesContext.getCurrentInstance().addMessage("form1:progressMessage", new FacesMessage("Zavrsena obrada!"));
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
        List<FilterPickList> izvorniFilteri = new ArrayList<>();
        List<FilterPickList> odabraniFilteri = new ArrayList<>();

        izvorniFilteri.add(new FilterPickList(new OtsuBinarizeFilter(), "Otsu Binarizacija"));
        izvorniFilteri.add(new FilterPickList(new MedianFilter(), "Median Filter"));
        izvorniFilteri.add(new FilterPickList(new GrayscaleFilter(), "Grayscale"));
        izvorniFilteri.add(new FilterPickList(new EdgeDetection(), "Edge Detection"));

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
        List<FilterPickList> odabraniFilteri = filteri.getTarget();
        for (FilterPickList imageFilter : odabraniFilteri) {
            try {
                //***** Promeni ovu putanju!!!
                BufferedImage s = ImageIO.read(new File("/home/aleksandar/Documents/NetBeansProjects/NJTProjekat/NJTProjekat-war/web/resources/Images/" + slika));
                BufferedImage processedImage = imageFilter.getFilterObject().processImage(s);
                String naziv = slika + "_" + imageFilter.getFilterName();
                File outputfile = new File("/home/aleksandar/Documents/NetBeansProjects/NJTProjekat/NJTProjekat-war/web/resources/Images/" + naziv);
                ImageIO.write(processedImage, "png", outputfile);

                SlikaPK pk = new SlikaPK(zadatak.getZadatakid(), zadatak.getSlikaList().size() + 1);
                Slika sl = new Slika();
                sl.setSlikaPK(pk);
                sl.setNaziv(naziv);
                zadatak.getSlikaList().add(sl);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        postaviSlikeZadataka();
        System.out.println("Gotovo");
        this.progress = 80;
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
        if (slika == null) {
            slika = "border.jpg";
        }
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

    public DualListModel<FilterPickList> getFilteri() {
        return filteri;
    }

    public void setFilteri(DualListModel<FilterPickList> filteri) {
        this.filteri = filteri;
    }

}
