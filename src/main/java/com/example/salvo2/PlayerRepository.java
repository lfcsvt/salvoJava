package com.example.salvo2;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByLastName(String lastName);
}
