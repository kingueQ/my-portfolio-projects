/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itson.dao;

import com.itson.dominio.Usuario;

/**
 *
 * @author kingu
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Prueba de conexion
        Usuario usuario=new Usuario();
        usuario.setNombre("Abraham");
        usuario.setPass("Gatito");
        DAO dao = new DAO();
        boolean res=dao.insertarUsuario(usuario);
        if(res){
            System.out.println("insercion exitosa");
        }else{
            System.out.println("error en insercion");
        }
    }
    
}
