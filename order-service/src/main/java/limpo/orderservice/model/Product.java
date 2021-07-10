package limpo.orderservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String name;

    private String description;

    private double price;

//    For example - "ЧИСТЕНЕ НА ВХОДОВЕ"
    private String type;


}
