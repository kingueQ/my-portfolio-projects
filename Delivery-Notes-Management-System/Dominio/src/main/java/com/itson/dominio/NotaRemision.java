/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.dominio;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author alexasoto
 */
@Entity
@Table(name = "NotasRemision")
public class NotaRemision implements Serializable {

    @Id
    @Column(name = "folio", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long folio;
    
    @Column(name = "fecha_recepcion", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecha_recepcion;
    
    @Column(name = "fecha_entrega", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecha_entrega;
    
    @Column(name = "total", nullable = false)
    private float total;
    
    
    public Long getFolio() {
        return folio;
    }

    public void setFolio(Long folio) {
        this.folio = folio;
    }

    public Date getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(Date fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    public Date getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(Date fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.folio);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NotaRemision other = (NotaRemision) obj;
        return Objects.equals(this.folio, other.folio);
    }

    @Override
    public String toString() {
        return "NotaRemision{" + "folio=" + folio + ", fecha_recepcion=" + fecha_recepcion + ", fecha_entrega=" + fecha_entrega + ", total=" + total + '}';
    }


}
