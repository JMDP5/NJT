package domen;

import domen.Korisnik;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-23T13:41:50")
@StaticMetamodel(Mesto.class)
public class Mesto_ { 

    public static volatile SingularAttribute<Mesto, String> nazivmesta;
    public static volatile SingularAttribute<Mesto, Integer> pttbroj;
    public static volatile ListAttribute<Mesto, Korisnik> korisnikList;

}