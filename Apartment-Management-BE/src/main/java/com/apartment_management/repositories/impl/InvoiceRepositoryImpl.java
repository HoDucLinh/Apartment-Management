/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apartment_management.repositories.impl;

import com.apartment_management.pojo.Invoice;
import com.apartment_management.repositories.InvoiceRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ADMIN
 */
@Repository
@Transactional
public class InvoiceRepositoryImpl implements InvoiceRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Invoice> getInvoicesByUserId(int userId) {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query<Invoice> query = session.createQuery(
                "FROM Invoice i LEFT JOIN FETCH i.detailInvoiceSet d LEFT JOIN FETCH d.feedId WHERE i.userId.id = :userId",
                Invoice.class
        );
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public Invoice getInvoiceById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(Invoice.class, id);
    }

}
