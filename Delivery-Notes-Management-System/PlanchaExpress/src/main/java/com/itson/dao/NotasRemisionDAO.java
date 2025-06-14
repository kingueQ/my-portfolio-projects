/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.dao;

import com.itson.dominio.Cliente;
import com.itson.dominio.NotaRemision;
import com.itson.dominio.NotaServicio;
import com.itson.dominio.Servicio;
import com.itson.dominio.Usuario;
import com.itson.interfaces.INotasRemisionDAO;
import enumeradores.Estado;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;

/**
 *
 * @author alexasoto
 */
public class NotasRemisionDAO implements INotasRemisionDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson.planchaexpress");
    EntityManager em = emf.createEntityManager();


    @Override
    public boolean eliminarNota(Long folio) {
        NotaRemision notaRemision = em.find(NotaRemision.class, folio);

        if (notaRemision != null) {

            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            try {

                em.remove(notaRemision);

                transaction.commit();

                System.out.println("Registro eliminado exitosamente.");
                return true;
            } catch (Exception e) {

                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("No se encontró ningún registro con el ID proporcionado.");

        }

        return false;
    }

    @Override
    public NotaRemision buscarNota(Long folio) {
        try {
            em.getTransaction().begin();
            NotaRemision nota = em.find(NotaRemision.class, folio);
            if (nota == null) {
                System.out.println("No se encontró la nota");
                return null;
            } else {
                System.out.println("Detalles de la nota: ");
                System.out.println("Folio: " + nota.getFolio());
                System.out.println("Estado: " + nota.getEstado());
                System.out.println("Fecha entrega: " + nota.getFecha_entrega());
                System.out.println("Fecha recepcion: " + nota.getFecha_recepcion());
                List<NotaServicio> servicios = nota.getNotaServicios();
                for (NotaServicio servicioI : servicios) {
                    System.out.println(servicioI.getServicio().getDescripcion());
                }
                System.out.println("Servicios: " + servicios);
                System.out.println("Total: " + nota.getTotal());
                System.out.println("Usuario: " + nota.getUsuario());

                return nota;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al buscar la nota.");
            return null;
        } finally {
            em.getTransaction().commit();
        }

    }

    @Override
    public String buscarNotasCliente(Cliente cliente) {
        try {
            String consulta = "SELECT n FROM NotaRemision n WHERE n.cliente = :cliente";
            List<NotaRemision> notas = em.createQuery(consulta, NotaRemision.class)
                    .setParameter("cliente", cliente)
                    .getResultList();

            StringBuilder resultado = new StringBuilder();
            resultado.append("Notas de remisión para el cliente ").append(cliente.getNombre()).append(":\n");
            for (NotaRemision nota : notas) {
                resultado.append("Folio: ").append(nota.getFolio()).append("\n");
                resultado.append("Fecha de Recepción: ").append(nota.getFecha_recepcion()).append("\n");
                resultado.append("Fecha de Entrega: ").append(nota.getFecha_entrega()).append("\n");
                resultado.append("Total: ").append(nota.getTotal()).append("\n");
                resultado.append("Estado: ").append(nota.getEstado()).append("\n");
                resultado.append("--------------------------------------\n");
            }
            return resultado.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al buscar las notas de remisión para el cliente.";
        }
    }

    @Override
    public boolean insertarNota(Usuario usuario, Cliente cliente, List<NotaServicio> servicios, float total, Date fecha_recepcion, Date fecha_entrega, Estado estado, float anticipo) throws PersistenceException {
        try {
            em.getTransaction().begin();
            NotaRemision nota = new NotaRemision(usuario, cliente, total, fecha_recepcion, fecha_entrega, estado);
            nota.setAnticipo(anticipo);
            for(int i=0;i<servicios.size();i++){
                servicios.get(i).setNota(nota);
            }
            nota.setNotaServicios(servicios);
            em.persist(nota);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "La nota con folio: " + nota.getFolio() + " se insertó correctamente");
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar la nota");
//            em.getTransaction().rollback();
            return false;
        }
    }
    
    @Override
    public Long insertarNota(Usuario usuario, Cliente cliente, float total, Date fecha_recepcion, Date fecha_entrega, Estado estado, float anticipo) throws PersistenceException {
    Long folio = null;
    try {
        em.getTransaction().begin();
        NotaRemision nota = new NotaRemision(usuario, cliente, total, fecha_recepcion, fecha_entrega, estado);
        nota.setAnticipo(anticipo);
        em.persist(nota);
        em.getTransaction().commit();
        folio = nota.getFolio(); // Obtenemos el folio después de la inserción
        JOptionPane.showMessageDialog(null, "La nota con folio: " + folio + " se insertó correctamente");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error al insertar la nota");
        // Si ocurre una excepción, no se asigna ningún folio
        em.getTransaction().rollback();
    }
    return folio;
}

    @Override
    public boolean cancelarNota(Long folio) {
        NotaRemision notaRemision = em.find(NotaRemision.class, folio);

        if (notaRemision != null) {

            if (!Objects.equals(notaRemision.getEstado(), Estado.CANCELADA)
                    && !Objects.equals(notaRemision.getEstado(), Estado.ENTREGADA)) {

                EntityTransaction transaction = em.getTransaction();
                transaction.begin();

                try {

                    notaRemision.setEstado(Estado.CANCELADA);
                    transaction.commit();
                    return true;
                } catch (Exception e) {
                    if (transaction != null && transaction.isActive()) {
                        transaction.rollback();
                    }
                    e.printStackTrace();
                }
            } else {
                System.out.println("La nota no puede ser cancelada en su estado actual.");
            }
        } else {
            System.out.println("No se encontró la nota con el folio proporcionado.");
        }
        return false;
    }
    
    @Override
    public boolean realizarEntrega(Long folio, Date fecha_entregada) {
        NotaRemision notaRemision = em.find(NotaRemision.class, folio);

        if (notaRemision != null) {

            if (Objects.equals(notaRemision.getEstado(), Estado.ENTREGADA)) {
                 JOptionPane.showMessageDialog(null, "La nota ya fue entregada anteriormente");
                return false;
            }

            if (Objects.equals(notaRemision.getEstado(), Estado.CANCELADA)) {
                JOptionPane.showMessageDialog(null,"La nota no puede ser entregada en su estado actual.");
                return false;
            }

            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            try {

                notaRemision.setFecha_entregada(fecha_entregada);
                notaRemision.setEstado(Estado.ENTREGADA);
                transaction.commit();
                return true;
            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null,"No se encontró la nota con el folio proporcionado.");
        }
        return false;
    }

    @Override
    public void editarNota(Long folio, Usuario usuario, Cliente cliente, List<NotaServicio> servicios, float total, Date fecha_recepcion, Date fecha_entrega, Estado estado) {
        em.getTransaction().begin();
        NotaRemision nota = em.find(NotaRemision.class, folio);
        if (nota == null) {
            System.out.println("No se encontró la nota");
        } else {

            System.out.println("Nota a editar: ");
            System.out.println("Folio: " + nota.getFolio());
            System.out.println("Estado: " + nota.getEstado());
            System.out.println("Fecha entrega: " + nota.getFecha_entrega());
            System.out.println("Fecha recepcion: " + nota.getFecha_recepcion());
            System.out.println("---------------------------");
            System.out.println("Servicios: ");
            for (NotaServicio servicioI : servicios) {
                System.out.println(servicioI.getServicio().toString());
            }
            System.out.println("---------------------------");
            System.out.println("Total: " + nota.getTotal());
            System.out.println("Usuario: " + nota.getUsuario());

            nota.setFecha_entrega(fecha_entrega);
            nota.setFecha_recepcion(fecha_recepcion);
            nota.setUsuario(usuario);
            nota.setTotal(total);
            nota.setCliente(cliente);
            nota.getNotaServicios().clear();
            nota.getNotaServicios().addAll(servicios);
            nota.setEstado(estado);

            em.merge(nota);
        }
        em.getTransaction().commit();
    }
    
    @Override
    public boolean insertarNotaServicio(NotaServicio nota){
        try {
            em.getTransaction().begin();
            em.persist(nota);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Se insertó la referencia");
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar la referencia");
//            em.getTransaction().rollback();
            return false;
        }
    }
    
    
    @Override
    public boolean actualizarNotaRemision(NotaRemision nota) {
        try {
            // Realiza las operaciones necesarias para actualizar la nota en la base de datos
            // Por ejemplo:
            em.getTransaction().begin();
            em.merge(nota); // Actualiza la entidad en la base de datos
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            // Manejo de excepciones, si es necesario
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<NotaRemision> consultarLista() {

        try {
            TypedQuery<NotaRemision> query = em.createQuery("SELECT n FROM NotaRemision n", NotaRemision.class);
            return query.getResultList();
        } catch (PersistenceException ex) {
            JOptionPane.showMessageDialog(null, "No hay notas registradas");
            em.getTransaction().rollback();
        }
        return null;
    }
}
