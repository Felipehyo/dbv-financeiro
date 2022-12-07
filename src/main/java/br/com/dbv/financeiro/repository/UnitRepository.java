package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.Units;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Units, Long> {
}