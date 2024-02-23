package br.com.dbv.financeiro.controller;

import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.event.EventRegisterDTO;
import br.com.dbv.financeiro.dto.event.ResponseEventRegisterDTO;
import br.com.dbv.financeiro.dto.event.ResponseEventUserSubscribeDTO;
import br.com.dbv.financeiro.enums.UserTypeEnum;
import br.com.dbv.financeiro.exception.CustomException;
import br.com.dbv.financeiro.repository.EventRegisterRepository;
import br.com.dbv.financeiro.repository.EventRepository;
import br.com.dbv.financeiro.repository.UserEventBankRepository;
import br.com.dbv.financeiro.repository.UserRepository;
import br.com.dbv.financeiro.service.EventService;
import br.com.dbv.financeiro.service.UserService;
import br.com.dbv.financeiro.service.mapper.EventRegisterMapper;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/event/register")
public class EventRegisterController {

    @Autowired
    private EventRegisterRepository repository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserEventBankRepository userEventBankRepository;

    @Autowired
    private EventService eventService;

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getAllEventRegisterByEvent(@PathVariable("eventId") Long eventId, @PathParam("userType") String userType) {

        List<UserTypeEnum> typeList = new ArrayList<>();

        if (!StringUtils.isBlank(userType) && !userType.equals("ALL")) {
            try {
                var type = UserTypeEnum.valueOf(userType);
                if (type.equals(UserTypeEnum.DIRECTION) || type.equals(UserTypeEnum.EXECUTIVE)) {
                    typeList.add(UserTypeEnum.DIRECTION);
                    typeList.add(UserTypeEnum.EXECUTIVE);
                } else {
                    typeList.add(type);
                }
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(new ErrorDTO("400", "Invalid User Type", "Invalid User Type"));
            }
        } else {
            typeList.addAll(Arrays.stream(UserTypeEnum.values()).toList());
        }

        var response = repository.findByEventIdAndPathfinderUserTypeInOrderByPathfinderName(eventId, typeList);

        ArrayList<ResponseEventRegisterDTO> responseList = new ArrayList<>();

        response.forEach(eventRegister -> responseList.add(EventRegisterMapper.convert(eventRegister, userEventBankRepository)));

        return ResponseEntity.ok().body(responseList);

    }

    @GetMapping("/{eventId}/club/{clubId}")
    public ResponseEntity<?> getAllUsersSubscribes(@PathVariable("eventId") Long eventId, @PathVariable("clubId") Long clubId) {

        var eventRegisters = repository.findByEventId(eventId);

        var event = eventRepository.findById(eventId);

        if (!event.isPresent()) {
            return ResponseEntity.badRequest().body("error");
        }

        var users = userService.getUsersByClubWithEventualUsers(clubId, Boolean.TRUE, Boolean.TRUE, null);

        ArrayList<ResponseEventUserSubscribeDTO> responseList = new ArrayList<>();

        users.forEach(user -> {
                    var subscribed = Boolean.FALSE;

                    for (var er : eventRegisters) {
                        if (er.getPathfinder().getId().equals(user.getId())) {
                            subscribed = Boolean.TRUE;
                        }
                    }

                    responseList.add(ResponseEventUserSubscribeDTO.builder()
                            .event(event.get().getEvent())
                            .userId(user.getId())
                            .user(user.getName())
                            .userGender(user.getGender())
                            .subscribed(subscribed)
                            .userType(user.getUserType())
                            .build());
                }
        );

        return ResponseEntity.ok().body(responseList);

    }

    @GetMapping("/{eventId}/count")
    public ResponseEntity<?> getCountRegistersByClub(@PathVariable("eventId") Long eventId) {

        return ResponseEntity.ok().body(repository.findByEventId(eventId).size());

    }

    @PostMapping
    public ResponseEntity<?> createEventRegister(@RequestBody EventRegisterDTO request) {

        try {
            return ResponseEntity.ok().body(eventService.registerUserInEvent(request));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getError());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEventRegister(@PathVariable("id") Long id) {

        repository.delete(repository.findById(id).get());

        return new ResponseEntity<>(HttpStatus.OK);

    }

}