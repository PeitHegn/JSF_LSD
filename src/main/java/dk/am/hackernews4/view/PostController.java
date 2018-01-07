package dk.am.hackernews4.view;

import dk.am.hackernews4.model.Post;
import dk.am.hackernews4.view.util.JsfUtil;
import dk.am.hackernews4.view.util.JsfUtil.PersistAction;
import dk.am.hackernews4.facade.PostFacade;
import dk.am.hackernews4.model.Contributor;
import io.prometheus.client.Histogram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@Named("postController")
@SessionScoped
public class PostController implements Serializable {

    @EJB
    private PostFacade ejbFacade;
    private List<Post> items = null;
    private List<Post> stories = null;
    private List<Post> comments = null;
    private List<Post> jobs = null;
    private List<Post> questions = null;
    private Post selected;
    private Post parentComment;
    private Post reply;
    private Post comment;
    private TreeNode treeNodeParent;
    private TreeNode treeNode;

    private static final Histogram REQUEST_LATENCY_COMMENT = Histogram.build()
            .name("create_comment_latency")
            .help("Request for creating a comment latency in seconds.")
            .register();

    private static final Histogram REQUEST_LATENCY_STORY = Histogram.build()
            .name("create_story_latency")
            .help("Request for creating a story latency in seconds.")
            .register();

    public PostController() {
    }

    public Post getSelected() {
        return selected;
    }

    public Post getParentComment() {
        return parentComment;
    }

    public void setParentComment(Post parentComment) {
        this.parentComment = parentComment;
    }

    public void setSelected(Post selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PostFacade getFacade() {
        return ejbFacade;
    }

    public Post prepareCreate() {
        selected = new Post();
        initializeEmbeddableKey();
        return selected;
    }

    public Post prepareReplyCreate() {
        reply = new Post();
        initializeEmbeddableKey();
        return reply;
    }
    
    public Post prepareAddComment() {
        comment = new Post();
        initializeEmbeddableKey();
        return comment;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PostCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void refreshParent() {
        parentComment = null;
        treeNodeParent = null;
    }

    public void addComment() {
        comment.setParentId(selected);
        comment.setContributorId((Contributor) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInContributor"));
        comment.setCreatedDate(new Date());
        comment.setPostType("comment");
        if (null == selected.getPostList()) {
            selected.setPostList(new ArrayList<Post>());
            selected.getPostList().add(comment);
        } else {
            selected.getPostList().add(comment);
        }
        update();
    }

    public void createComment() {
        Histogram.Timer requestTimer = REQUEST_LATENCY_COMMENT.startTimer();
        selected.setCreatedDate(new Date());
        selected.setPostType("comment");
        selected.setContributorId((Contributor) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInContributor"));
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PostCreated"));
        requestTimer.observeDuration();
    }

    public void replyComment() {
        System.out.println("------------------RAMT------------------------");
        reply.setCreatedDate(new Date());
        reply.setPostType("comment");
        parentComment = (Post) treeNodeParent.getData();
        reply.setParentId(parentComment);
        reply.setContributorId((Contributor) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInContributor"));
        attachReply(selected);
        update();
        parentComment = null;
        treeNodeParent = null;
    }

    public void attachReply(Post parent) {
        if (null != parent.getPostList()) {
            for (Post post : parent.getPostList()) {
                if (post.equals(parentComment)) {
                    if (null == post.getPostList()) {
                        post.setPostList(new ArrayList<Post>());
                        post.getPostList().add(reply);
                        break;
                    } else {
                        post.getPostList().add(reply);
                        break;
                    }
                }
                attachReply(post);
            }
        }
    }

    public TreeNode getTreeNode() {
        if (selected != null) {
            treeNode = new DefaultTreeNode(selected, null);
            if (null != selected.getPostList()) {
                for (Post post : selected.getPostList()) {
                    TreeNode comments = new DefaultTreeNode(post, treeNode);
                    createSubNode(post, comments);
                }
            }
        }

        return treeNode;
    }

    private void createSubNode(Post parrent, TreeNode node) {
        List<Post> postList = parrent.getPostList();
        if (null != postList) {
            for (Post subPost : postList) {
                TreeNode subNode = new DefaultTreeNode(subPost, node);
                createSubNode(subPost, subNode);
            }
        }
    }

    public void createStory() {
        Logger.getLogger(PostController.class.getName()).log(Level.INFO, null, "starting create story method");
        Histogram.Timer requestTimer = REQUEST_LATENCY_STORY.startTimer();
        selected.setCreatedDate(new Date());
        selected.setContributorId((Contributor) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInContributor"));
        selected.setPostType("story");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PostCreated"));
        requestTimer.observeDuration();
        Logger.getLogger(PostController.class.getName()).log(Level.INFO, null, "finishing create story method");
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PostUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PostDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Post> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();

            try {
                if (persistAction == PersistAction.CREATE) {
                    getFacade().create(selected);
                } else if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage("An error occurred while trying to persist!");
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Post getPost(java.math.BigDecimal id) {
        return getFacade().find(id);
    }

    public List<Post> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Post> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<Post> getStories() {
        stories = getFacade().findAllStories();
        return stories;
    }

    public void setStories(List<Post> stories) {
        this.stories = stories;
    }

    public List<Post> getComments() {
        comments = getFacade().findAllComments();
        return comments;
    }

    public void setComments(List<Post> comments) {
        this.comments = comments;
    }

    public List<Post> getJobs() {
        jobs = getFacade().findAllJobs();
        return jobs;
    }

    public void setJobs(List<Post> jobs) {
        this.jobs = jobs;
    }

    public List<Post> getQuestions() {
        questions = getFacade().findAllQuestions();
        return questions;
    }

    public void setQuestions(List<Post> questions) {
        this.questions = questions;
    }
    
    

    public TreeNode getTreeNodeParent() {
        return treeNodeParent;
    }

    public void setTreeNodeParent(TreeNode treeNodeParent) {
        this.treeNodeParent = treeNodeParent;
    }

    public Post getReply() {
        return reply;
    }

    public void setReply(Post reply) {
        this.reply = reply;
    }

    public Post getComment() {
        return comment;
    }

    public void setComment(Post comment) {
        this.comment = comment;
    }

    @FacesConverter(forClass = Post.class)
    public static class PostControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PostController controller = (PostController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "postController");
            return controller.getPost(getKey(value));
        }

        java.math.BigDecimal getKey(String value) {
            java.math.BigDecimal key;
            key = new java.math.BigDecimal(value);
            return key;
        }

        String getStringKey(java.math.BigDecimal value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Post) {
                Post o = (Post) object;
                return getStringKey(o.getPostId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Post.class.getName()});
                return null;
            }
        }

    }

}
