package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.User;
import br.com.dbv.financeiro.model.Presence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PresenceRepository extends JpaRepository<Presence, Long> {

    List<Optional<User>> findByPathfinderId(UUID id);
    List<Presence> findByDateEquals(LocalDate date);
    Optional<Presence> findByPathfinderIdAndDateEquals(UUID id, LocalDate date);

}