package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.UserMovementHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserMovementHistoryRepository extends JpaRepository<UserMovementHistory, UUID> {

    List<UserMovementHistory> findByEventIdAndPathfinderId(Long eventId, UUID pathfinderId);

}