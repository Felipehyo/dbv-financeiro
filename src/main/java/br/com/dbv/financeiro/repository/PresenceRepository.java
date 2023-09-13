package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.Presence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PresenceRepository extends JpaRepository<Presence, Long> {

    List<Presence> findByClubId(@PathParam("club_id") Long id);
    List<Optional<Presence>> findByPathfinderId(UUID id);
    List<Presence> findByClubIdAndDateEquals(@PathParam("club_id") Long id, LocalDate date);
    Optional<Presence> findByPathfinderIdAndDateEquals(UUID id, LocalDate date);

}