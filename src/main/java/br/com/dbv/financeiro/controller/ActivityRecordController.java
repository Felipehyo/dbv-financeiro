package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ActivityRecordDTO;
import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.TotalPointsResponseDTO;
import br.com.dbv.financeiro.model.Activity;
import br.com.dbv.financeiro.model.ActivityRecord;
import br.com.dbv.financeiro.model.Unit;
import br.com.dbv.financeiro.repository.ActivitiesRepository;
import br.com.dbv.financeiro.repository.ActivityRecordRepository;
import br.com.dbv.financeiro.repository.ClubRepository;
import br.com.dbv.financeiro.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/activity-record")
public class ActivityRecordController {

    @Autowired
    private ActivityRecordRepository repository;

    @Autowired
    private ActivitiesRepository activitiesRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ClubRepository clubRepository;

    @GetMapping("/club/{clubId}")
    public ResponseEntity<?> getAllRecordsByClub(@PathVariable("clubId") Long id) {

        return ResponseEntity.ok().body(repository.findByActivityClubId(id));

    }

    @GetMapping("/unit/{unitId}")
    public ResponseEntity<?> getRecordsByUnitId(@PathVariable("unitId") Long id) {

        List<ActivityRecord> records = repository.findByUnitId(id);

        records.sort(Comparator.comparing(ActivityRecord::getCreatedDate).reversed());

        return ResponseEntity.ok().body(records);

    }

    @GetMapping("/total-points/club/{clubId}")
    public ResponseEntity<?> getTotalPoints(@PathVariable("clubId") Long clubId) {

        var club = clubRepository.findById(clubId);

        if (!club.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Club not found", "Club not found in database"));
        }

        List<Unit> units;
        try {
            units = unitRepository.findByClubId(clubId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Unit not found", "Unit not found in database"));
        }

        List<TotalPointsResponseDTO> totalPointsResponseDTO = new ArrayList<>();

        for (var unit : units) {
            var total = 0;
            List<ActivityRecord> records = repository.findByUnitId(unit.getId());

            if (records.size() == 0) {
                totalPointsResponseDTO.add(new TotalPointsResponseDTO(unit, total));
            } else {
                for (var record : records) {
                    total = total + record.getPoints();
                }
                totalPointsResponseDTO.add(new TotalPointsResponseDTO(unit, total));
            }
        }

        return ResponseEntity.ok().body(totalPointsResponseDTO);

    }

    @GetMapping("/total-points/unit/{unitId}")
    public ResponseEntity<?> getTotalPointsByUnitId(@PathVariable("unitId") Long unitId) {

        var total = 0;

        Unit unit;
        try {
            unit = unitRepository.findById(unitId).get();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Unit not found", "Unit not found in database"));
        }

        List<ActivityRecord> records = repository.findByUnitId(unitId);

        if (records.size() == 0)
            return ResponseEntity.ok().body(new TotalPointsResponseDTO(unit, total));

        for (var record : records) {
            total = total + record.getPoints();
        }

        return ResponseEntity.ok().body(new TotalPointsResponseDTO(unit, total));

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
        try {
            repository.delete(repository.findById(id).get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO("400", "Activity not found", "Activity does not exist or has already been deleted"));
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
