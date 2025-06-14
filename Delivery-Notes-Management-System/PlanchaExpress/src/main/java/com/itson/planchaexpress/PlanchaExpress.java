/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.itson.planchaexpress;

import com.itson.dominio.Usuario;
import com.itson.presentacion.FrmPrincipal;
import negocio.ILogica;
import negocio.LogicaNegocio;

/**
 *
 * @author alexasoto
 */
public class PlanchaExpress {

    public static void main(String[] args) {
        ILogica logica=new LogicaNegocio();
        logica.registrarUsuario(new Usuario("admin", "admin"));
        FrmPrincipal principal = new FrmPrincipal();
        principal.setVisible(true);
    }
}
