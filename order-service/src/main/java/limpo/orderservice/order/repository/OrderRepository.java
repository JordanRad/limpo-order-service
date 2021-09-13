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

    @Query("SELECT o FROM Order o WHERE o.orderNumber LIKE :searchInput% OR o.client.firstName LIKE :searchInput% OR o.client.lastName LIKE :searchInput% OR o.createdAt LIKE :searchInput%")
    Collection<Order> findBySearchInput(String searchInput);

    @Query("SELECT o FROM Order o WHERE o.status = :status AND o.orderNumber LIKE :searchInput% OR o.client.firstName LIKE :searchInput% OR o.client.lastName LIKE :searchInput% OR o.createdAt LIKE :searchInput%")
    Collection<Order> findBySearchInputAndStatusFilter(String searchInput, Status status);

    @Query(nativeQuery = true, value = "SELECT * FROM orders LIMIT 5 OFFSET :startIndex ;")
    Collection<Order> findAll(int startIndex);

    @Query(nativeQuery = true, value = "SELECT * FROM orders o WHERE o.status = :status LIMIT 5 OFFSET :startIndex ;")
    Collection<Order> findAllOrdersByStatus(int status,int startIndex);

    @Query("SELECT count(o) FROM Order o")
    int findAllOrdersCount();

    @Query("SELECT count(o) FROM Order o WHERE o.status = :status")
    int findAllOrdersCountByStatus(Status status);
}
