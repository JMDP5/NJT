/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans.zadatak;

import domen.Zadatak;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.Future;
import org.primefaces.event.RowEditEvent;
import session.zadatak.ZadatakSession;

/**
 *
 * @author aleksandar
 */
@Named(value = "pretragazadataka")
@ViewScoped
public class PretragaZadatakBean implements Serializable {

    @EJB
    private ZadatakSession zadatakSession;

    private List<Zadatak> sviZadaci;
    private List<Zadatak> filtriraniZadaci;

    private Zadatak selektovanZadatak;
    @Future
    Date rok;

    /**
     * Creates a new instance of PretragaZadatakBean
     */
    @PostConstruct
    public void init() {
        this.sviZadaci = zadatakSession.pronadjiZadatke();
        rok = null;
    }

    public PretragaZadatakBean() {
    }

    public void onRowEdit(RowEditEvent event) {
        Zadatak izmenjenZadatak = (Zadatak) event.getObject();
        if (rok != null) {
            izmenjenZadatak.setRokzaizvrsenje(rok);
        }
        zadatakSession.izmeniZadatak(izmenjenZadatak);
        FacesMessage msg = new FacesMessage("Izmenjen zadatak:", izmenjenZadatak.getNazivzadatka() + " ID: " + izmenjenZadatak.getZadatakid());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Izmena otkazana!", ((Zadatak) event.getObject()).getNazivzadatka());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void obrisiZadatak(Zadatak z) {

        if (zadatakSession.obrisiZadatak(z.getZadatakid())) {
            this.sviZadaci = zadatakSession.pronadjiZadatke();
            FacesMessage msg = new FacesMessage("Zadatak obrisan! ", z.getNazivzadatka() + " ID: " + z.getZadatakid());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zadatak nije obrisan! ", z.getNazivzadatka() + " ID: " + z.getZadatakid());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public List<Zadatak> getFiltriraniZadaci() {
        return filtriraniZadaci;
    }

    public void setFiltriraniZadaci(List<Zadatak> filtriraniZadaci) {
        this.filtriraniZadaci = filtriraniZadaci;
    }

    public List<Zadatak> getSviZadaci() {
        return sviZadaci;
    }

    public void setSviZadaci(List<Zadatak> sviZadaci) {
        this.sviZadaci = sviZadaci;
    }

    public Zadatak getSelektovanZadatak() {
        return selektovanZadatak;
    }

    public void setSelektovanZadatak(Zadatak selektovanZadatak) {
        this.selektovanZadatak = selektovanZadatak;
    }

    public Date getRok() {
        return rok;
    }

    public void setRok(Date rok) {
        this.rok = rok;
    }

}
