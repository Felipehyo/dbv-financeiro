package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.Activities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.Optional;

@Repository
public interface ActivitiesRepository extends JpaRepository<Activities, Long> {

    Optional<Activities> findByName(@PathParam("name") String name);

}