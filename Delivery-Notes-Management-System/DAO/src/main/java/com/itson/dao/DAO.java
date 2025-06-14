/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.itson.dao;

import com.itson.dominio.Cliente;
import com.itson.dominio.Servicio;
import com.itson.dominio.Usuario;
import com.itson.interfaces.IClientesDAO;
import com.itson.interfaces.IServiciosDAO;
import com.itson.interfaces.IUsuariosDAO;

/**
 *
 * @author alexasoto
 */
public class DAO {

        IUsuariosDAO usuarios = new UsuariosDAO();
        IClientesDAO clientes = new ClientesDAO();
        IServiciosDAO servicios = new ServiciosDAO();
        
        
        //Métodos de usuarios
        public boolean insertarUsuario(Usuario usuario){
            return this.usuarios.insertarUsuario(usuario);
        }
        
        public boolean eliminarUsuario(Long id){
            return this.usuarios.eliminarUsuario(id);
        }
        
        public boolean actualizarUsuario(Usuario usuario){
            return this.usuarios.actualizarUsuario(usuario);
        }
        
        public Usuario buscarUsuario(Long id){
            return this.usuarios.buscarUsuario(id);
        }
        
        public boolean autenticarUsuario(String nombre, String pass){
            return this.usuarios.autenticarUsuario(nombre, pass);
        }
        
        //Métodos de clientes
        public boolean insertarCliente(Cliente cliente){
            return this.clientes.insertarCliente(cliente);
        }
        
        public boolean eliminarCliente(Long id){
            return this.clientes.eliminarCliente(id);
        }
        
        public boolean actualizarCliente(Cliente cliente){
            return this.clientes.actualizarCliente(cliente);
        }
        
        public Cliente buscarCliente(Long id){
            return this.clientes.buscarCliente(id);
        }
        
        //Métodos de servicios
        public boolean insertarServicio(Servicio servicio){
            return this.servicios.insertarServicio(servicio);
        }
        
        public boolean eliminarServicio(Long id){
            return this.servicios.eliminarServicio(id);
        }
        
        public boolean actualizarServicio(Servicio servicio){
            return this.servicios.actualizarServicio(servicio);
        }
        
        public Servicio buscarServicio(Long id){
            return this.servicios.buscarServicio(id);
        }
    
}
