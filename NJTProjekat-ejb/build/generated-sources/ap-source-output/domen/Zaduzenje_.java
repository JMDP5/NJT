package domen;

import domen.Korisnik;
import domen.Zadatak;
import domen.ZaduzenjePK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-09-03T15:43:19")
@StaticMetamodel(Zaduzenje.class)
public class Zaduzenje_ { 

    public static volatile SingularAttribute<Zaduzenje, Zadatak> zadatak;
    public static volatile SingularAttribute<Zaduzenje, Korisnik> korisnik;
    public static volatile SingularAttribute<Zaduzenje, String> napomena;
    public static volatile SingularAttribute<Zaduzenje, ZaduzenjePK> zaduzenjePK;
    public static volatile SingularAttribute<Zaduzenje, Date> datumdodele;

}