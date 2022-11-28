package ge.softgen.softlab.springboottutorial.service;

import ge.softgen.softlab.springboottutorial.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll();
    List<Customer> getAllNotDeleted();
    Customer getCustomerByID(int id);
    void addCustomer(Customer customer);
    Customer updateCustomer(Customer customer, int id);
    void deleteCustomer(int id);
    Customer getCustomer(int id);
}
