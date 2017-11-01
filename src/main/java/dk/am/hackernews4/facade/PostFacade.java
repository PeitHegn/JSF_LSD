/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.am.hackernews4.facade;

import dk.am.hackernews4.model.Post;
import dk.am.hackernews4.model.RolfHelgePost;
import java.util.List;
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
    
    public List<Post> findAllStories(){
        return em.createNamedQuery("Post.findByPostType").setParameter("postType", "story").getResultList();
    }
    
    public List<Post> findAllComments(){
        return em.createNamedQuery("Post.findByPostType").setParameter("postType", "comment").getResultList();
    }
    
    public List<RolfHelgePost> findAllRolfHelgePosts(){
        return em.createNamedQuery("Post.findRolfHelgePost", RolfHelgePost.class).getResultList();
    }
    
}
