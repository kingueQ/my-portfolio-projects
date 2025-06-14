/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.itson.interfaces;

import com.itson.dominio.Cliente;
import com.itson.dominio.NotaRemision;
import com.itson.dominio.NotaServicio;
import com.itson.dominio.Servicio;
import com.itson.dominio.Usuario;
import enumeradores.Estado;
import java.util.Date;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 *
 * @author alexasoto
 */
public interface INotasRemisionDAO {
    
    public boolean insertarNota(Usuario usuario, Cliente cliente, List<NotaServicio> servicios, 
            float total, Date fecha_recepcion, Date fecha_entrega, Estado estado, float anticipo) throws PersistenceException;
    public boolean eliminarNota(Long folio);
    public void editarNota(Long folio,Usuario usuario, Cliente cliente,List<NotaServicio> servicios, float total, Date fecha_recepcion, Date fecha_entrega, Estado estado);
    public boolean cancelarNota(Long folio);
    public boolean realizarEntrega(Long folio, Date fecha_entregada);
    public NotaRemision buscarNota(Long folio);
    public String buscarNotasCliente(Cliente cliente);
    public boolean insertarNotaServicio(NotaServicio nota);
    public boolean actualizarNotaRemision(NotaRemision nota);
    public Long insertarNota(Usuario usuario, Cliente cliente, float total, Date fecha_recepcion, Date fecha_entrega, Estado estado, float anticipo);
    public List<NotaRemision> consultarLista();
}
