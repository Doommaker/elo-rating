package com.elorating.repository;

import com.elorating.model.Player;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("playerRepository")
public interface PlayerRepository extends MongoRepository<Player, String> {
    List<Player> findByLeagueId(String id);

    @Query(value = "{'league.id': ?0, 'active': true}")
    List<Player> getRanking(String id, Sort sort);

    List<Player> findByLeagueIdAndUsernameLikeIgnoreCase(String leagueId, String username);

    void deleteByLeagueId(String leagueId);
}
