package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PathfinderRepository extends JpaRepository<User, UUID> {

    List<Optional<User>> findByUnitId(Long id);

    Optional<User> findByEmail(String email);

}