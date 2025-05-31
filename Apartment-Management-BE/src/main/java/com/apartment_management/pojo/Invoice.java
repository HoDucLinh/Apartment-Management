/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apartment_management.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "invoice")
@NamedQueries({
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i"),
    @NamedQuery(name = "Invoice.findById", query = "SELECT i FROM Invoice i WHERE i.id = :id"),
    @NamedQuery(name = "Invoice.findByIssuedDate", query = "SELECT i FROM Invoice i WHERE i.issuedDate = :issuedDate"),
    @NamedQuery(name = "Invoice.findByDueDate", query = "SELECT i FROM Invoice i WHERE i.dueDate = :dueDate"),
    @NamedQuery(name = "Invoice.findByPaymentMethod", query = "SELECT i FROM Invoice i WHERE i.paymentMethod = :paymentMethod"),
    @NamedQuery(name = "Invoice.findByTotalAmount", query = "SELECT i FROM Invoice i WHERE i.totalAmount = :totalAmount"),
    @NamedQuery(name = "Invoice.findByStatus", query = "SELECT i FROM Invoice i WHERE i.status = :status")})
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "issued_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date issuedDate;
    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;
    @Size(max = 8)
    @Column(name = "payment_method")
    private String paymentMethod;
    @Lob
    @Size(max = 65535)
    @Column(name = "payment_proof")
    private String paymentProof;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Size(max = 6)
    @Column(name = "status")
    private String status;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoiceId", fetch = FetchType.EAGER)
    private Set<DetailInvoice> detailInvoiceSet;
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    @ManyToOne(cascade = CascadeType.DETACH)
    private User userId;

    public Invoice() {
    }

    public Invoice(Integer id) {
        this.id = id;
    }

    public Invoice(Integer id, Date issuedDate, BigDecimal totalAmount) {
        this.id = id;
        this.issuedDate = issuedDate;
        this.totalAmount = totalAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentProof() {
        return paymentProof;
    }

    public void setPaymentProof(String paymentProof) {
        this.paymentProof = paymentProof;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<DetailInvoice> getDetailInvoiceSet() {
        return detailInvoiceSet;
    }

    public void setDetailInvoiceSet(Set<DetailInvoice> detailInvoiceSet) {
        this.detailInvoiceSet = detailInvoiceSet;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.apartment_management.pojo.Invoice[ id=" + id + " ]";
    }
    
}
