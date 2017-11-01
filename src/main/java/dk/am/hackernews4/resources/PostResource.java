/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.am.hackernews4.resources;

import dk.am.hackernews4.facade.PostFacade;
import dk.am.hackernews4.model.RolfHelgePost;
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
    
    @GET
    @Path("/latest")
    @Produces(MediaType.TEXT_PLAIN)
    public String findAllRolfHelgePosts(){
//        return postFacade.findAllRolfHelgePosts();
    return String.valueOf(postFacade.findHighestHanesstId());
    }
    
    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public void digestPost(RolfHelgePost rolfHelgePost){
        
    }
}
