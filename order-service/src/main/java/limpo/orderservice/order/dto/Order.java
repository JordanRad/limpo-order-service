package limpo.orderservice.order.dto;

import limpo.orderservice.client.dto.Client;
import limpo.orderservice.order.dto.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String createdAt;

    @Column(unique = true)
    private String orderNumber;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> orderItems;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    private Status status;

    private String scheduledAtString;

    private Timestamp scheduledAt;

    @Transient
    private long timestamp;

}
