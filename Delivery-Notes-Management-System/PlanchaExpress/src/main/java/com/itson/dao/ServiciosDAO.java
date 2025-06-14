/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.dao;

import com.itson.dominio.Servicio;
import com.itson.interfaces.IServiciosDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;

/**
 *
 * @author alexasoto
 */
public class ServiciosDAO implements IServiciosDAO {
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson.planchaexpress");
    EntityManager em = emf.createEntityManager();

    public ServiciosDAO() {
    }

    @Override
    public boolean insertarServicio(String descripcion, float precio) {
        try {
            em.getTransaction().begin();
            Servicio servicio = new Servicio(descripcion, precio);
            em.persist(servicio);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "El servicio: " + servicio.getDescripcion() + " se insertó correctamente");
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar el servicio");
//            em.getTransaction().rollback();
            return false;
        }
    }
    
    public Servicio consultaServicio(Long id) {
        try {
            //Busca el id en la clase Servicio
            return em.find(Servicio.class, id);
        } catch (PersistenceException ex) {
            JOptionPane.showMessageDialog(null, "Error al consultar el servicio");
            return null;
        }
    }

    @Override
    public boolean eliminarServicio(Long id) {
        try {
            em.getTransaction().begin();
           
            Servicio servicio = em.find(Servicio.class, id);
            
            if (servicio != null) {
                em.remove(servicio);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    
    }

    @Override
    public List<Servicio> buscarServicios() {
        try {
            TypedQuery<Servicio> query = em.createQuery("SELECT s FROM Servicio s", Servicio.class);
        return query.getResultList();
        } catch (PersistenceException ex) {
            JOptionPane.showMessageDialog(null, "No hay servicios registrados");
            em.getTransaction().rollback();
        }
        return null;
    }

    @Override
    public boolean editarServicio(Servicio servicio) {
        try {
            em.getTransaction().begin();
            em.merge(servicio); // Actualiza la entidad en la base de datos
            JOptionPane.showMessageDialog(null, "Servicio actualizado");
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            // Manejo de excepciones, si es necesario
            ex.printStackTrace();
            return false;
        }
    }

    
}
