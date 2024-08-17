package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findByClubId(@PathParam("club_id") Long id);

    @Query("SELECT p FROM Payment p WHERE " +
            "p.club.id = :clubId AND " +
            "(COALESCE(:pathfinderId, NULL) IS NULL OR p.pathfinder.id = :pathfinderId) AND " +
            "(:eventId IS NULL OR p.event.id = :eventId)")
//            "p.date BETWEEN :startDate AND :endDate")
    Page<Payment> findByClubIdAndPathfinderIdAndEventId(
            @Param("clubId") Long clubId,
            @Param("pathfinderId") UUID pathfinderId,
            @Param("eventId") Long eventId,
            Pageable pageable);
//            @Param("startDate") LocalDate startDate,
//            @Param("endDate") LocalDate endDate);

    List<Payment> findByPathfinderId(@PathParam("pathfinder_id") UUID id);

}