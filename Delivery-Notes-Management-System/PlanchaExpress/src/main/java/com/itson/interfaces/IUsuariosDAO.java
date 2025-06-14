/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.itson.interfaces;

import com.itson.dominio.Usuario;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author alexasoto
 */
public interface IUsuariosDAO {
    
    public boolean insertarUsuario(Usuario usuario);
    public Usuario consultaUsuario(Long id);
    public boolean eliminarUsuario(Long id);
    public Long autenticarUsuario(String nombre, String pass);
    public List<Usuario> buscarUsuarios();
    public boolean editaUsuario(Usuario usuario);
    public boolean solicitarContrasenaAdmin(JFrame frm);
}
