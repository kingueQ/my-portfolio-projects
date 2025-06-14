/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.itson.interfaces;

import com.itson.dominio.Cliente;

/**
 *
 * @author alexasoto
 */
public interface IClientesDAO {
    
    public boolean insertarCliente(Cliente cliente);
    public boolean actualizarCliente(Cliente cliente);
    public boolean eliminarCliente(Long id);
    public Cliente buscarCliente(Long id);
}
