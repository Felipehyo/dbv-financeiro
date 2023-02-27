package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    List<Unit> findByClubId(@PathParam("club_id") Long id);

}