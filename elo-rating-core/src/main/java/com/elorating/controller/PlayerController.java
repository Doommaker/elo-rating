package com.elorating.controller;

import com.elorating.model.*;
import com.elorating.service.MatchService;
import com.elorating.service.PlayerService;
import com.elorating.service.PlayerStatsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value = "players", description = "Players API")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MatchService matchService;

    @Autowired
    private PlayerStatsService playerStatsService;

    @CrossOrigin
    @RequestMapping(value = "/leagues/{leagueId}/players", method = RequestMethod.GET)
    @ApiOperation(value = "Get players list", notes = "Return players list by league id")
    public ResponseEntity<List<Player>> get(@PathVariable String leagueId) {
        List<Player> player = playerService.findByLeagueId(leagueId);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/players/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get player", notes = "Return player by player id")
    public ResponseEntity<Player> getById(@PathVariable String id) {
        Player player = playerService.getById(id);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/leagues/{leagueId}/players/ranking", method = RequestMethod.GET)
    @ApiOperation(value = "Get players ranking", notes = "Return active players list by league id")
    public ResponseEntity<List<Player>> getRanking(@PathVariable String leagueId) {
        Sort sortByRating = new Sort(Sort.Direction.DESC, "rating");
        List<Player> ranking = playerService.getRanking(leagueId, sortByRating);
        return new ResponseEntity<>(ranking, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/leagues/{leagueId}/players", method = RequestMethod.POST)
    @ApiOperation(value = "Create player", notes = "Create player")
    public ResponseEntity<Player> create(@PathVariable String leagueId, @RequestBody Player player) {
        player.setLeague(new League(leagueId));
        player = playerService.save(player);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/leagues/{leagueId}/players/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Edit player", notes = "Edit player by player id")
    public ResponseEntity<Player> edit(@PathVariable String id, @RequestBody Player player) {
        Player currentPlayer = playerService.getById(id);;
        player.setLeague(currentPlayer.getLeague());
        player = playerService.save(player);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/leagues/{leagueId}/players/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Remove player", notes = "Remove player by player id")
    public ResponseEntity<Player> delete(@PathVariable String id) {
        removePlayerFromMatches(id);
        playerService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void removePlayerFromMatches(String playerId) {
        List<Match> matches = matchService.findByPlayerId(playerId);
        for (Match match : matches) {
            match.removePlayerId(playerId);
        }

        matchService.save(matches);
    }

    @CrossOrigin
    @RequestMapping(value = "/leagues/{leagueId}/players/find-by-username", method = RequestMethod.GET)
    @ApiOperation(value = "Find by username", notes = "Find player by username and league")
    public ResponseEntity<List<Player>> findByUsernameAndLeague(@PathVariable String leagueId,
                                                                @RequestParam String username) {
        List<Player> players = playerService.findByLeagueIdAndUsernameLikeIgnoreCase(leagueId, username);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/players/{playerId}/opponents/{opponentId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get player stats against a specific opponent", notes = "Returns player stats for player id against opponent id")
    public ResponseEntity<OpponentStats> getPlayerStatsAgainstOpponent(@PathVariable("playerId") String playerId, @PathVariable("opponentId") String opponentId) {
        OpponentStats opponentStats = playerStatsService.getOpponentStats(playerId, opponentId);

        if (opponentStats == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(opponentStats, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/players/{playerId}/opponents", method = RequestMethod.GET)
    @ApiOperation(value = "Get player stats against all opponents", notes = "Returns player stats for player id against all opponents")
    public ResponseEntity<List<OpponentStats>> getPlayerStatsAgainstOpponents(@PathVariable("playerId") String playerId) {
        Player player = playerService.getById(playerId);
        List<Player> opponents = playerService.findByLeagueId(player.getLeague().getId());

        ArrayList<OpponentStats> opponentStatsList = new ArrayList<>(opponents.size());
        for (Player opponent : opponents) {
            if (!opponent.getId().equals(player.getId())) {
                opponentStatsList.add(playerStatsService.getOpponentStats(playerId, opponent.getId()));
            }
        }

        return new ResponseEntity<>(opponentStatsList, HttpStatus.OK);
    }
}
