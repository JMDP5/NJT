/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domen;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "mesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mesto.findAll", query = "SELECT m FROM Mesto m"),
    @NamedQuery(name = "Mesto.findByPttbroj", query = "SELECT m FROM Mesto m WHERE m.pttbroj = :pttbroj"),
    @NamedQuery(name = "Mesto.findByNazivmesta", query = "SELECT m FROM Mesto m WHERE m.nazivmesta = :nazivmesta")})
public class Mesto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pttbroj")
    private Integer pttbroj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nazivmesta")
    private String nazivmesta;
    @OneToMany(mappedBy = "mesto")
    private List<Korisnik> korisnikList;

    public Mesto() {
    }

    public Mesto(Integer pttbroj) {
        this.pttbroj = pttbroj;
    }

    public Mesto(Integer pttbroj, String nazivmesta) {
        this.pttbroj = pttbroj;
        this.nazivmesta = nazivmesta;
    }

    public Integer getPttbroj() {
        return pttbroj;
    }

    public void setPttbroj(Integer pttbroj) {
        this.pttbroj = pttbroj;
    }

    public String getNazivmesta() {
        return nazivmesta;
    }

    public void setNazivmesta(String nazivmesta) {
        this.nazivmesta = nazivmesta;
    }

    @XmlTransient
    public List<Korisnik> getKorisnikList() {
        return korisnikList;
    }

    public void setKorisnikList(List<Korisnik> korisnikList) {
        this.korisnikList = korisnikList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pttbroj != null ? pttbroj.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesto)) {
            return false;
        }
        Mesto other = (Mesto) object;
        if ((this.pttbroj == null && other.pttbroj != null) || (this.pttbroj != null && !this.pttbroj.equals(other.pttbroj))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nazivmesta;
    }
    
}
