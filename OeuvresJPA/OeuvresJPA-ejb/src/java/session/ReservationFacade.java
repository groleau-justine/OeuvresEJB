/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import dao.Adherent;
import dao.Oeuvre;
import dao.Reservation;
import dao.ReservationPK;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Epulapp
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReservationFacade {

    @PersistenceContext(unitName = "OeuvresJPA-ejb")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }
    
    @EJB
    private OeuvreFacade oeuvreF;
    
    @EJB
    private AdherentFacade adherentF;
    
    public Object findAllReservations() {
        try {
            return (em.createNamedQuery("Reservation.findAll").getResultList());
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Reservation findReservationById(Date dateReservation, int idOeuvre) {
        try {
            return em.find(Reservation.class, new ReservationPK(dateReservation, idOeuvre));
        } catch (Exception e) {
            throw e;
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addReservation(int idOeuvre, Date dateReservation, int idAdherent){
       
        Reservation reservation = new Reservation();
           
        Oeuvre oeuvre = oeuvreF.findOeuvreById(idOeuvre);
        reservation.setOeuvre(oeuvre);
        
        Adherent adherent = adherentF.findOeuvreById(idAdherent);
        reservation.setAdherent(adherent);
        
        ReservationPK rPK = new ReservationPK(dateReservation, idOeuvre);
        reservation.setReservationPK(rPK);
        
        reservation.setStatut("Attente");
        
        em.persist(reservation);  
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateReservation(int idOeuvre, Date dateReservation){
        Reservation reservation = findReservationById(dateReservation, idOeuvre);       
        reservation.setStatut("Confirmée");

        em.merge(reservation);
    }
    
    public void deleteReservationById(Date date, int idOeuvre) throws Exception{        
        try{
            Reservation reservation = findReservationById(date, idOeuvre);           
            em.remove(reservation);
        }catch(Exception e){
            throw e;
        }
    }
    
}
