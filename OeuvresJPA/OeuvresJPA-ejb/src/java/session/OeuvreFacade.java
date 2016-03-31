/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import dao.Oeuvre;
import dao.Proprietaire;
import dao.Reservation;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Epulapp
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class OeuvreFacade {

    @PersistenceContext(unitName = "OeuvresJPA-ejb")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }
    
    @EJB
    private ProprietaireFacade proprietaireF;

    /**
     *
     * @param idProprio
     * @param titre
     * @param prix
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addOeuvre(int idProprio, String titre, Double prix){
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setPrix(BigDecimal.valueOf(prix));
        oeuvre.setTitre(titre);
        Proprietaire proprietaire = proprietaireF.findProprietaireById(idProprio);
        oeuvre.setProprietaire(proprietaire);
        
        em.persist(oeuvre);      
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateOeuvre(int idOeuvre, int idProprio, String titre, Double prix){
        Oeuvre oeuvre = findOeuvreById(idOeuvre);
        Proprietaire proprietaire = proprietaireF.findProprietaireById(idProprio);
        oeuvre.setPrix(BigDecimal.valueOf(prix));
        oeuvre.setTitre(titre);
        oeuvre.setProprietaire(proprietaire);
        
        em.merge(oeuvre);
    }
    
    public String deleteOeuvreById(int idOeuvre) throws Exception{        
        String titre;
        try{
            Oeuvre oeuvre = findOeuvreById(idOeuvre);
            titre = oeuvre.getTitre();
            em.remove(oeuvre);
        }catch(Exception e){
            throw e;
        }       
        return titre;
    }
    
    /**
     * Lecture d'une occurence d'oeuvre sur la clé primaire
     *
     * @param idOeuvre
     * @return
     */
    public Oeuvre findOeuvreById(int idOeuvre) {
        try {
            return em.find(Oeuvre.class, idOeuvre);
        } catch (Exception e) {
            throw e;
        }
    }

    public Object findAllOeuvres() {
        try {
            return (em.createNamedQuery("Oeuvre.findAll").getResultList());
        } catch (Exception e) {
            throw e;
        }
    }

    public Reservation findReservationById(Date dateReservation, int id_oeuvre) throws Exception {
        try {
           Query query = em. createNamedQuery("Reservation.findByDateReservationIdOeuvre");
           query.setParameter("dateReservation", dateReservation, TemporalType.DATE);
           query.setParameter("idOeuvre", id_oeuvre);
           return (Reservation)query.getSingleResult();
        } catch (Exception e) {
            throw e;
        }
    }

}
