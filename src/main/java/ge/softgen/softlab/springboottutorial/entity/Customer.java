package ge.softgen.softlab.springboottutorial.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

// Lombok
// @Getter + @Setter  ამის გამოყენებით ავტომატურად გენერირდება სეტერბი და გეტერები
// @Data - იგივეს აკეთებს რაც მე-10 ხაზზე წერია. კიდევ ამატებს სხვა მეთოდებსაც.
// შეგიძლია ნახო target file -ში, ამ ფაილის ანალოგი. იქ ყველაფერი წერია.

@Data

public class Customer {
    private Integer id;
    private String firstName, lastName;
    private LocalDate birthDate;
    private Boolean deleted;
}
