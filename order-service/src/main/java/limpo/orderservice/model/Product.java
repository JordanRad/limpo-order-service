package limpo.orderservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private long id;

    @Column(unique = true)
    @Getter
    @Setter
    private String name;


    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private double price;


    public boolean equals(Product obj) {
        boolean equalsName = obj.getName() == this.name;
        boolean equalsPrice = obj.getPrice() == this.price;

        return equalsName && equalsPrice ? true:false;
    }
}
