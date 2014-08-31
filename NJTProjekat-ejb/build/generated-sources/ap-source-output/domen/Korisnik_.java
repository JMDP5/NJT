package domen;

import domen.Mesto;
import domen.Zaduzenje;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-31T16:59:01")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile SingularAttribute<Korisnik, Integer> korisnikid;
    public static volatile ListAttribute<Korisnik, Zaduzenje> zaduzenjeList;
    public static volatile SingularAttribute<Korisnik, String> aktivacionikod;
    public static volatile SingularAttribute<Korisnik, String> email;
    public static volatile SingularAttribute<Korisnik, Integer> status;
    public static volatile SingularAttribute<Korisnik, String> korisnickoime;
    public static volatile SingularAttribute<Korisnik, Mesto> mesto;
    public static volatile SingularAttribute<Korisnik, String> ime;
    public static volatile SingularAttribute<Korisnik, Integer> tipkorisnika;
    public static volatile SingularAttribute<Korisnik, String> lozinka;

}