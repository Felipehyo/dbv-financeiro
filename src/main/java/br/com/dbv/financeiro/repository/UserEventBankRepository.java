package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.UserEventBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserEventBankRepository extends JpaRepository<UserEventBank, UUID> {

    Optional<UserEventBank> findByEventIdAndPathfinderId(Long eventId, UUID pathfinderId);

}