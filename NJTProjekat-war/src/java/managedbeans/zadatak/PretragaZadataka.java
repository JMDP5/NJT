/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans.zadatak;

import domen.Zadatak;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import session.zadatak.ZadatakSession;

/**
 *
 * @author aleksandar
 */
@Named(value = "pretraga")
@SessionScoped
public class PretragaZadataka implements Serializable {

    @EJB
    private ZadatakSession zadatakSession;

    private String nazivzadatka;
    private String status;
    private Date rok;
    private List<String> slike;

    /**
     * Creates a new instance of PretragaZadataka
     */
    public PretragaZadataka() {
    }

    @PostConstruct
    public void init() {
        slike = new ArrayList<>();
        slike.add("lenna.jpg");
        slike.add("flower2.jpg");
        slike.add("lotus_flower.jpg");
    }

    public String getNazivzadatka() {
        return nazivzadatka;
    }

    public void setNazivzadatka(String nazivzadatka) {
        this.nazivzadatka = nazivzadatka;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRok() {
        return rok;
    }

    public void setRok(Date rok) {
        this.rok = rok;
    }

    public String pretrazi() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        List zadaci = (List) zadatakSession.vratiZadatak(nazivzadatka, status, rok);
        if (zadaci != null && zadaci.size() > 0) {
            session.setAttribute("rezultat", zadaci);
            return "pretragarez?faces-redirect=true";

        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Nisu pronadjeni zadaci po unetim kriterijumima!!"));
        }
        return null;

    }
    
    public String izmeni() {
        return "izmenabrisanje?faces-redirect=true";
    }

    public void pronajdiZadatak() {
        SimpleDateFormat sdf
                = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public List<String> getSlike() {
        return slike;
    }

    public void setSlike(List<String> slike) {
        this.slike = slike;
    }

}
