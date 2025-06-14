/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.itson.interfaces;

import com.itson.dominio.Servicio;

/**
 *
 * @author alexasoto
 */
public interface IServiciosDAO {
    
    public boolean insertarServicio(Servicio servicio);
    public boolean eliminarServicio(Long id);
    public Servicio buscarServicio(Long id);
    public boolean actualizarServicio(Servicio servicio);
    
    
}
