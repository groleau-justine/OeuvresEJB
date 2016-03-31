/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import dao.Proprietaire;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Epulapp
 */
@Stateless
@LocalBean
public class ProprietaireFacade {

   @PersistenceContext(unitName = "OeuvresJPA-ejb")
   private EntityManager em;
   
    public EntityManager getEm() {
        return em;
    }
    
    public Proprietaire findProprietaireById(int idProprio){
        try {
            return em.find(Proprietaire.class, idProprio);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Proprietaire findProprietaireByLogin(String login){
        try {
           Query query = em.createNamedQuery("Proprietaire.findByLogin");
           query.setParameter("login", login);

           return (Proprietaire)query.getSingleResult();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Object findAllProprietaires() {
        try {
            return (em.createNamedQuery("Proprietaire.findAll").getResultList());
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Proprietaire connecter(String login, String pwd) throws Exception {
        try {
            Proprietaire p = findProprietaireByLogin(login);
            if (pwd.equals(p.getPwd())) {
                return p;
            }           
            return null;
        } catch (Exception e) {
            throw e;
        }
    }
}
