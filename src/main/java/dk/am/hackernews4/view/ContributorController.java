package dk.am.hackernews4.view;

import dk.am.hackernews4.model.Contributor;
import dk.am.hackernews4.view.util.JsfUtil;
import dk.am.hackernews4.view.util.JsfUtil.PersistAction;
import dk.am.hackernews4.facade.ContributorFacade;
import java.io.IOException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

@Named("contributorController")
@SessionScoped
public class ContributorController implements Serializable {

    @EJB
    private dk.am.hackernews4.facade.ContributorFacade ejbFacade;
    private List<Contributor> items = null;
    private Contributor selected;

    public ContributorController() {
    }

    public Contributor getSelected() {
        return selected;
    }

    public void setSelected(Contributor selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ContributorFacade getFacade() {
        return ejbFacade;
    }

    public Contributor prepareCreate(ActionEvent event) {
        System.out.println("RAMT!!");
        selected = new Contributor();
        initializeEmbeddableKey();
//        RequestContext.getCurrentInstance().update("ContributorForm");
        return selected;
    }

    public void prepareNewContributor() {
        System.out.println("RAMT!!");
        selected = new Contributor();
        RequestContext.getCurrentInstance().update("ContributorForm:display:grid");
        initializeEmbeddableKey();
        try {

            FacesContext.getCurrentInstance().getExternalContext().redirect("createUser.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ContributorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String create() {
        selected.setCreatedDate(new Date());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ContributorCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        return "index.xhtml?faces-redirect=true";
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ContributorUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ContributorDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Contributor> getItems() {
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

    public Contributor findContributorForLogin(String username, String password) {
        return getFacade().findContributorForLogin(username, password);
    }

    public Contributor getContributor(java.math.BigDecimal id) {
        return getFacade().find(id);
    }

    public List<Contributor> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Contributor> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Contributor.class)
    public static class ContributorControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContributorController controller = (ContributorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "contributorController");
            return controller.getContributor(getKey(value));
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
            if (object instanceof Contributor) {
                Contributor o = (Contributor) object;
                return getStringKey(o.getContributorId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Contributor.class.getName()});
                return null;
            }
        }

    }

}
