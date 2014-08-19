/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domen;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aleksandar
 */
@Entity
@Table(name = "korisnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k"),
    @NamedQuery(name = "Korisnik.findByKorisnikid", query = "SELECT k FROM Korisnik k WHERE k.korisnikid = :korisnikid"),
    @NamedQuery(name = "Korisnik.findByIme", query = "SELECT k FROM Korisnik k WHERE k.ime = :ime"),
    @NamedQuery(name = "Korisnik.findByAktivacioniKod", query = "SELECT k FROM Korisnik k WHERE k.aktivacionikod = :aktivacionikod"),
    @NamedQuery(name = "Korisnik.findByKorisnickoime", query = "SELECT k FROM Korisnik k WHERE k.korisnickoime = :korisnickoime"),
    @NamedQuery(name = "Korisnik.findByLozinka", query = "SELECT k FROM Korisnik k WHERE k.lozinka = :lozinka"),
    @NamedQuery(name = "Korisnik.findByEmail", query = "SELECT k FROM Korisnik k WHERE k.email = :email"),
    @NamedQuery(name = "Korisnik.findByTipkorisnika", query = "SELECT k FROM Korisnik k WHERE k.tipkorisnika = :tipkorisnika"),
    @NamedQuery(name = "Korisnik.findByStatus", query = "SELECT k FROM Korisnik k WHERE k.status = :status")})
public class Korisnik implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "korisnikid")
    private Integer korisnikid;
    @Size(max = 100)
    @Column(name = "ime")
    private String ime;
    @Size(max = 100)
    @Column(name = "aktivacionikod")
    private String aktivacionikod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "korisnickoime")
    private String korisnickoime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "lozinka")
    private String lozinka;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "email")
    private String email;
    @Column(name = "tipkorisnika")
    private Integer tipkorisnika;
    @Column(name = "status")
    private Integer status;
    @JoinColumn(name = "mesto", referencedColumnName = "pttbroj")
    @ManyToOne
    private Mesto mesto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "korisnik")
    private List<Zaduzenje> zaduzenjeList;

    public Korisnik() {
    }

    public Korisnik(Integer korisnikid) {
        this.korisnikid = korisnikid;
    }

    public Korisnik(Integer korisnikid, String korisnickoime, String lozinka, String email) {
        this.korisnikid = korisnikid;
        this.korisnickoime = korisnickoime;
        this.lozinka = lozinka;
        this.email = email;
    }

    public Integer getKorisnikid() {
        return korisnikid;
    }

    public void setKorisnikid(Integer korisnikid) {
        this.korisnikid = korisnikid;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getAktivacioniKod() {
        return aktivacionikod;
    }

    public void setAktivacioniKod(String kod) {
        this.aktivacionikod = kod;
    }

    public String getKorisnickoime() {
        return korisnickoime;
    }

    public void setKorisnickoime(String korisnickoime) {
        this.korisnickoime = korisnickoime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTipkorisnika() {
        return tipkorisnika;
    }

    public void setTipkorisnika(Integer tipkorisnika) {
        this.tipkorisnika = tipkorisnika;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }

    @XmlTransient
    public List<Zaduzenje> getZaduzenjeList() {
        return zaduzenjeList;
    }

    public void setZaduzenjeList(List<Zaduzenje> zaduzenjeList) {
        this.zaduzenjeList = zaduzenjeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (korisnikid != null ? korisnikid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.korisnikid == null && other.korisnikid != null) || (this.korisnikid != null && !this.korisnikid.equals(other.korisnikid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.Korisnik[ korisnikid=" + korisnikid + " Ime: " + ime +" +]";
    }
    
}
