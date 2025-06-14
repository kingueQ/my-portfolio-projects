/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.dao;

import com.itson.dominio.Usuario;
import com.itson.interfaces.IUsuariosDAO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.swing.JOptionPane;

/**
 *
 * @author alexasoto
 */
public class UsuariosDAO implements IUsuariosDAO {
    
    private EntityManagerFactory em = null;

    public UsuariosDAO() {
        em = Persistence.createEntityManagerFactory("notas");
    }

    public EntityManager getEntityManager() {
        return em.createEntityManager();
    }

    @Override
    public boolean insertarUsuario(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usuario);
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
    public boolean eliminarUsuario(Long id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Usuario usuario = em.find(Usuario.class, id); 

            if (usuario != null) {
                em.remove(usuario);
                em.getTransaction().commit();
                System.out.println("Usuario eliminado exitosamente");
                return true;
            } else {
                System.out.println("Usuario no encontrado");
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
    public boolean actualizarUsuario(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(usuario);
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

    @Override
    public Usuario buscarUsuario(Long id) {
        EntityManager em = null;
        Usuario usuario = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Usuarios u where u.id = :id");
            query.setParameter("id", id);
            usuario = (Usuario) query.getSingleResult();
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        em.close();
        return usuario;
    }

    @Override
    public boolean autenticarUsuario(String nombre, String pass) {
        EntityManager em = null;
        Usuario usuario = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Usuarios u where u.nombre = :nombre && u.pass = :pass");
            query.setParameter("nombre", nombre);
            query.setParameter("pass", pass);
            usuario = (Usuario) query.getSingleResult();
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        em.close();
        if(usuario!=null){
            return true;
        }else{
            return false;
        }
    }
    
    
    }
    
