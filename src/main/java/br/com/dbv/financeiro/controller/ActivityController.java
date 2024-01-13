package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ActivityDTO;
import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.model.Activity;
import br.com.dbv.financeiro.model.ActivityRecord;
import br.com.dbv.financeiro.model.Unit;
import br.com.dbv.financeiro.repository.ActivitiesRepository;
import br.com.dbv.financeiro.repository.ActivityRecordRepository;
import br.com.dbv.financeiro.repository.ClubRepository;
import br.com.dbv.financeiro.repository.UnitRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
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

    @Autowired
    private ClubRepository clubRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getActivity(@PathVariable("id") Long id) {

        return ResponseEntity.ok().body(repository.findById(id));

    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<?> getActivitiesByClub(@PathVariable("clubId") Long id) {

        return ResponseEntity.ok().body(repository.findByClubId(id));

    }

    @GetMapping("/today/{unitId}")
    public ResponseEntity<?> getDiaryRecordsByUnitId(@PathVariable("unitId") Long unitId) {

        Optional<Unit> unit = unitRepository.findById(unitId);

        if (!unit.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Unit not found", "Unit not found in database"));
        }

        List<Optional<ActivityRecord>> allRecords = activityRecordRepository.findByUnitIdAndDateEquals(unitId, LocalDate.now(ZoneId.of("America/Sao_Paulo")));
        List<Activity> activities = repository.findAll();

        for (Activity activity : repository.findAll()) {
            for (Optional<ActivityRecord> record : allRecords) {
                if (record.get().getActivity() != null && record.get().getActivity().getId() == activity.getId() && record.get().getDate().equals(LocalDate.now(ZoneId.of("America/Sao_Paulo"))) && !activity.getAlwaysDisplay()) {
                    activities.remove(activity);
                    break;
                }
            }
        }

        return ResponseEntity.ok().body(activities);
    }

    @PostMapping
    public ResponseEntity<?> createActivity(@RequestBody ActivityDTO request) {

        Activity activity;
        try {
            repository.findByName(request.getName()).get();
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Activity already registered", "Activity already registered in database"));
        } catch (Exception e) {

            var club = clubRepository.findById(request.getClubId());

            if (!club.isPresent()) {
                return ResponseEntity.badRequest().body(new ErrorDTO("400", "Club not found", "Club not found in database"));
            }

            activity = request.convert(club.get());
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
            activity.setDemerit(request.getDemerit());
        if (request.getActivityOrder() != null) activity.setActivityOrder(request.getActivityOrder());
        if (request.getAlwaysDisplay() != null) activity.setAlwaysDisplay(request.getAlwaysDisplay());

        repository.save(activity);

        return ResponseEntity.ok().body(activity);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable("id") Long id) {

        repository.delete(repository.findById(id).get());

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
