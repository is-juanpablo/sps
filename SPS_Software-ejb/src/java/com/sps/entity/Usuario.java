package com.sps.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Juan Pablo
 */
@Entity
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByPlaca", query = "SELECT u FROM Usuario u WHERE u.placa = :placa")
    , @NamedQuery(name = "Usuario.findByMarca", query = "SELECT u FROM Usuario u WHERE u.marca = :marca")
    , @NamedQuery(name = "Usuario.findByCedula", query = "SELECT u FROM Usuario u WHERE u.idPersona = :idPersona")
    , @NamedQuery(name = "Usuario.findByIdPropiedad", query = "SELECT u FROM Usuario u WHERE u.idPropiedad = :idPropiedad")})

public class Usuario implements Serializable {

    @OneToMany(mappedBy = "idUsuario")
    private Collection<Reporte> reporteCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "PLACA")
    private String placa;
    @Size(max = 15)
    @Column(name = "MARCA")
    private String marca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID_PROPIEDAD")
    private String idPropiedad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO_VEHICULO")
    private Boolean tipoVehiculo;
    @OneToMany(mappedBy = "idUsuario")
    private Collection<Reserva> reservaCollection;
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "CEDULA")
    @ManyToOne(optional = false)
    private Persona idPersona;

    public Usuario() {
    }

    public Usuario(String placa) {
        this.placa = placa;
    }

    public Usuario(Persona persona, String idPropiedad, String placa, String marca, Boolean tipoVehiculo) {
        this.idPersona = persona;
        this.marca = marca;
        this.placa = placa;
        this.idPropiedad = idPropiedad;
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(String idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

    public Boolean getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(Boolean tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    @XmlTransient
    public Collection<Reserva> getReservaCollection() {
        return reservaCollection;
    }

    public void setReservaCollection(Collection<Reserva> reservaCollection) {
        this.reservaCollection = reservaCollection;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (placa != null ? placa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.placa == null && other.placa != null) || (this.placa != null && !this.placa.equals(other.placa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "{\"id\":\"" + placa + "\", \"marca\":\"" + marca + "\", \"perfil\":\"usuario\", \"tipo\":\"" + tipoVehiculo + "\"}";
    }

    @XmlTransient
    public Collection<Reporte> getReporteCollection() {
        return reporteCollection;
    }

    public void setReporteCollection(Collection<Reporte> reporteCollection) {
        this.reporteCollection = reporteCollection;
    }

}
