package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.ActivityRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRecordRepository extends JpaRepository<ActivityRecord, Long> {

    List<ActivityRecord> findByUnitId(@PathParam("unit_id") Long id);

    List<Optional<ActivityRecord>> findByUnitIdAndDateEquals(Long id, LocalDate date);

}