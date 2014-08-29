/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans.izmena;

import domen.Zadatak;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import session.zadatak.ZadatakSession;

/**
 *
 * @author aleksandar
 */
@Named(value = "izmena")
@RequestScoped
public class IzmenaBean {
    @EJB
    private ZadatakSession zadatakSession;

    
    
    @ManagedProperty("#{param.id}")
    private String zadatakID;

    private Zadatak z;
    
    @PostConstruct
    public void init() {
        this.z = zadatakSession.vratiZadatak(Integer.parseInt(zadatakID));
    }
    /**
     * Creates a new instance of IzmenaBean
     */
    public IzmenaBean() {

    }

    public String getZadatakID() {
        return zadatakID;
    }

    public void setZadatakID(String zadatakID) {
        this.zadatakID = zadatakID;
    }

    public Zadatak getZ() {
        return z;
    }

    public void setZ(Zadatak z) {
        this.z = z;
    }

}
