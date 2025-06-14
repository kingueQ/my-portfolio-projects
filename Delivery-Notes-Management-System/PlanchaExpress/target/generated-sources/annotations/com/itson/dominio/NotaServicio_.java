package com.itson.dominio;

import com.itson.dominio.NotaRemision;
import com.itson.dominio.Servicio;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-09-01T11:02:48")
@StaticMetamodel(NotaServicio.class)
public class NotaServicio_ { 

    public static volatile SingularAttribute<NotaServicio, Float> precio;
    public static volatile SingularAttribute<NotaServicio, Servicio> servicio;
    public static volatile SingularAttribute<NotaServicio, Float> perdidas;
    public static volatile SingularAttribute<NotaServicio, Integer> cant;
    public static volatile SingularAttribute<NotaServicio, String> detalles;
    public static volatile SingularAttribute<NotaServicio, Long> id;
    public static volatile SingularAttribute<NotaServicio, NotaRemision> nota;

}