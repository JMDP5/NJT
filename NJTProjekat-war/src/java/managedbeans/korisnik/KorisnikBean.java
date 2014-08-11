/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans.korisnik;

import domen.Korisnik;
import domen.Mesto;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import session.korisnik.KorisnikSession;

/**
 *
 * @author aleksandar
 */
@Named(value = "korisnik")
@SessionScoped
public class KorisnikBean implements Serializable {

    @EJB
    private KorisnikSession korisnikSession;

    private Korisnik korisnik;
    private String mestoId;

    /**
     * Creates a new instance of KorisnikBean
     */
    public KorisnikBean() {
    }

    @PostConstruct
    public void init() {
        korisnik = new Korisnik();
    }

    public List<Korisnik> vratiSveKorisnike() {
        return korisnikSession.vratiSveKorisnike();
    }

    public List<Mesto> vratiMesta() {
        SelectItem[] option = null;
        List<Mesto> mestaList = korisnikSession.vratiSvaMesta();
//        int i = 0;
//        option = new SelectItem[mestaList.size()];
//        for (Mesto m : mestaList) {
//            option[i++] = new SelectItem(m, m.getNazivmesta());
//        }
//        return option;
        return mestaList;

    }

    public void registrujKorisnika() {
        korisnik.setMesto(korisnikSession.vratiMesto(mestoId));
        korisnikSession.ubaci(korisnik);
        posaljiAktivacioniMail();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Korisnik unet", "Molimo aktivirajte prvo svoj nalog!"));
    }

    public Korisnik prikaziUlogovanogRadnika() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        Korisnik r = (Korisnik) session.getAttribute("korisnik");
        return r;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public String getMestoId() {
        return mestoId;
    }

    public void setMestoId(String mestoId) {
        this.mestoId = mestoId;
    }

    private void posaljiAktivacioniMail() {
        final String username = "aleksandar.buha05@gmail.com";
        final String password = "Ivana5391";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("aleksandar.buha05@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(korisnik.getEmail()));
            message.setSubject("Registracioni mejl");
            message.setText("Postovani/a " + korisnik.getIme() + ", \n" +
                    "Molimo Vas prvo aktivirajte svoj nalog klikom na link: "
                    + "\n\n http://localhost:8080/NJTProjekat-war/faces/aktivacija.xhtml?key=" + korisnik.getKorisnickoime());

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}