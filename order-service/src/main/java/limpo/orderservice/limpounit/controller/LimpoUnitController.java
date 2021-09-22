package limpo.orderservice.limpounit.controller;

import limpo.orderservice.limpounit.dto.LimpoUnit;
import limpo.orderservice.limpounit.repository.LimpoUnitRepository;
import limpo.orderservice.limpounit.service.LimpoUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(LimpoUnitController.BASE_URL)
public class LimpoUnitController {
    public static final String BASE_URL = "api/v1/limpoUnits";

    @Autowired
    private LimpoUnitRepository repository;

    @Autowired
    private LimpoUnitService limpoUnitService;

    @GetMapping("/")
    public ResponseEntity<?> getAllLimpoUnits() {
        List<LimpoUnit> result = limpoUnitService.getAllLimpoUnits();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLimpoUnitById(@PathVariable long id) {
        LimpoUnit limpoUnit = limpoUnitService.getLimpoUnitById(id);

        if (limpoUnit == null) {
            return new ResponseEntity<>("LimpoUnit cannot be found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(limpoUnit, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createNewLimpoUnit(@RequestBody LimpoUnit limpoUnit) {
        LimpoUnit createdLimpoUnit = limpoUnitService.createLimpoUnit(limpoUnit);

        if (createdLimpoUnit == null) {
            return new ResponseEntity<>("LimpoUnit with this name already exists", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(createdLimpoUnit, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLimpoUnit(@PathVariable Long id, @RequestBody LimpoUnit limpoUnit) {
        LimpoUnit updatedLimpoUnit = limpoUnitService.updateLimpoUnit(id, limpoUnit);

        if (updatedLimpoUnit == null) {
            return new ResponseEntity<>("LimpoUnit cannot be found", HttpStatus.NOT_FOUND);
        }
        if (updatedLimpoUnit.getName() == null) {

            return new ResponseEntity<>("LimpoUnit with this name already exists", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(updatedLimpoUnit, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLimpoUnit(@PathVariable long id) {
        LimpoUnit deletedLimpoUnit = limpoUnitService.deleteLimpoUnit(id);

        if (deletedLimpoUnit == null) {
            new ResponseEntity<>("LimpoUnit cannot be found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(deletedLimpoUnit, HttpStatus.OK);
    }
}
