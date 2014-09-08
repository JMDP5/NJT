/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import session.korisnik.KorisnikSession;

/**
 *
 * @author aleksandar
 */
@Named(value = "usernamevalidation")
@RequestScoped
public class UsernameValidation implements Validator {

    @EJB
    private KorisnikSession korisnikSession;

    /**
     * Creates a new instance of UsernameValidation
     */
    public UsernameValidation() {
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        String username = (String) value;

        if (korisnikSession.prijaviSe(username) != null) {
            throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Korisnicko ime zauzetom! Molimo izaberite drugo.", null));
        } 
    }

}
