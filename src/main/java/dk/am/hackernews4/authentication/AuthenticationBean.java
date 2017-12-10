/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.am.hackernews4.authentication;

import dk.am.hackernews4.model.Contributor;
import dk.am.hackernews4.view.ContributorController;
import dk.am.hackernews4.view.util.JsfUtil;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Doctor
 */
@Named("authenticationBean")
@SessionScoped
public class AuthenticationBean implements Serializable {

    @Inject
    private ContributorController contributorController;

    private Contributor loggedInContributor;
    private String username;
    private String password;

    public String doLogin() {
        loggedInContributor = contributorController.findContributorForLogin(username, password);
        if (loggedInContributor != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInContributor", loggedInContributor);
            String msg = "You are now logged in";
            JsfUtil.addSuccessMessage(msg);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            return "index.xhml?faces-redirect=true";
        } else {
            String msg = "Login failed! Better luck next time";
            JsfUtil.addErrorMessage(msg);
            return "login.xhtml";
        }
    }

    public String doLogout() {
        loggedInContributor = null;
        String msg = "You are now logged out";
        JsfUtil.addSuccessMessage(msg);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("loggedInContributor", loggedInContributor);
        RequestContext.getCurrentInstance().update("storyListForm");
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        return "index.xhtml?faces-redirect=true";
    }

    public Contributor getLoggedInContributor() {
        return loggedInContributor;
    }

    public void setLoggedInContributor(Contributor loggedInContributor) {
        this.loggedInContributor = loggedInContributor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
