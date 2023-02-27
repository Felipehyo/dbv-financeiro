package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.CashBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface CashBookRepository extends JpaRepository<CashBook, Long> {

    List<CashBook> findByClubId(@PathParam("club_id") Long id);

}