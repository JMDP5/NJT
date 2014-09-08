/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans.korisnik;

import domen.Korisnik;
import domen.Mesto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
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
@ViewScoped
public class KorisnikBean implements Serializable {

    @EJB
    private KorisnikSession korisnikSession;

    private Korisnik korisnik;
    private String mestoId;
    private String email;
    private String username;

    /**
     * Creates a new instance of KorisnikBean
     */
    public KorisnikBean() {
    }

    @PostConstruct
    public void init() {
        korisnik = new Korisnik();

    }

    public List<Mesto> vratiMesta() {
        SelectItem[] option = null;
        List<Mesto> mestaList = korisnikSession.pronadjiMesta();
//        int i = 0;
//        option = new SelectItem[mestaList.size()];
//        for (Mesto m : mestaList) {
//            option[i++] = new SelectItem(m, m.getNazivmesta());
//        }
//        return option;
        return mestaList;

    }

    public void registrujKorisnika() {
        try {
            korisnik.setMesto(korisnikSession.vratiMesto(mestoId));
            korisnik.setKorisnickoime(username);
            korisnik.setTipkorisnika(1); //1 - radnik 2 - menadzer
            korisnik.setStatus(0); //0 - Neaktivan 1- OK 2-Obrisan
            korisnik.setAktivacioniKod(vratiAktivacioniKod());
            korisnik.setEmail(this.email);
            korisnikSession.registrujRadnika(korisnik);
            posaljiAktivacioniMail(korisnik.getEmail(), korisnik.getAktivacioniKod());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Registracija uspesna!", "Molimo aktivirajte prvo svoj nalog!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("f1:registruj", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Greska! " + e.getMessage()));
        }

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

    private void posaljiAktivacioniMail(String email, String kod) {
        final String username = "njtprojekatfon@gmail.com";
        final String password = "njtprojekat";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username.trim(), password.trim());
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("njtprojekatfon@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Registracioni mejl");
            message.setText("Postovani/a " + korisnik.getIme() + ", \n"
                    + "Molimo Vas prvo aktivirajte svoj nalog klikom na link: "
                    + "\n\n http://localhost:8080/NJTProjekat-war/stranice/aktivacija.xhtml?key=" + kod);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Mail nije poslat! Kontaktirajte administratora!");
        }
    }

    private String vratiAktivacioniKod() {
        StringBuilder buffer = new StringBuilder();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int charactersLength = characters.length();

        for (int i = 0; i < 9; i++) {
            double index = Math.random() * charactersLength;
            buffer.append(characters.charAt((int) index));
        }
        return buffer.toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

}
