package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ActivityDTO;
import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.model.Activity;
import br.com.dbv.financeiro.model.ActivityRecord;
import br.com.dbv.financeiro.model.Unit;
import br.com.dbv.financeiro.repository.ActivitiesRepository;
import br.com.dbv.financeiro.repository.ActivityRecordRepository;
import br.com.dbv.financeiro.repository.UnitRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivitiesRepository repository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ActivityRecordRepository activityRecordRepository;

    @GetMapping()
    public ResponseEntity<?> getAllActivities() {

        return ResponseEntity.ok().body(repository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getActivities(@PathVariable("id") Long id) {

        return ResponseEntity.ok().body(repository.findById(id));

    }

    @GetMapping("/today/{unitId}")
    public ResponseEntity<?> getDiaryRecordsByUnitId(@PathVariable("unitId") Long unitId) {

        Optional<Unit> unit = unitRepository.findById(unitId);

        if (!unit.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Unit not found", "Unit not found in database"));
        }

        List<Optional<ActivityRecord>> allRecords = activityRecordRepository.findByUnitIdAndDateEquals(unitId, LocalDate.now());
        List<Activity> activities = repository.findAll();

        for (Activity activity : repository.findAll()) {
            for (Optional<ActivityRecord> record : allRecords) {
                if (record.get().getActivity().getId() == activity.getId() && record.get().getDate().equals(LocalDate.now()) && !activity.getName().equals("Customize")) {
                    activities.remove(activity);
                    break;
                }
            }
        }

        return ResponseEntity.ok().body(activities);
    }

    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody ActivityDTO request) {

        Activity activity;
        try {
            repository.findByName(request.getName()).get();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            activity = request.convert();
            return ResponseEntity.ok().body(repository.save(activity));
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> putActivity(@PathVariable("id") Long id, @RequestBody ActivityDTO request) {

        var activity = repository.findById(id).get();
        if (!StringUtils.isBlank(request.getName())) activity.setName(request.getName());
        if (!StringUtils.isBlank(request.getDescription())) activity.setDescription(request.getDescription());
        if (request.getMerit() != null && request.getMerit() >= 0)
            activity.setMerit(request.getMerit());
        if (request.getDemerit() != null && request.getDemerit() >= 0)
            activity.setDemerit(request.getMerit());

        repository.save(activity);

        return ResponseEntity.ok().body(activity);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable("id") Long id) {

        repository.delete(repository.findById(id).get());

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
