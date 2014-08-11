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
public class SlikaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "zadatakid")
    private int zadatakid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "slikaid")
    private int slikaid;

    public SlikaPK() {
    }

    public SlikaPK(int zadatakid, int slikaid) {
        this.zadatakid = zadatakid;
        this.slikaid = slikaid;
    }

    public int getZadatakid() {
        return zadatakid;
    }

    public void setZadatakid(int zadatakid) {
        this.zadatakid = zadatakid;
    }

    public int getSlikaid() {
        return slikaid;
    }

    public void setSlikaid(int slikaid) {
        this.slikaid = slikaid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) zadatakid;
        hash += (int) slikaid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SlikaPK)) {
            return false;
        }
        SlikaPK other = (SlikaPK) object;
        if (this.zadatakid != other.zadatakid) {
            return false;
        }
        if (this.slikaid != other.slikaid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.SlikaPK[ zadatakid=" + zadatakid + ", slikaid=" + slikaid + " ]";
    }
    
}
