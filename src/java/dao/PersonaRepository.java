/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidades.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;   

/**
 *
 * @author MSI GF63
 */
public class PersonaRepository extends BaseRepository<Persona>{

    @Override
    public boolean guardar(Persona entidad) {
        EntityManager entityManager= this.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entidad);
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    public boolean actualizar(Persona personaActualizada) {
        EntityManager entityManager = this.createEntityManager();
        entityManager.getTransaction().begin();
        Persona persona = entityManager.find(Persona.class,personaActualizada.getId());
        if (persona != null) {
            persona.setNombre(personaActualizada.getNombre());
            persona.setApellido(personaActualizada.getApellido());
            entityManager.merge(persona);
            entityManager.getTransaction().commit();
            return true;
        }
        entityManager.getTransaction().commit();
        return false;
    }

    public boolean eliminar(Long id){
        EntityManager em = this.createEntityManager();
        em.getTransaction().begin();
        Persona persona = em.find(Persona.class, id);
        if (persona != null) {
            em.remove(persona);
            em.getTransaction().commit();
            em.close();
            return true;
        }
        em.getTransaction().commit();
        em.close();
        return false;
    }
            
    @Override
    public Persona buscarporID(long id) {
        EntityManager entityManager = this.createEntityManager();
        entityManager.getTransaction().begin();
        Persona persona = entityManager.find(Persona.class, id);
        entityManager.getTransaction().commit();
        return persona;
    }

    @Override
    public ArrayList<Persona> buscarTodas() {
        EntityManager entityManager = this.createEntityManager();
        List<Persona> p = entityManager.createQuery("SELECT p FROM Persona p").getResultList();
        return new ArrayList<>(p);
    }
    
    protected EntityManager getEntityManager() {
        return this.createEntityManager();
    }
    
    @Override
    public List<Persona> buscarComo(String busqueda) {
         
        return null;
    }
    
}
