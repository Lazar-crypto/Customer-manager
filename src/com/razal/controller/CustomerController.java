package com.razal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.razal.entity.Customer;
import com.razal.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController 
{

	//inject service into controller
	@Autowired // spring scans for Component(Controller,Repository,Service..) that implements (CustController,CustomerDAO,CustomerService..)
	private CustomerService customerService; 
	
	@GetMapping("/list") //only handles get request
	public String listCustomers(Model model) 
	{
		
		//get customers from service layer
		List<Customer> customers = customerService.getCustomers();
		
		//add customers to the model
		model.addAttribute("theCustomers",customers);
		
		return "list-customers";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) 
	{
		Customer customer = new Customer();
		
		model.addAttribute("theCustomer",customer);
		
		return "customer-form";
	}

	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("theCustomer") Customer customer)
	{
		customerService.saveCustomer(customer);
		
		return "redirect:/customer/list";
	}

	@GetMapping("/showFormForUpdate")
	public String updateCustomer(@RequestParam("customerId") int id, Model model)
	{
		// get customer from db(service - > dao - > db)
		Customer customer = customerService.getCustomer(id);
		
		//set customer as a model attribute
		model.addAttribute("theCustomer",customer);
		
		//send model to form
		return "customer-form";
	}

	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int id)
	{
		customerService.deleteCustomer(id);
		
		return "redirect:/customer/list";
	}

	
	@GetMapping("/search")
	public String searchCustomer(@RequestParam("theSearchName") String searchName,Model model)
	{
		
		List<Customer> customers = customerService.searchCustomer(searchName);
		
		model.addAttribute("theCustomers",customers);
		
		return "list-customers";
	}
}
