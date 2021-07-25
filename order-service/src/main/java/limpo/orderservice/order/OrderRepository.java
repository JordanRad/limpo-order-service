package limpo.orderservice.order;

import limpo.orderservice.order.Order;
import limpo.orderservice.order.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order,Long> {
    @Query("SELECT o FROM Order o WHERE o.status = :status")
    Collection<Order> findAllOrdersByStatus(Status status);

    @Query("SELECT o FROM Order o WHERE o.orderNumber = :orderNumber")
    Optional<Order> findByOrderNumber(String orderNumber);
}
