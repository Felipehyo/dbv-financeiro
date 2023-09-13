package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.dto.EventDTO;
import br.com.dbv.financeiro.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByClubId(@PathParam("club_id") Long id);

    @Query("select new br.com.dbv.financeiro.dto.EventDTO(e, COUNT(er.id)) from Event e LEFT JOIN EventRegister er ON e.id = er.event.id WHERE e.club.id = :id GROUP BY e.id")
    List<EventDTO> getEventsAndRegisterByClub(@PathParam("id") Long id);

}