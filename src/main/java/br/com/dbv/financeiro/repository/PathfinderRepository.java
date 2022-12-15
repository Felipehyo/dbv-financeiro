package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.Pathfinder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PathfinderRepository extends JpaRepository<Pathfinder, UUID> {

    List<Optional<Pathfinder>> findByUnitId(Long id);

    Optional<Pathfinder> findByEmail(String email);

}