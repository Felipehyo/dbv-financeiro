package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.EventRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRegisterRepository extends JpaRepository<EventRegister, Long> {

    List<EventRegister> findByEventId(@PathParam("event_id") Long id);

    EventRegister findByEventIdAndPathfinderId(@PathParam("event_id") Long id, @PathParam("pathfinder_id") UUID pathfinderId);

}