package limpo.orderservice.limpounit.service;

import limpo.orderservice.limpounit.dto.LimpoUnit;
import limpo.orderservice.limpounit.repository.LimpoUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LimpoUnitService {
    @Autowired
    private LimpoUnitRepository limpoUnitRepository;

    /**
     * Get all limpoUnits
     *
     * @return ArrayList of limpoUnits
     */
    public ArrayList<LimpoUnit> getAllLimpoUnits() {
        return (ArrayList<LimpoUnit>) limpoUnitRepository.findAll();
    }

    /**
     * Get a limpoUnit by name
     *
     * @return LimpoUnit
     */
    public LimpoUnit getLimpoUnitByName(String name) {
        return limpoUnitRepository.findByName(name).orElse(null);
    }

    /**
     * Get a limpoUnit by id
     *
     * @return LimpoUnit
     */
    public LimpoUnit getLimpoUnitById(Long id) {
        return limpoUnitRepository.findById(id).orElse(null);
    }

    /**
     * Create a limpoUnit
     *
     * @return Created limpoUnit
     */
    public LimpoUnit createLimpoUnit(LimpoUnit limpoUnit) {
        LimpoUnit result;

        try {
            result = limpoUnitRepository.save(limpoUnit);
        } catch (Exception e) {
            return null;
        }

        return result;
    }

    /**
     * Update a limpoUnit
     *
     * @return Updated limpoUnit
     */
    public LimpoUnit updateLimpoUnit(long id, LimpoUnit obj) {
        LimpoUnit updatedLimpoUnit = limpoUnitRepository.findById(id).orElse(null);

        if (updatedLimpoUnit == null) {
            return null;
        }
        updatedLimpoUnit.setName(obj.getName());
        updatedLimpoUnit.setDescription(obj.getDescription());

        try {
            limpoUnitRepository.save(updatedLimpoUnit);
        } catch (Exception e) {
            return new LimpoUnit();
        }

        return updatedLimpoUnit;
    }

    /**
     * Delete a limpoUnit
     *
     * @return Deleted limpoUnit
     */
    public LimpoUnit deleteLimpoUnit(Long id) {
        LimpoUnit limpoUnit = limpoUnitRepository.findById(id).orElse(null);

        if (limpoUnit != null) {
            limpoUnitRepository.deleteById(id);
            return limpoUnit;
        }

        return null;
    }
}
