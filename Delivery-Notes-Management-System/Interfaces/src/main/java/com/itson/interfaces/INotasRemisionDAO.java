/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.itson.interfaces;

import java.sql.Date;

/**
 *
 * @author alexasoto
 */
public interface INotasRemisionDAO {
    
    public void insertar(Date fecha_recepcion, Date fecha_entrega, float total);
    
}
