package ge.softgen.softlab.springboottutorial.service;

import ge.softgen.softlab.springboottutorial.entity.Customer;
import ge.softgen.softlab.springboottutorial.exception.InvalidParameterException;
import ge.softgen.softlab.springboottutorial.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImplement implements CustomerService {
    private static int id = 1;
    private List<Customer> db = new ArrayList<>(); // database

    public List<Customer> getAll() {
        return db;
    }

    public List<Customer> getAllNotDeleted() {
        return db.stream().filter(customer -> !customer.getDeleted()).toList();
    }

    public Customer getCustomerByID(int id) {
        return getCustomer(id);
    }

    public void addCustomer(Customer customer) {
        customer.setId(id++);
        customer.setDeleted(false);
        db.add(customer);
    }

    public Customer updateCustomer(Customer customer, int id) {
        // @RequestBody Customer customer   Postman Object
        var foundedCustomer = getCustomer(id);
        foundedCustomer.setFirstName(customer.getFirstName());
        foundedCustomer.setLastName(customer.getLastName());
        foundedCustomer.setBirthDate(customer.getBirthDate());
        return foundedCustomer; // same as changedCustomer
    }

    public void deleteCustomer(int id) {
        var foundedCustomer = getCustomer(id);
        foundedCustomer.setDeleted(true); // same as deletedCustomer
    }

    public Customer getCustomer(int id) {
        if (id < 1 || id > getAll().size()) {
            throw new InvalidParameterException("Id must be more than 0 and less," +
                    " than maximum member's index from database");
        }
        var optional = db.stream().filter(customer -> customer.getId() == id).findFirst();
        if (optional.isEmpty()) {
            throw new NotFoundException("Customer not found");
        }
        return optional.get();
    }
}
