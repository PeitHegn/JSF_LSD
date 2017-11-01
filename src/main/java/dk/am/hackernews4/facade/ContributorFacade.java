/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.am.hackernews4.facade;

import dk.am.hackernews4.model.Contributor;
import java.math.BigDecimal;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Doctor
 */
@Stateless
public class ContributorFacade extends AbstractFacade<Contributor> {

    @PersistenceContext(unitName = "dk.am_HackerNews4_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContributorFacade() {
        super(Contributor.class);
    }

    public Contributor findContributorForLogin(String username, String password) {
        Contributor contributor = null;
        try {
            contributor =  em.createNamedQuery("Contributor.findByContributorNameAndPassword", Contributor.class).setParameter("contributorName", username).setParameter("contributorPassword", password).getSingleResult();
        } catch (Exception ex) {
            System.out.println("Error in findContributorForLogin" + ex.getMessage());
        }
        return contributor;
    }
    
    public Contributor findContributorFromId(BigDecimal id){
        
        Contributor contributor = null;
        
        try{
           contributor = em.createNamedQuery("Contributor.findByContributorId", Contributor.class).setParameter("contributorId", id).getSingleResult();
        }
        catch(Exception ex){
            System.out.println("findContributorFromId failed: "+ex.getMessage());
        }
        return contributor;
    }

}
