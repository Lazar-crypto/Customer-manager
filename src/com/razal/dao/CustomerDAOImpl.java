package com.razal.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.razal.entity.Customer;

@Repository //for data access object implementations
public class CustomerDAOImpl implements CustomerDAO {

	//need to inject session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
//	@Transactional //spring manages starting and stoping tranasctions to db
	public List<Customer> getCustomers() {

		//get current session
		Session session = sessionFactory.getCurrentSession();
		
		//create query .. sort by last name
		Query<Customer> query = session.createQuery("from Customer order by lastName",Customer.class);
		
		//getResultList
		List<Customer> customers = query.getResultList();
		
		//return customers
		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {
		
		Session session = sessionFactory.getCurrentSession();
		
		//if id of the customer is empy insert else update
		session.saveOrUpdate(customer);
	}

	@Override
	public Customer getCustomer(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Customer customer = session.get(Customer.class, id);
		
		return customer;
	}

	@Override
	public void deleteCustomer(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
//		Customer customer = session.get(Customer.class, id);
//		
//		session.delete(customer);

		Query query = session.createQuery("delete from Customer where id =: customerId");
		query.setParameter("customerId", id);
		
		query.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomer(String searchName) {

		Session session = sessionFactory.getCurrentSession();
				
		Query<Customer>query = null;
		
		//if searchName not null and trim.length() > 0 find by given name
		if(searchName != null  && searchName.trim().length() > 0) {
			
			query = session.createQuery("from Customer where lower(firstName) like : theFirstName or lower(lastName) like : theLastName",Customer.class);
			query.setParameter("theFirstName", "%" + searchName.toLowerCase() + "%");
			query.setParameter("theLastName", "%" + searchName.toLowerCase() + "%");
		}
		else {
			query = session.createQuery("from Customer",Customer.class);
		}
		
		List<Customer> customers = query.getResultList();
		
		return customers;
		
	}

}
