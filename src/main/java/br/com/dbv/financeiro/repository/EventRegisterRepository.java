package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.enums.UserTypeEnum;
import br.com.dbv.financeiro.model.EventRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRegisterRepository extends JpaRepository<EventRegister, Long> {

    List<EventRegister> findByEventId(@PathParam("event_id") Long id);

    List<EventRegister> findByEventIdAndPathfinderUserTypeInOrderByPathfinderName(@PathParam("event_id") Long id, List<UserTypeEnum> userTypes);

    Optional<EventRegister> findByEventIdAndPathfinderId(@PathParam("event_id") Long id, @PathParam("pathfinder_id") UUID pathfinderId);

}