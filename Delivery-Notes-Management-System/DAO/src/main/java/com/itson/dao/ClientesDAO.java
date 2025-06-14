/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.dao;

import com.itson.dominio.Cliente;
import com.itson.interfaces.IClientesDAO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author alexasoto
 */
public class ClientesDAO implements IClientesDAO {

    private EntityManagerFactory em = null;

    public ClientesDAO() {
        em = Persistence.createEntityManagerFactory("notas");
    }

    public EntityManager getEntityManager() {
        return em.createEntityManager();
    }

    @Override
    public boolean insertarCliente(Cliente cliente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cliente);
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
    public Cliente buscarCliente(Long id) {
        EntityManager em = null;
        Cliente cliente = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT c FROM Clientes c where c.id = :id");
            query.setParameter("id", id);
            cliente = (Cliente) query.getSingleResult();
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        em.close();
        return cliente;
    }

    @Override
    public boolean eliminarCliente(Long id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Cliente cliente = em.find(Cliente.class, id); 

            if (cliente != null) {
                em.remove(cliente);
                em.getTransaction().commit();
                System.out.println("Cliente eliminado exitosamente");
                return true;
            } else {
                System.out.println("Cliente no encontrado");
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Error al eliminar cliente: " + ex.getMessage());
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
    public boolean actualizarCliente(Cliente cliente){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(cliente);
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
