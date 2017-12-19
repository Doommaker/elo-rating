package com.elorating.service;

import com.elorating.model.Player;
import com.elorating.repository.PlayerRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("playerService")
public class PlayerServiceImpl implements PlayerService {

    @Resource
    private PlayerRepository playerRepository;

    @Override
    public Player getById(String id) {
        return playerRepository.findOne(id);
    }

    @Override
    public List<Player> getAll() {
        return playerRepository.findAll();
    }

    @Override
    public Player save(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public List<Player> save(Iterable<Player> players) {
        return playerRepository.save(players);
    }

    @Override
    public void deleteById(String id) {
        playerRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        playerRepository.deleteAll();
    }

    @Override
    public List<Player> findByLeagueId(String id) {
        return playerRepository.findByLeagueId(id);
    }

    @Override
    public List<Player> getRanking(String id, Sort sort) {
        return playerRepository.getRanking(id, sort);
    }

    @Override
    public List<Player> findByLeagueIdAndUsernameLikeIgnoreCase(String leagueId, String username) {
        return playerRepository.findByLeagueIdAndUsernameLikeIgnoreCase(leagueId, username);
    }
}