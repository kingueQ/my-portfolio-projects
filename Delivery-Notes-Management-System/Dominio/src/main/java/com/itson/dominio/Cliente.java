/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.dominio;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author alexasoto
 */
@Entity
@Table(name = "Clientes")
public class Cliente implements Serializable {
    
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "telefono", nullable = false)
    private String telefono;
    
    @Column(name = "direccion", nullable = false)
    private String direccion;
    
    @OneToMany
    (mappedBy = "folio") // Nombre del atributo de la otra clase
    private List<NotaRemision> notas;


//    /**
//     * Rfc de la persona dueña del vehículo
//     */
//    @ManyToOne
//    @JoinColumn(name = "rfc", referencedColumnName = "rfc", nullable = false)
//    private Persona persona;
//
//    /**
//     * Placas que ha tenido un vehículo
//     */
//    @OneToMany(mappedBy = "vehiculo") // Nombre del atributo de la otra clase
//    private List<Placa> placas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itson.dominio.Cliente[ id=" + id + " ]";
    }
    
}
