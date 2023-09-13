package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findByClubId(@PathParam("club_id") Long id);

    List<Payment> findByPathfinderId(@PathParam("pathfinder_id") UUID id);

}