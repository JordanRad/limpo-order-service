package limpo.orderservice.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String firstName;
    private String familyName;
    private String address;
    private Long bulstat;


    public Client(Long id, String email, String firstName, String familyName, String address, Long bulstat) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.familyName = familyName;
        this.address = address;
        this.bulstat = bulstat;
    }

    public Client() {
    }
}
