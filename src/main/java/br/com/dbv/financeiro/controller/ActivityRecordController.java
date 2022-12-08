package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ActivityRecordDTO;
import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.model.Activity;
import br.com.dbv.financeiro.model.ActivityRecord;
import br.com.dbv.financeiro.model.Unit;
import br.com.dbv.financeiro.repository.ActivitiesRepository;
import br.com.dbv.financeiro.repository.ActivityRecordRepository;
import br.com.dbv.financeiro.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/activity-record")
public class ActivityRecordController {

    @Autowired
    private ActivityRecordRepository repository;

    @Autowired
    private ActivitiesRepository activitiesRepository;

    @Autowired
    private UnitRepository unitRepository;

    @GetMapping()
    public ResponseEntity<?> getAllRecords() {

        return ResponseEntity.ok().body(repository.findAll());

    }

    @GetMapping("/{unitId}")
    public ResponseEntity<?> getRecordsByUnitId(@PathVariable("unitId") Long id) {

        return ResponseEntity.ok().body(repository.findByUnitId(id));

    }

    @GetMapping("/total-points/{unitId}")
    public ResponseEntity<?> getTotalPointsByUnitId(@PathVariable("unitId") Long unitId) {

        var total = 0;

        Unit unit;
        try {
            unit = unitRepository.findById(unitId).get();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Unit not found", "Unit not found in database"));
        }

        List<Optional<ActivityRecord>> records = repository.findByUnitId(unitId);

        if (records.size() == 0)
            return ResponseEntity.ok().body(new TotalPointsResponseDTO(unit.getName(), total));

        for (Optional<ActivityRecord> record : records) {
            total = total + record.get().getPoints();
        }

        return ResponseEntity.ok().body(new TotalPointsResponseDTO(unit.getName(), total));

    }

    @PostMapping("/unit/{unitId}/activity/{activityId}")
    public ResponseEntity<?> createRecord(@RequestBody ActivityRecordDTO request, @PathVariable("unitId") Long unitId, @PathVariable("activityId") Long activityId) {

        ActivityRecord record;

        Unit unit;
        try {
            unit = unitRepository.findById(unitId).get();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Unit not found", "Unit not found in database"));
        }

        Activity activity;
        try {
            activity = activitiesRepository.findById(activityId).get();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Activity not found", "Activity not found in database"));
        }

        record = request.convert(unit, activity);

        return ResponseEntity.ok().body(repository.save(record));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecord(@PathVariable("id") Long id) {

        repository.delete(repository.findById(id).get());

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
