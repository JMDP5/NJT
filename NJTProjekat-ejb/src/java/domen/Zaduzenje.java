/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domen;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aleksandar
 */
@Entity
@Table(name = "zaduzenje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zaduzenje.findAll", query = "SELECT z FROM Zaduzenje z"),
    @NamedQuery(name = "Zaduzenje.findByKorisnikid", query = "SELECT z FROM Zaduzenje z WHERE z.zaduzenjePK.korisnikid = :korisnikid"),
    @NamedQuery(name = "Zaduzenje.findByZadatakid", query = "SELECT z FROM Zaduzenje z WHERE z.zaduzenjePK.zadatakid = :zadatakid"),
    @NamedQuery(name = "Zaduzenje.findByDatumdodele", query = "SELECT z FROM Zaduzenje z WHERE z.datumdodele = :datumdodele")})
public class Zaduzenje implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ZaduzenjePK zaduzenjePK;
    @Column(name = "datumdodele")
    @Temporal(TemporalType.DATE)
    private Date datumdodele;
    @Lob
    @Size(max = 65535)
    @Column(name = "napomena")
    private String napomena;
    @JoinColumn(name = "zadatakid", referencedColumnName = "zadatakid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Zadatak zadatak;
    @JoinColumn(name = "korisnikid", referencedColumnName = "korisnikid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Korisnik korisnik;

    public Zaduzenje() {
    }

    public Zaduzenje(ZaduzenjePK zaduzenjePK) {
        this.zaduzenjePK = zaduzenjePK;
    }

    public Zaduzenje(int korisnikid, int zadatakid) {
        this.zaduzenjePK = new ZaduzenjePK(korisnikid, zadatakid);
    }

    public ZaduzenjePK getZaduzenjePK() {
        return zaduzenjePK;
    }

    public void setZaduzenjePK(ZaduzenjePK zaduzenjePK) {
        this.zaduzenjePK = zaduzenjePK;
    }

    public Date getDatumdodele() {
        return datumdodele;
    }

    public void setDatumdodele(Date datumdodele) {
        this.datumdodele = datumdodele;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public Zadatak getZadatak() {
        return zadatak;
    }

    public void setZadatak(Zadatak zadatak) {
        this.zadatak = zadatak;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zaduzenjePK != null ? zaduzenjePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zaduzenje)) {
            return false;
        }
        Zaduzenje other = (Zaduzenje) object;
        if ((this.zaduzenjePK == null && other.zaduzenjePK != null) || (this.zaduzenjePK != null && !this.zaduzenjePK.equals(other.zaduzenjePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.Zaduzenje[ zaduzenjePK=" + zaduzenjePK + " ]";
    }
    
}
