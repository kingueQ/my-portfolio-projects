/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.itson.interfaces;

import com.itson.dominio.Cliente;
import java.util.List;

/**
 *
 * @author alexasoto
 */
public interface IClientesDAO {
    
    public boolean insertarCliente(Cliente cliente);
    public Cliente consultaCliente(Long id);
    public List<Cliente> consultarLista();
    public boolean editarCliente(Cliente cliente);
    public boolean eliminarCliente(Long id);
}
