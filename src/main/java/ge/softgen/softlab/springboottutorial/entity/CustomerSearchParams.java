package ge.softgen.softlab.springboottutorial.entity;

import lombok.Data;

@Data
public class CustomerSearchParams {
    private String firstName;
    private String lastName;
}



/*
ეს რათ მინდა...

როცა query-ით ბევრი პარამეტრია გადასაცემი ის ფუნქცია რომელიც ურთიერთობის ფაილში წერია
უნდა მოიცავდეს ბევრს პარამეტრს და ჩანაწერი იზრდება. ამიტომ შეგვიძლია ცალკე ავაწყო ეს პარამეტრები.
გავუკეთო getter setter ები. და დავაბრუნო ობიექტის სახით იქ სადაც დამჭირდება.

ანუ ჩვენ შემთხვევაში თუ იყო ესეთი ჩანაწერი:

@GetMapping("/")
    public List<Customer> getAll(@RequestParam(required=false) String firstName,
                                 @RequestParam(required=false) String lastName ) {
        return customerService.getAll(firstName, lastName);
    }


ამას გადავცემ ახლა სხვანაირ პარამეტრებს, მაგრამ 1 მინუსი ექნება. required ველს ვეღარ გამოვიყენებ.
ამიტომ ეს ფუნქცია ყოველთვის მოითხოვს სახელისა და გვარის მინიჭებას. ფაქტობრივად ფუნქციას მივეცით
ობიექტი, ამიტომ შეგვიძლია პარამეტრზე searchParams ვიმოქმედოთ როგორც ობიექტზე და ამოვიღოთ მასში
ჩადებული სახელისა და გვარის ცვლადები აი ასე:


@GetMapping()
    public List<Customer> getAll(CustomerSearchParams searchParams) {
        return customerService.getAll(searchParams);
    }


    ასეთ შემთხვევაში საჭირო ხდება CustomerService ინტერფეისის შესაბამისი ფუნქციის შეცვლაც ასეთი სახით:

    List<Customer> getAll(CustomerSearchParams searchParams);

    და რა თქმა უნდა შესაცვლელი ხდება CustomerServiceImplement ფაილში შესაბამისი ფუნქციის შეცვლაც ასე:

public List<Customer> getAll(CustomerSearchParams searchParams) {
    // ასევე შემიძლი ვითომ დესტრუქტურიზაცია შევქმნა, რომ ობიექტზე მიწვდენაც არ დამჭირდეს ყველგან.

        String firstName = searchParams.getFirstName();
        String lastName = searchParams.getLastName();
        var stream = db.stream().filter(customer -> !customer.getDeleted());

        if(firstName != null && !firstName.isEmpty()){
            stream = stream.filter(customer -> customer.getFirstName().equals(firstName));
        }
        if(lastName != null && !lastName.isEmpty()){
            stream = db.stream().filter(customer -> customer.getLastName().equals(lastName));
        }
        return stream.toList();
    }


    ასევე ასეთ ჩანაწერებს კიდევ ის პლუსი აქვს, რომ ზემოთ ცვლადის დამატება გვიწევს 1 ადგილზე მარტივად.

    დანარჩენი კი კოდის მიხედვით სადაც ეს ცვლადი დაგვჭირდება ისე უნდა ჩაემატოს როგორც ზემოთაა ნაჩვენები.
*/