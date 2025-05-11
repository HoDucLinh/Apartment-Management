/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apartment_management.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "floor")
@NamedQueries({
    @NamedQuery(name = "Floor.findAll", query = "SELECT f FROM Floor f"),
    @NamedQuery(name = "Floor.findById", query = "SELECT f FROM Floor f WHERE f.id = :id"),
    @NamedQuery(name = "Floor.findByFloorNumber", query = "SELECT f FROM Floor f WHERE f.floorNumber = :floorNumber")})
public class Floor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "floor_number")
    private int floorNumber;
    @OneToMany(mappedBy = "floorId")
    @JsonIgnore
    private Set<Room> roomSet;

    public Floor() {
    }

    public Floor(Integer id) {
        this.id = id;
    }

    public Floor(Integer id, int floorNumber) {
        this.id = id;
        this.floorNumber = floorNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Set<Room> getRoomSet() {
        return roomSet;
    }

    public void setRoomSet(Set<Room> roomSet) {
        this.roomSet = roomSet;
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
        if (!(object instanceof Floor)) {
            return false;
        }
        Floor other = (Floor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apartment_management.pojo.Floor[ id=" + id + " ]";
    }
    
}
