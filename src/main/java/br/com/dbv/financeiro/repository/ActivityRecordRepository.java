package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.ActivityRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRecordRepository extends JpaRepository<ActivityRecord, Long> {

    List<Optional<ActivityRecord>> findByUnitId(@PathParam("unit_id") Long id);

}