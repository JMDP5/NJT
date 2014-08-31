package domen;

import domen.Slika;
import domen.Zaduzenje;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-31T16:59:01")
@StaticMetamodel(Zadatak.class)
public class Zadatak_ { 

    public static volatile SingularAttribute<Zadatak, Integer> zadatakid;
    public static volatile SingularAttribute<Zadatak, String> nazivzadatka;
    public static volatile ListAttribute<Zadatak, Zaduzenje> zaduzenjeList;
    public static volatile ListAttribute<Zadatak, Slika> slikaList;
    public static volatile SingularAttribute<Zadatak, String> status;
    public static volatile SingularAttribute<Zadatak, Date> rokzaizvrsenje;
    public static volatile SingularAttribute<Zadatak, String> opis;

}