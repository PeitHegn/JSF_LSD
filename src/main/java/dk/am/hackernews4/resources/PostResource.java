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
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.exporter.common.TextFormat;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

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

    private static final Counter STATUS_REQUEST = Counter.build()
            .name("staus_total")
            .help("Number of times status has been served.").register();

    private static final Counter STATUS_LATEST = Counter.build()
            .name("latest_total")
            .help("Number of times latest has been served.").register();

    private static final Counter STATUS_POST = Counter.build()
            .name("post_total")
            .help("Number of times post has been served.").register();

    @GET
    @Path("/status")
    @Produces(MediaType.TEXT_PLAIN)
    public String status() {
        STATUS_REQUEST.inc();
        return "Alive";
    }

    @GET
    @Path("/latest")
    @Produces(MediaType.TEXT_PLAIN)
    public int findAllRolfHelgePosts() {
        STATUS_LATEST.inc();
        int hanesstId = 0;
                if(postFacade.findHighestHanesstId()!= null)
                    hanesstId = postFacade.findHighestHanesstId();
        return hanesstId;
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public void digestPost(RolfHelgePost rolfHelgePost) {

        Contributor contributor = null;
        contributor = contributorFacade.findContributorForLogin(rolfHelgePost.getUsername(), rolfHelgePost.getPwd_hash());

        if (null == contributor) {
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
        STATUS_POST.inc();

    }

    @GET
    @Path("/metrics")
    @Produces(MediaType.TEXT_PLAIN)
    public StreamingOutput metrics() {
        return new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                try (Writer writer = new OutputStreamWriter(output)) {
                    TextFormat.write004(writer, CollectorRegistry.defaultRegistry.metricFamilySamples());
                }
            }
        };
    }

}
