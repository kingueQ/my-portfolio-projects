package com.itson.dominio;

import com.itson.dominio.Cliente;
import com.itson.dominio.NotaServicio;
import com.itson.dominio.Usuario;
import enumeradores.Estado;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-09-01T11:02:48")
@StaticMetamodel(NotaRemision.class)
public class NotaRemision_ { 

    public static volatile SingularAttribute<NotaRemision, Float> anticipo;
    public static volatile SingularAttribute<NotaRemision, Cliente> cliente;
    public static volatile SingularAttribute<NotaRemision, Float> total;
    public static volatile SingularAttribute<NotaRemision, Estado> estado;
    public static volatile SingularAttribute<NotaRemision, Date> fecha_entrega;
    public static volatile SingularAttribute<NotaRemision, Long> folio;
    public static volatile ListAttribute<NotaRemision, NotaServicio> notaServicios;
    public static volatile SingularAttribute<NotaRemision, Date> fecha_recepcion;
    public static volatile SingularAttribute<NotaRemision, Usuario> usuario;
    public static volatile SingularAttribute<NotaRemision, Date> fecha_entregada;

}