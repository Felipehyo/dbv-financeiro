package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.Club;
import br.com.dbv.financeiro.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
}