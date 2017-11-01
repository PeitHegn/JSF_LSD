package dk.am.hackernews4.view;

import dk.am.hackernews4.model.Post;
import dk.am.hackernews4.view.util.JsfUtil;
import dk.am.hackernews4.view.util.JsfUtil.PersistAction;
import dk.am.hackernews4.facade.PostFacade;
import dk.am.hackernews4.model.Contributor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("postController")
@SessionScoped
public class PostController implements Serializable {

    @EJB
    private PostFacade ejbFacade;
    private List<Post> items = null;
    private List<Post> stories = null;
    private List<Post> comments = null;
    
    private Post selected;

    public PostController() {
    }

    public Post getSelected() {
        return selected;
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

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PostCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void createComment(){
        selected.setCreatedDate(new Date());
        selected.setPostType("comment");
        selected.setContributorId((Contributor)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInContributor"));
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PostCreated"));
    }
    
    public void createStory(){
        
        selected.setCreatedDate(new Date());
        selected.setContributorId((Contributor)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInContributor"));
        selected.setPostType("story");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PostCreated"));
        
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
