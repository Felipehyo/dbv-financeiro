package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.Pathfinder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Pathfinder, UUID> {

    List<Optional<Pathfinder>> findByUnitIdAndActive(Long id, Boolean active);

    Optional<Pathfinder> findByEmailAndActive(String email, Boolean active);
    Optional<Pathfinder> findByName(String name);

    List<Pathfinder> findByClubIdAndActive(@PathParam("club_id") Long id, Boolean active);

}