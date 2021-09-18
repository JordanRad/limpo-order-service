package limpo.orderservice.limpounit.repository;

import limpo.orderservice.limpounit.dto.LimpoUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LimpoUnitRepository extends JpaRepository<LimpoUnit,Long> {
    Optional<LimpoUnit> findByName(String name);
}
