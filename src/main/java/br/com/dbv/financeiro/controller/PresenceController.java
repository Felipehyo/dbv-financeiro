package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.PresenceDTO;
import br.com.dbv.financeiro.dto.PresencePercentDTO;
import br.com.dbv.financeiro.dto.PresenceTodayDTO;
import br.com.dbv.financeiro.enums.PresenceTypeEnum;
import br.com.dbv.financeiro.model.Kit;
import br.com.dbv.financeiro.model.Pathfinder;
import br.com.dbv.financeiro.model.Presence;
import br.com.dbv.financeiro.repository.KitRepository;
import br.com.dbv.financeiro.repository.PathfinderRepository;
import br.com.dbv.financeiro.repository.PresenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/presence")
public class PresenceController {

    @Autowired
    private PresenceRepository repository;

    @Autowired
    private PathfinderRepository pathfinderRepository;

    @Autowired
    private KitRepository kitRepository;

    @GetMapping()
    public ResponseEntity<?> getAllPresences() {

        return ResponseEntity.ok().body(repository.findAll());

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPresencesWithPercent() {

        List<Presence> all = repository.findAll();
        List<Pathfinder> users = pathfinderRepository.findAll();
        List<PresencePercentDTO> percents = new ArrayList<>();
        List<String> countDates = new ArrayList<>();

        for (var p : all) {
            if (!countDates.contains(p.getDate().toString())) {
                countDates.add(p.getDate().toString());
            }
        }

        for (var user : users) {
            var countPresences = 0;
            for (var p : all) {
                if (user.getId().equals(p.getPathfinder().getId()) && p.getPresenceType() == PresenceTypeEnum.PRESENT) {
                    countPresences++;
                }
            }
            Integer percent = (100 / countDates.size()) * countPresences;
            percents.add(new PresencePercentDTO(user, percent));
        }


        return ResponseEntity.ok().body(percents);

    }

    @GetMapping("/today")
    public ResponseEntity<?> getAllPresencesToday() {

        List<Pathfinder> allUsers = pathfinderRepository.findAll();

        List<Presence> presenceToday = repository.findByDateEquals(LocalDate.now());

        List<PresenceTodayDTO> presenceList = new ArrayList<>();

        allUsers.forEach(user -> {

            PresenceTypeEnum typeEnum = null;

            if (presenceToday.size() != 0) {
                for (var p : presenceToday) {
                    if (user.getId() == p.getPathfinder().getId()) {
                        typeEnum = p.getPresenceType();
                        break;
                    }
                }
            }

            presenceList.add(new PresenceTodayDTO(typeEnum, user));

        });

        presenceList.sort(Comparator.comparing(PresenceTodayDTO::getUserName));

        return ResponseEntity.ok().body(presenceList);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getPathfinderById(@PathVariable("userId") UUID userId) {

        return ResponseEntity.ok().body(repository.findByPathfinderId(userId));

    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> createPresence(@PathVariable("userId") UUID userId, @RequestBody PresenceDTO request) {

        Optional<Pathfinder> pathfinder = pathfinderRepository.findById(userId);

        if (!pathfinder.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("400", "Pathfinder not found", "Pathfinder not found in database"));
        }

        Kit kit;

        Optional<Presence> oldPresence = repository.findByPathfinderIdAndDateEquals(userId, LocalDate.now());

        if (!oldPresence.isPresent()) {
            if (request.getPresenceType() == PresenceTypeEnum.PRESENT) {
                kit = kitRepository.save(request.getKit().convert());
            } else {
                kit = new Kit();
                kit.setBible(Boolean.FALSE);
                kit.setBibleStudy(Boolean.FALSE);
                kit.setPencil(Boolean.FALSE);
                kit.setCap(Boolean.FALSE);
                kit.setBottle(Boolean.FALSE);
                kit.setScarf(Boolean.FALSE);
                kit.setActivityNotebook(Boolean.FALSE);
                kitRepository.save(kit);
            }
            Presence presence = request.convert(pathfinder.get(), kit);
            return ResponseEntity.ok().body(repository.save(presence));
        } else {
            if (request.getPresenceType() == PresenceTypeEnum.PRESENT) {
                kit = oldPresence.get().getKit();
                kit.setBible(request.getKit().getBible());
                kit.setBibleStudy(request.getKit().getBibleStudy());
                kit.setPencil(request.getKit().getPencil());
                kit.setCap(request.getKit().getCap());
                kit.setBottle(request.getKit().getBottle());
                kit.setScarf(request.getKit().getScarf());
                kit.setActivityNotebook(request.getKit().getActivityNotebook());
                kitRepository.save(kit);
            } else {
                kit = oldPresence.get().getKit();
                kit.setBible(Boolean.FALSE);
                kit.setBibleStudy(Boolean.FALSE);
                kit.setPencil(Boolean.FALSE);
                kit.setCap(Boolean.FALSE);
                kit.setBottle(Boolean.FALSE);
                kit.setScarf(Boolean.FALSE);
                kit.setActivityNotebook(Boolean.FALSE);
                kitRepository.save(kit);
            }
            oldPresence.get().setKit(kit);
            oldPresence.get().setPresenceType(request.getPresenceType());
            return ResponseEntity.ok().body(repository.save(oldPresence.get()));
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePresence(@PathVariable("id") Long id) {

        repository.delete(repository.findById(id).get());

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
