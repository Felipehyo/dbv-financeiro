package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.enums.UserTypeEnum;
import br.com.dbv.financeiro.model.Pathfinder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Pathfinder, UUID> {

    List<Optional<Pathfinder>> findByUnitIdAndActive(Long id, Boolean active);

    Optional<Pathfinder> findByEmailAndActive(String email, Boolean active);
    Optional<Pathfinder> findByName(String name);
    ArrayList<Pathfinder> findByClubIdAndActiveAndUserTypeNotOrderByName(Long id, Boolean active, UserTypeEnum eventual);
    ArrayList<Pathfinder> findByClubIdAndUserTypeNotOrderByName(Long id, UserTypeEnum eventual);
    ArrayList<Pathfinder> findByClubIdAndActiveOrderByName(Long id, Boolean active);
    ArrayList<Pathfinder> findByClubIdOrderByName(Long id);

}
