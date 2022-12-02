package ge.softgen.softlab.springboottutorial.controller;

import ge.softgen.softlab.springboottutorial.entity.Customer;
import ge.softgen.softlab.springboottutorial.entity.CustomerSearchParams;
import ge.softgen.softlab.springboottutorial.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

// @RequestMapping - გვეხმარება საბაზისო ლინკის შენახვაში. ანუ ამას რო გავწერ კოდში აღარ მომიწევს ბაზის წერა
// მაგალითად - @RequestMapping("/customers")
// მაშინ კოდში რომელიმე მოქმედებაზე დავწერდი
//  @RequestMapping("/{id}") და არა @RequestMapping("/customers/{id}")


@RequestMapping("/customers")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping()
    public List<Customer> getAll(CustomerSearchParams searchParams) {
        return customerService.getAll(searchParams);
    }


    /*
    @GetMapping("/")
    public List<Customer> getAll(@RequestParam(required=false) String firstName,
                                 @RequestParam(required=false) String lastName) {
        return customerService.getAll(firstName, lastName);
    }

    ასე თუ დავწერ სერვერზე ჩადებულ მასივს გაფილტრავს სახელისა და გვარის მიხედვით.
    და მხოლოდ მის შესაბამის ობიექტებს დააბრუნებს.

    ასე რომ იმუშავოს კოდმა საჭიროა გადაიწეროს CustomerService ფაილში შესაბამისი ფუნქცია ასე:

    List<Customer> getAll(String firstName, String lastName);

    ასევე უნდა გადაიწეროს CustomerServiceImplement ფაილში შესაბამისი ფუნქცია ასე:

    public List<Customer> getAll(String firstName, String lastName) {
        return db;
    }

    აქვე უნდა გავითვალისწინოთ, რომ CustomerServiceImplement ფაილში საჭიროა ამ მონაცემებით
    გაფილტვრაც. ასე, რომ წინასწარ უნდა გადამოწმდეს სახელი ცალკე და გვარი ცალკე.
    თუ ეს მონაცემი null არ არის და ცვლადი არაა ცარიელი (!variable.isEmpty())
    მხოლოდ მაშინ გაფილტროს. ეს დავამატოთ წაშლილების ფაილთან ერთად:

    public List<Customer> getAll(String firstName, String lastName) {
        // ზოგადად მივწვდი ბაზას
        var stream = db.stream().filter(customer -> customer.getFirstName()!=null);
        if(firstName != null && !firstname.isEmpty()){
            stream = stream().filter(customer -> customer.getFirstName().equals(firstName));
        }
        if(lastName != null && !lastName.isEmpty()){
            stream = db.stream().filter(customer -> customer.getLastName().equals(lastName));
        }
        return stream.toList();
    }


    */

    @GetMapping("/{id}")
    public Customer getById(@PathVariable int id) {
        return customerService.getCustomerByID(id);
    }

    @PostMapping()
    public ResponseEntity<Customer> add(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
//        ეს იგივეა რაც...
//        customer = customerService.addCustomer(customer);
//        კლიენტის გადაცემისას რადგან შიგნით სერვისში მისი ფროფერთები იცვლება გარეთაც შეიცვლება.
//        ამიტომ შეიძლება ასე მოკლედ ჩაიწეროს.


        var location = UriComponentsBuilder.fromPath("/customers/" + customer.getId()).build().toUri();
        return ResponseEntity.created(location).body(customer);
    }

    @PutMapping("/{id}")
    public Customer update(@RequestBody Customer customer, @PathVariable int id) {
        return customerService.updateCustomer(customer, id);
        // 204 წარმატებით დასრულდა და ბრუნდება კლიენტის ობიექტი
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable int id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build(); // 204 წარმატებით დასრულდა და არაფერი ბრუნდება
    }

}
