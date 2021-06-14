package limpo.orderservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String name;

    private String description;

    private double price;

    public boolean equals(Product obj) {
        boolean equalsName = obj.getName() == this.name;
        boolean equalsPrice = obj.getPrice() == this.price;

        return equalsName && equalsPrice ? true:false;
    }
}
