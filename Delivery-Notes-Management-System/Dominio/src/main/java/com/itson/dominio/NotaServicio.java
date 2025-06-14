/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.dominio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author alexasoto
 */
@Entity
@Table(name = "Nota_Servicio")
public class NotaServicio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "servicio_id")
    Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "notaremision_folio")
    NotaRemision nota;
    
    @Column(name = "cant", nullable = false)
    private int cant;
    
    @Column(name = "detalles", nullable = false)
    private String detalles;
    
    @Column(name = "precio", nullable = false)
    private float precio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public NotaRemision getNota() {
        return nota;
    }

    public void setNota(NotaRemision nota) {
        this.nota = nota;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotaServicio)) {
            return false;
        }
        NotaServicio other = (NotaServicio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "NotaServicio{" + "id=" + id + ", servicio=" + servicio + ", nota=" + nota + '}';
    }

}
