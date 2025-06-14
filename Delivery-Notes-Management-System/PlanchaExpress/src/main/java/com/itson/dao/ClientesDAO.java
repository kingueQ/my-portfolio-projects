/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.dao;

import com.itson.dominio.Cliente;
import com.itson.interfaces.IClientesDAO;
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
public class ClientesDAO implements IClientesDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson.planchaexpress");
    EntityManager em = emf.createEntityManager();

    public ClientesDAO() {
    }

    @Override
    public boolean insertarCliente(Cliente cliente) {
        try {
            em.getTransaction().begin();

            Cliente cliente1 = new Cliente(cliente.getNombre(), cliente.getDireccion(), cliente.getTelefono());
            
            em.persist(cliente1);

            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Se ha insertado el cliente " + cliente.getNombre() + " con éxito");
            return true;
        } catch (PersistenceException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar");
            em.getTransaction().rollback();
            return false;
        }
    }
    
    @Override
    public Cliente consultaCliente(Long id) {
        try {
            //Busca el id en la clase Cliente
            return em.find(Cliente.class, id);
        } catch (PersistenceException ex) {
            JOptionPane.showMessageDialog(null, "Error al consultar al cliente");
            return null;
        }
    }
    
    @Override
    public List<Cliente> consultarLista() {
        try {
            TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
        return query.getResultList();
        } catch (PersistenceException ex) {
            JOptionPane.showMessageDialog(null, "No hay clientes registrados");
            em.getTransaction().rollback();
        }
        return null;
    }

    @Override
    public boolean eliminarCliente(Long id) {
        try {
            em.getTransaction().begin();

            Cliente cliente = em.find(Cliente.class, id);

            if (cliente != null) {
                em.remove(cliente);
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
    public boolean editarCliente(Cliente cliente) {
        try {
            em.getTransaction().begin();
            em.merge(cliente); // Actualiza la entidad en la base de datos
            JOptionPane.showMessageDialog(null, "Cliente actualizado");
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            // Manejo de excepciones, si es necesario
            ex.printStackTrace();
            return false;
        }
    }
    

}
