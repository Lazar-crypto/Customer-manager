package com.razal.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.razal.dao.CustomerDAO;
import com.razal.entity.Customer;

@Service //layer between controller and dao if we have more dao objects 
public class CustomerServiceImpl implements CustomerService {

	//inject CustomerDAO
	@Autowired
	private CustomerDAO customerDAO;
	
	@Transactional //service layer defines transactions(starts transaction,gets objects and commits) - Spring AOP(Before and AfterReturning)
	public List<Customer> getCustomers() {
		
		//delegate calls to dao
		return customerDAO.getCustomers();
	}

	@Override
	@Transactional
	public void saveCustomer(Customer customer) {
		
		customerDAO.saveCustomer(customer);
	}

	@Override
	@Transactional
	public Customer getCustomer(int id) {

		return customerDAO.getCustomer(id);
	}

	@Override
	@Transactional
	public void deleteCustomer(int id) {
		
		customerDAO.deleteCustomer(id);
	}

	@Override
	@Transactional
	public List<Customer> searchCustomer(String searchName) {
		
		return customerDAO.searchCustomer(searchName);
	}

}
