package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.Kit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitRepository extends JpaRepository<Kit, Long> {
}