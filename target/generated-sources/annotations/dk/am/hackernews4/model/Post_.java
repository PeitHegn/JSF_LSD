package dk.am.hackernews4.model;

import dk.am.hackernews4.model.Contributor;
import dk.am.hackernews4.model.Post;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-01T19:54:56")
@StaticMetamodel(Post.class)
public class Post_ { 

    public static volatile SingularAttribute<Post, String> postUrl;
    public static volatile SingularAttribute<Post, BigInteger> score;
    public static volatile SingularAttribute<Post, String> postText;
    public static volatile SingularAttribute<Post, Date> createdDate;
    public static volatile SingularAttribute<Post, String> postType;
    public static volatile SingularAttribute<Post, Contributor> contributorId;
    public static volatile ListAttribute<Post, Post> postList;
    public static volatile SingularAttribute<Post, String> postTitle;
    public static volatile SingularAttribute<Post, BigDecimal> postId;
    public static volatile SingularAttribute<Post, Post> parentId;
    public static volatile SingularAttribute<Post, BigInteger> hanesstId;

}