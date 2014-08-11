/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domen;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author aleksandar
 */
@Embeddable
public class ZaduzenjePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "korisnikid")
    private int korisnikid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zadatakid")
    private int zadatakid;

    public ZaduzenjePK() {
    }

    public ZaduzenjePK(int korisnikid, int zadatakid) {
        this.korisnikid = korisnikid;
        this.zadatakid = zadatakid;
    }

    public int getKorisnikid() {
        return korisnikid;
    }

    public void setKorisnikid(int korisnikid) {
        this.korisnikid = korisnikid;
    }

    public int getZadatakid() {
        return zadatakid;
    }

    public void setZadatakid(int zadatakid) {
        this.zadatakid = zadatakid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) korisnikid;
        hash += (int) zadatakid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ZaduzenjePK)) {
            return false;
        }
        ZaduzenjePK other = (ZaduzenjePK) object;
        if (this.korisnikid != other.korisnikid) {
            return false;
        }
        if (this.zadatakid != other.zadatakid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.ZaduzenjePK[ korisnikid=" + korisnikid + ", zadatakid=" + zadatakid + " ]";
    }
    
}
