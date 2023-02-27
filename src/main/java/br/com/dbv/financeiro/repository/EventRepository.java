package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByClubId(@PathParam("club_id") Long id);

}