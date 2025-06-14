/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.dao;

import com.itson.dominio.Usuario;
import com.itson.interfaces.IUsuariosDAO;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 *
 * @author alexasoto
 */
public class UsuariosDAO implements IUsuariosDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson.planchaexpress");
    EntityManager em = emf.createEntityManager();

    public UsuariosDAO() {
    }

    @Override
    public boolean insertarUsuario(Usuario usuario) {
        try {
            em.getTransaction().begin();

            em.persist(usuario);

            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Se han insertado el usuario con éxito");
            return true;
        } catch (PersistenceException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar");
            em.getTransaction().rollback();
            return false;
        }

    }

    @Override
    public boolean eliminarUsuario(Long id) {
        try {
            em.getTransaction().begin();

            Usuario usuario = em.find(Usuario.class, id);

            if (usuario != null) {
                em.remove(usuario);
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
    public Long autenticarUsuario(String nombre, String pass) {
        try {
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.nombre = :nombre AND u.pass = :pass", Usuario.class);
            query.setParameter("nombre", nombre);
            query.setParameter("pass", pass);
            Usuario usuario = query.getSingleResult();
            if(usuario!=null){
                return usuario.getId();
            }else{
                return -1L;
            }
        } catch (javax.persistence.NoResultException ex) {
            // Maneja el caso en el que no se encuentra ningún usuario administrador
            return -1L;
        }
    }

    @Override
    public List<Usuario> buscarUsuarios() {
        try {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
            return query.getResultList();
        } catch (PersistenceException ex) {
            JOptionPane.showMessageDialog(null, "No hay usuarios registrados");
            em.getTransaction().rollback();
        }
        return null;
    }

    @Override
    public Usuario consultaUsuario(Long id) {
        try {
            //Busca el id en la clase Cliente
            return em.find(Usuario.class, id);
        } catch (PersistenceException ex) {
            JOptionPane.showMessageDialog(null, "Error al consultar al usuario");
            return null;
        }
    }

    @Override
    public boolean editaUsuario(Usuario usuario) {
        em.getTransaction().begin();
        em.merge(usuario); // Actualiza la entidad en la base de datos
        JOptionPane.showMessageDialog(null, "Usuario actualizado");
        em.getTransaction().commit();
        return true;
    }

    // Método para solicitar la contraseña al usuario
    @Override
    public boolean solicitarContrasenaAdmin(JFrame frm) {
        JPasswordField contrasenaField = new JPasswordField();
        Object[] message = {
            "Ingrese la contraseña de administrador:", contrasenaField
        };
        int option = JOptionPane.showConfirmDialog(frm, message, "Autenticación", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            char[] contrasenaIngresada = contrasenaField.getPassword();
            String contrasenaAdminRegistrada = obtenerContrasenaAdmin(); // Método ficticio para obtener la contraseña del administrador registrado
            return Arrays.equals(contrasenaIngresada, contrasenaAdminRegistrada.toCharArray());
        } else {
            return false; // Si el usuario cancela, retorna falso
        }
    }

    private String obtenerContrasenaAdmin() {
        Usuario admin = this.obtenerUsuarioAdmin();
        return admin.getPass();
    }

    public Usuario obtenerUsuarioAdmin() {
        try {
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.nombre = :nombre", Usuario.class);
            query.setParameter("nombre", "admin");
            return query.getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            // Maneja el caso en el que no se encuentra ningún usuario administrador
            return null;
        }
    }

}
