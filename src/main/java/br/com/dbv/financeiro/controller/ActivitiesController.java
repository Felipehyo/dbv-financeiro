package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ActivityDTO;
import br.com.dbv.financeiro.model.Activities;
import br.com.dbv.financeiro.repository.ActivitiesRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activities")
public class ActivitiesController {

    @Autowired
    private ActivitiesRepository repository;

    @GetMapping()
    public ResponseEntity<?> getAllActivities() {

        return ResponseEntity.ok().body(repository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getActivities(@PathVariable("id") Long id) {

        return ResponseEntity.ok().body(repository.findById(id));

    }

    @PostMapping
    public ResponseEntity<Activities> createActivity(@RequestBody ActivityDTO request) {

        Activities activity;
        try {
            repository.findByName(request.getName()).get();
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e) {
            activity = request.convert();
            return ResponseEntity.ok().body(repository.save(activity));
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Activities> putActivity(@PathVariable("id") Long id, @RequestBody ActivityDTO request) {

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
