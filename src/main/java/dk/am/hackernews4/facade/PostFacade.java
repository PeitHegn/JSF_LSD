/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.am.hackernews4.facade;

import dk.am.hackernews4.model.Post;
import dk.am.hackernews4.model.RolfHelgePost;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Doctor
 */
@Stateless
public class PostFacade extends AbstractFacade<Post> {

    @PersistenceContext(unitName = "dk.am_HackerNews4_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PostFacade() {
        super(Post.class);
    }

    public List<Post> findAllStories() {
        return em.createNamedQuery("Post.findByPostType").setParameter("postType", "story").getResultList();
    }

    public List<Post> findAllComments() {
        return em.createNamedQuery("Post.findByPostType").setParameter("postType", "comment").getResultList();
    }

    public List<RolfHelgePost> findAllRolfHelgePosts() {

        List<RolfHelgePost> rolfHelgePosts = null;
        try {
            rolfHelgePosts = em.createNamedQuery("Post.findRolfHelgePost", RolfHelgePost.class).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rolfHelgePosts;
    }

    public Integer findHighestHanesstId() {
        Integer hanesstId = null;
        try {
            BigInteger bi = (BigInteger)em.createNamedQuery("Post.findHighestHanessId").getSingleResult();
            hanesstId = bi.intValue();
        } catch (Exception ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hanesstId;
    }

    public Post findFromPostId(BigDecimal postId) {
        Post post = null;
        try {
            post = em.createNamedQuery("Post.findByPostId", Post.class).setParameter("postId", postId).getSingleResult();
        } catch (Exception ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return post;
    }

}
