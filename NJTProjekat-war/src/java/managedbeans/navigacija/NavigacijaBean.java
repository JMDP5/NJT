/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans.navigacija;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author aleksandar
 */
@Named(value = "navigacija")
@RequestScoped
public class NavigacijaBean {

    /**
     * Creates a new instance of NavigacijaBean
     */
    public NavigacijaBean() {
    }

    public static String redirect(int type) {
        if (type == 0) {
            return "radnikmain?faces-redirect=true";
        } else if (type == 1) {
            return "obradazadataka?faces-redirect=true";
        }
        return "";
    }

}
