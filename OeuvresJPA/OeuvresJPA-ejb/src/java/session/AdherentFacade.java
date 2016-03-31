/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import dao.Adherent;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Epulapp
 */
@Stateless
@LocalBean
public class AdherentFacade {

    @PersistenceContext(unitName = "OeuvresJPA-ejb")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }
    
    public Object findAllAdherents() {
        try {
            return (em.createNamedQuery("Adherent.findAll").getResultList());
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Adherent findOeuvreById(int idAdherent) {
        try {
            return em.find(Adherent.class, idAdherent);
        } catch (Exception e) {
            throw e;
        }
    }
}
