package limpo.orderservice.limpounit.repository;

import limpo.orderservice.limpounit.dto.LimpoUnit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LimpoUnitRepository extends CrudRepository<LimpoUnit,Long> {
    Optional<LimpoUnit> findByName(String name);
}
