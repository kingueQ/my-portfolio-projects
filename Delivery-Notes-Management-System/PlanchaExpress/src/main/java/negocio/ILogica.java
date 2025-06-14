/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import com.itson.dominio.Cliente;
import com.itson.dominio.NotaRemision;
import com.itson.dominio.NotaServicio;
import com.itson.dominio.Servicio;
import com.itson.dominio.Usuario;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author kingu
 */
public interface ILogica {
    public boolean registrarCliente(Cliente cliente);
    public boolean registrarUsuario(Usuario usuario);
    public boolean registrarServicio(Servicio servicio);
    public boolean crearNotaRemision(NotaRemision notaRemision);
    public boolean eliminarCliente(Long id);
    public boolean eliminarUsuario(Long id);
    public boolean eliminarServicio(Long id);
    public boolean cancelarNotaRemision(Long id);
    public boolean realizarEntrega(Long id, Date fecha_entregada);
    public String buscarNotasCliente(Cliente cliente);
    public NotaRemision buscarNota(Long folio);
    public Cliente buscarCliente(Long folio);
    public Servicio buscarServicio(Long folio);
    public Usuario buscarUsuario(Long id);
    public List<Servicio> recuperarServicios();
    public List<Cliente> recuperarClientes();
    public List<NotaRemision> recuperarnotas();
    public List<Usuario> recuperarUsuarios();
    public Long autenticarUsuario(String nombre, String pass);
    public boolean insertarNotaServicio(NotaServicio notaServicio);
    public boolean actualizarNotaRemision(NotaRemision nota);
    public boolean actualizarServicio(Servicio servicio);
    public boolean actualizaCliente(Cliente cliente);
    public boolean actualizaUsuario(Usuario usuario);
    public boolean solicitarPass(JFrame frm);
    
}
