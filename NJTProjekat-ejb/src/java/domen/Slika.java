/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domen;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aleksandar
 */
@Entity
@Table(name = "slika")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Slika.findAll", query = "SELECT s FROM Slika s"),
    @NamedQuery(name = "Slika.findByZadatakid", query = "SELECT s FROM Slika s WHERE s.slikaPK.zadatakid = :zadatakid"),
    @NamedQuery(name = "Slika.findBySlikaid", query = "SELECT s FROM Slika s WHERE s.slikaPK.slikaid = :slikaid"),
    @NamedQuery(name = "Slika.findByNaziv", query = "SELECT s FROM Slika s WHERE s.naziv = :naziv"),
    @NamedQuery(name = "Slika.findBySlikafajl", query = "SELECT s FROM Slika s WHERE s.slikafajl = :slikafajl"),
    @NamedQuery(name = "Slika.findByOpis", query = "SELECT s FROM Slika s WHERE s.opis = :opis")})
public class Slika implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SlikaPK slikaPK;
    @Size(max = 200)
    @Column(name = "naziv")
    private String naziv;
    @Size(max = 300)
    @Column(name = "slikafajl")
    private String slikafajl;
    @Size(max = 150)
    @Column(name = "opis")
    private String opis;
    @JoinColumn(name = "zadatakid", referencedColumnName = "zadatakid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Zadatak zadatak;

    public Slika() {
    }

    public Slika(SlikaPK slikaPK) {
        this.slikaPK = slikaPK;
    }

    public Slika(int zadatakid, int slikaid) {
        this.slikaPK = new SlikaPK(zadatakid, slikaid);
    }

    public SlikaPK getSlikaPK() {
        return slikaPK;
    }

    public void setSlikaPK(SlikaPK slikaPK) {
        this.slikaPK = slikaPK;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSlikafajl() {
        return slikafajl;
    }

    public void setSlikafajl(String slikafajl) {
        this.slikafajl = slikafajl;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Zadatak getZadatak() {
        return zadatak;
    }

    public void setZadatak(Zadatak zadatak) {
        this.zadatak = zadatak;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (slikaPK != null ? slikaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Slika)) {
            return false;
        }
        Slika other = (Slika) object;
        if ((this.slikaPK == null && other.slikaPK != null) || (this.slikaPK != null && !this.slikaPK.equals(other.slikaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.Slika[ slikaPK=" + slikaPK + " ]";
    }
    
}
