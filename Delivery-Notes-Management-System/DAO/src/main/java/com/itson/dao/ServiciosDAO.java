/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.dao;

import com.itson.dominio.Servicio;
import com.itson.interfaces.IServiciosDAO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author alexasoto
 */
public class ServiciosDAO implements IServiciosDAO {
    
    private EntityManagerFactory em = null;

    public ServiciosDAO() {
        em = Persistence.createEntityManagerFactory("com.itson.planchaexpress");
    }

    public EntityManager getEntityManager() {
        return em.createEntityManager();
    }

    @Override
    public boolean insertarServicio(Servicio servicio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(servicio);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em != null) {
                em.getTransaction().rollback();
                System.err.print(ex.getMessage());
                return false;
            }
        } finally {
            if (em != null) {
                em.close();
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean eliminarServicio(Long id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Servicio servicio = em.find(Servicio.class, id); 

            if (servicio != null) {
                em.remove(servicio);
                em.getTransaction().commit();
                System.out.println("Servicio eliminado exitosamente");
                return true;
            } else {
                System.out.println("Servicio no encontrado");
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Error al eliminar servicio: " + ex.getMessage());
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Deshacer la transacción en caso de error
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return false;
    }

    @Override
    public Servicio buscarServicio(Long id) {
        EntityManager em = null;
        Servicio servicio = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT s FROM Servicios s where s.id = :id");
            query.setParameter("id", id);
            servicio = (Servicio) query.getSingleResult();
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        em.close();
        return servicio;
    }

    @Override
    public boolean actualizarServicio(Servicio servicio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(servicio);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em != null) {
                em.getTransaction().rollback();
                System.err.print(ex.getMessage());
            }
            return false;
        } finally {
            if (em != null) {
                em.close();
                return false;
            }
        }
    }
    
}
