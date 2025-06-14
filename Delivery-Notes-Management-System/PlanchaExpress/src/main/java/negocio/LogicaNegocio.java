/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import com.itson.dao.ClientesDAO;
import com.itson.dao.NotasRemisionDAO;
import com.itson.dao.ServiciosDAO;
import com.itson.dao.UsuariosDAO;
import com.itson.dominio.*;
import com.itson.interfaces.IClientesDAO;
import com.itson.interfaces.INotasRemisionDAO;
import com.itson.interfaces.IServiciosDAO;
import com.itson.interfaces.IUsuariosDAO;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author kingu
 */
public class LogicaNegocio implements ILogica{

    IClientesDAO clientes=new ClientesDAO();
    IUsuariosDAO usuarios=new UsuariosDAO();
    IServiciosDAO servicios=new ServiciosDAO();
    INotasRemisionDAO notas=new NotasRemisionDAO();
    
    @Override
    public boolean registrarCliente(Cliente cliente) {
        return this.clientes.insertarCliente(cliente);
    }

    @Override
    public boolean registrarUsuario(Usuario usuario) {
        return this.usuarios.insertarUsuario(usuario);
    }

    @Override
    public boolean registrarServicio(Servicio servicio) {
        return this.servicios.insertarServicio(servicio.getDescripcion(), servicio.getPrecio());
    }

    @Override
    public boolean crearNotaRemision(NotaRemision notaRemision) {
        return this.notas.insertarNota(notaRemision.getUsuario(), notaRemision.getCliente(), notaRemision.getNotaServicios(), notaRemision.getTotal(), notaRemision.getFecha_recepcion(), notaRemision.getFecha_entrega(),notaRemision.getEstado(), notaRemision.getAnticipo());
    }

    @Override
    public boolean eliminarCliente(Long id) {
        return this.clientes.eliminarCliente(id);
    }

    @Override
    public boolean eliminarUsuario(Long id) {
        return this.usuarios.eliminarUsuario(id);
    }

    @Override
    public boolean eliminarServicio(Long id) {
        return this.servicios.eliminarServicio(id);
    }

    @Override
    public boolean cancelarNotaRemision(Long id) {
        return this.notas.cancelarNota(id);
    }
    
    @Override
    public boolean realizarEntrega(Long id, Date fecha_entregada) {
        return this.notas.realizarEntrega(id, fecha_entregada);
    }

    @Override
    public String buscarNotasCliente(Cliente cliente) {
        return this.notas.buscarNotasCliente(cliente);
    }

    @Override
    public NotaRemision buscarNota(Long folio) {
        return this.notas.buscarNota(folio);
    }

    @Override
    public List<Servicio> recuperarServicios() {
        return this.servicios.buscarServicios();
    }

    @Override
    public List<Cliente> recuperarClientes() {
        return this.clientes.consultarLista();
    }

    @Override
    public Long autenticarUsuario(String nombre, String pass) {
        return this.usuarios.autenticarUsuario(nombre, pass);
    }
    
    @Override
    public Usuario buscarUsuario(Long id){
        return this.usuarios.consultaUsuario(id);
    }

    @Override
    public boolean insertarNotaServicio(NotaServicio notaServicio) {
        return this.notas.insertarNotaServicio(notaServicio);
    }

    @Override
    public boolean actualizarNotaRemision(NotaRemision nota) {
        return this.notas.actualizarNotaRemision(nota);
    }

    @Override
    public List<NotaRemision> recuperarnotas() {
        return this.notas.consultarLista();
    }

    @Override
    public Servicio buscarServicio(Long folio) {
        return this.servicios.consultaServicio(folio);
    }

    @Override
    public boolean actualizarServicio(Servicio servicio) {
       return this.servicios.editarServicio(servicio);
    }

    @Override
    public Cliente buscarCliente(Long folio) {
        return this.clientes.consultaCliente(folio);
    }

    @Override
    public boolean actualizaCliente(Cliente cliente) {
        return this.clientes.editarCliente(cliente);
    }

    @Override
    public boolean actualizaUsuario(Usuario usuario) {
        return this.usuarios.editaUsuario(usuario);
    }

    @Override
    public List<Usuario> recuperarUsuarios() {
        return this.usuarios.buscarUsuarios();
    }

    @Override
    public boolean solicitarPass(JFrame frm) {
        return this.usuarios.solicitarContrasenaAdmin(frm);
    }

    
}
