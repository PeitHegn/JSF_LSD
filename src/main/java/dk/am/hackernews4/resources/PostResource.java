/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.am.hackernews4.resources;

import dk.am.hackernews4.facade.ContributorFacade;
import dk.am.hackernews4.facade.PostFacade;
import dk.am.hackernews4.model.Contributor;
import dk.am.hackernews4.model.Post;
import dk.am.hackernews4.model.RolfHelgePost;
import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Doctor
 */

@Path("/")
@RequestScoped
public class PostResource {
    
    
    @Inject
    private PostFacade postFacade;
    @Inject
    private ContributorFacade contributorFacade;
    
    @GET
    @Path("/latest")
    @Produces(MediaType.TEXT_PLAIN)
    public String findAllRolfHelgePosts(){
    return String.valueOf(postFacade.findHighestHanesstId());
    }
    
    
    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public void digestPost(RolfHelgePost rolfHelgePost){
        System.out.println("rolfHelgePost username: " + rolfHelgePost.getUsername()+ " passwword: " +rolfHelgePost.getPwd_hash() 
        + " parentId: "+ rolfHelgePost.getPost_parent());
        
        Contributor contributor = null;
        contributor = contributorFacade.findContributorForLogin(rolfHelgePost.getUsername(), rolfHelgePost.getPwd_hash());
     
        if(null == contributor){
            contributor = new Contributor();
            contributor.setContributorName(rolfHelgePost.getUsername());
            contributor.setContributorPassword(rolfHelgePost.getPwd_hash());
            contributor.setCreatedDate(new Date());
            contributorFacade.create(contributor);
            contributor = contributorFacade.findContributorForLogin(rolfHelgePost.getUsername(), rolfHelgePost.getPwd_hash());
        }
        
        Post post = new Post();
        Post parent = postFacade.findFromPostId(new BigDecimal(rolfHelgePost.getPost_parent()));
        
        post.setContributorId(contributor);
        post.setCreatedDate(new Date());
        post.setParentId(parent);
        post.setHanesstId(BigInteger.valueOf(rolfHelgePost.getHanesst_id()));
        post.setPostType(rolfHelgePost.getPost_type());
        post.setPostText(rolfHelgePost.getPost_text());
        post.setPostTitle(rolfHelgePost.getPost_title());
        post.setPostUrl(rolfHelgePost.getPost_url());
        
        postFacade.create(post);
        
    }
    
}
