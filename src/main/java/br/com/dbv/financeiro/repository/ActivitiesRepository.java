package br.com.dbv.financeiro.repository;

import br.com.dbv.financeiro.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActivitiesRepository extends JpaRepository<Activity, Long> {

    Optional<Activity> findByName(@PathParam("name") String name);

    List<Activity> findByClubId(@PathParam("club_id") Long id);

}