package dk.am.hackernews4.model;

import dk.am.hackernews4.model.Post;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-01T12:25:40")
@StaticMetamodel(Contributor.class)
public class Contributor_ { 

    public static volatile SingularAttribute<Contributor, String> contributorPassword;
    public static volatile SingularAttribute<Contributor, BigInteger> score;
    public static volatile SingularAttribute<Contributor, Date> createdDate;
    public static volatile SingularAttribute<Contributor, BigDecimal> contributorId;
    public static volatile ListAttribute<Contributor, Post> postList;
    public static volatile SingularAttribute<Contributor, String> contributorEmail;
    public static volatile SingularAttribute<Contributor, String> contributorName;

}