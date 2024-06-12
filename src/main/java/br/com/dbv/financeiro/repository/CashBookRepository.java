package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.CashBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface CashBookRepository extends JpaRepository<CashBook, Long> {

    List<CashBook> findByClubId(@PathParam("club_id") Long id);

    @Query("SELECT c FROM CashBook c WHERE " +
            "c.club.id = :clubId AND " +
            "(:eventId IS NULL OR c.event.id = :eventId)")
//            "p.date BETWEEN :startDate AND :endDate")
    List<CashBook> findByClubIdAndEventId(
            @Param("clubId") Long clubId,
            @Param("eventId") Long eventId);
//            @Param("startDate") LocalDate startDate,
//            @Param("endDate") LocalDate endDate);

}