/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domen;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aleksandar
 */
@Entity
@Table(name = "zadatak")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zadatak.findAll", query = "SELECT z FROM Zadatak z"),
    @NamedQuery(name = "Zadatak.findByZadatakid", query = "SELECT z FROM Zadatak z WHERE z.zadatakid = :zadatakid"),
    @NamedQuery(name = "Zadatak.findByNazivzadatka", query = "SELECT z FROM Zadatak z WHERE z.nazivzadatka = :nazivzadatka"),
    @NamedQuery(name = "Zadatak.findByRokzaizvrsenje", query = "SELECT z FROM Zadatak z WHERE z.rokzaizvrsenje = :rokzaizvrsenje"),
    @NamedQuery(name = "Zadatak.findByStatus", query = "SELECT z FROM Zadatak z WHERE z.status = :status")})
    
public class Zadatak implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "zadatakid")
    private Integer zadatakid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "nazivzadatka")
    private String nazivzadatka;
    @Column(name = "rokzaizvrsenje")
    @Temporal(TemporalType.DATE)
    private Date rokzaizvrsenje;
    @Size(max = 45)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "opis")
    private String opis;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zadatak")
    private List<Zaduzenje> zaduzenjeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zadatak")
    private List<Slika> slikaList;

    public Zadatak() {
    }

    public Zadatak(Integer zadatakid) {
        this.zadatakid = zadatakid;
    }

    public Zadatak(Integer zadatakid, String nazivzadatka, String opis) {
        this.zadatakid = zadatakid;
        this.nazivzadatka = nazivzadatka;
        this.opis = opis;
    }

    public Integer getZadatakid() {
        return zadatakid;
    }

    public void setZadatakid(Integer zadatakid) {
        this.zadatakid = zadatakid;
    }

    public String getNazivzadatka() {
        return nazivzadatka;
    }

    public void setNazivzadatka(String nazivzadatka) {
        this.nazivzadatka = nazivzadatka;
    }

    public Date getRokzaizvrsenje() {
        return rokzaizvrsenje;
    }

    public void setRokzaizvrsenje(Date rokzaizvrsenje) {
        this.rokzaizvrsenje = rokzaizvrsenje;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @XmlTransient
    public List<Zaduzenje> getZaduzenjeList() {
        return zaduzenjeList;
    }

    public void setZaduzenjeList(List<Zaduzenje> zaduzenjeList) {
        this.zaduzenjeList = zaduzenjeList;
    }

    @XmlTransient
    public List<Slika> getSlikaList() {
        return slikaList;
    }

    public void setSlikaList(List<Slika> slikaList) {
        this.slikaList = slikaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zadatakid != null ? zadatakid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zadatak)) {
            return false;
        }
        Zadatak other = (Zadatak) object;
        if ((this.zadatakid == null && other.zadatakid != null) || (this.zadatakid != null && !this.zadatakid.equals(other.zadatakid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nazivzadatka;
    }
    
}
