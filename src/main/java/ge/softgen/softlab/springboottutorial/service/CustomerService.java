package ge.softgen.softlab.springboottutorial.service;

import ge.softgen.softlab.springboottutorial.entity.Customer;
import ge.softgen.softlab.springboottutorial.entity.CustomerSearchParams;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll(CustomerSearchParams searchParams);
    Customer getCustomerByID(int id);
    void addCustomer(Customer customer);
    Customer updateCustomer(Customer customer, int id);
    void deleteCustomer(int id);
    Customer getCustomer(int id);
}
