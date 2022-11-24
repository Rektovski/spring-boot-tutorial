package ge.softgen.softlab.springboottutorial.controller;

import ge.softgen.softlab.springboottutorial.entity.Customer;
import ge.softgen.softlab.springboottutorial.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

// @RequestMapping - გვეხმარება საბაზისო ლინკის შენახვაში. ანუ ამას რო გავწერ კოდში აღარ მომიწევს ბაზის წერა
// მაგალითად - @RequestMapping("/customers")
// მაშინ კოდში რომელიმე მოქმედებაზე დავწერდი
//  @RequestMapping("/{id}") და არა @RequestMapping("/customers/{id}")


@RequestMapping("/customers")
@RestController
public class CustomerController {
    private static int id = 1;
    private List<Customer> db = new ArrayList<>();

    @GetMapping("/")
    public List<Customer> getAll() {
        return db;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable int id){
        try {
//            Customer foundedCustomer = getCustomer(id); // optional
            return ResponseEntity.ok(getCustomer(id)); // თუ კლიენტი არსებობს კლიენტის მონაცემი + კოდი 200
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

//      ამით შემიძლია წაშლილი და წაუშლელი კლიენტების გადალაგება.
//    და თუ ამ მეთოდს გამოვიყენ სერვერიდან მონაცემის წამოღებისას, მაშინ მხოლოდ
//    წაუშლელი კლიენტების სია დამიბრუნდება.1
//    @GetMapping("/customers")
//    public List<Customer> getAll() {
//        List<Customer> notDeletedCustomers = new ArrayList<>();
//        for(Customer i: db){
//            // თუ წაშლილი არაა შემიგროვე ახალ ლისთში
//            if(!i.getDeleted()){
//                notDeletedCustomers.add(i);
//            }
//        }
//        return notDeletedCustomers;
//    }
//
//    უკეთესი ვარიანტი
//    @GetMapping("/customers")
//    public List<Customer> getAll() {
//        return db.stream().filter(customer -> !customer.getDeleted()).toList();
//    }

    @PostMapping()
    public ResponseEntity<Customer> add(@RequestBody Customer customer) {
        customer.setId(id++);
        customer.setDeleted(false);
        db.add(customer);
        var location = UriComponentsBuilder.fromPath("/customers/"+id).build().toUri();
        return ResponseEntity.created(location).body(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Customer customer, @PathVariable int id){
        try {
            Customer foundedCustomer = getCustomer(id); // optional
            foundedCustomer.setDeleted(true);
            foundedCustomer.setFirstName(customer.getFirstName());
            foundedCustomer.setLastName(customer.getLastName());
            foundedCustomer.setBirthDate(customer.getBirthDate());
            return ResponseEntity.ok(foundedCustomer); // 204 წარმატებით დასრულდა და არაფერი ბრუნდება
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable int id){
        try {
            Customer foundedCustomer = getCustomer(id); // optional
            foundedCustomer.setDeleted(true);
            return ResponseEntity.noContent().build(); // 204 წარმატებით დასრულდა და არაფერი ბრუნდება
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private Customer getCustomer(int id) throws NotFoundException {
        var optional = db.stream().filter(customer -> customer.getId()==id).findFirst();
        if(optional.isEmpty()){
            throw new NotFoundException("Customer not found");
        }
        return optional.get();
    }
}
