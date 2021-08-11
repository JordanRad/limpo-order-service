package limpo.orderservice.order.repository;

import limpo.orderservice.order.dto.Order;
import limpo.orderservice.order.dto.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.orderNumber = :orderNumber")
    Optional<Order> findByOrderNumber(String orderNumber);

    @Query(nativeQuery = true, value = "SELECT * FROM orders LIMIT 2 OFFSET :startIndex ;")
    Collection<Order> findAll(int startIndex);

    @Query(nativeQuery = true, value = "SELECT * FROM orders o WHERE o.status = :status LIMIT 2 OFFSET :startIndex ;")
    Collection<Order> findAllOrdersByStatus(int status,int startIndex);

    @Query("SELECT count(o) FROM Order o")
    int findAllOrdersCount();

    @Query("SELECT count(o) FROM Order o WHERE o.status = :status")
    int findAllOrdersCountByStatus(Status status);
}
