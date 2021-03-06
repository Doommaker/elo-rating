package com.elorating.controller;

import com.elorating.algorithm.Elo;
import com.elorating.model.Match;
import com.elorating.model.Player;
import com.elorating.service.MatchService;
import com.elorating.service.PlayerService;
import com.elorating.utils.SortUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value = "Player matches", description = "Player matches API")
public class PlayerMatchesController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MatchService matchService;

    @CrossOrigin
    @RequestMapping(value = "/players/{playerId}/matches", method = RequestMethod.GET)
    @ApiOperation(value = "Get player matches list", notes = "Return matches list by player id")
    public ResponseEntity<List<Match>> getPlayerMatches(@PathVariable String playerId,
                                                        @RequestParam(required = false) String sort) {
        Sort sortByDate = SortUtils.getSort(sort);
        List<Match> matches = matchService.findByPlayerId(playerId, sortByDate);
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/players/{playerId}/completed-matches", method = RequestMethod.GET)
    @ApiOperation(value = "Get player completed matches page",
            notes = "Return page with player's completed matches by player id")
    public ResponseEntity<Page<Match>> getPlayerCompletedMatches(@PathVariable String playerId,
                                                                 @RequestParam int page,
                                                                 @RequestParam(defaultValue = "10") int pageSize,
                                                                 @RequestParam(required = false) String sort) {
        Sort sortByDate = SortUtils.getSort(sort);
        PageRequest pageRequest = new PageRequest(page, pageSize, sortByDate);
        Page<Match> matches = matchService.findCompletedByPlayerId(playerId, pageRequest);
        return new ResponseEntity<>(matches, HttpStatus.OK);

    }

    @CrossOrigin
    @RequestMapping(value = "/players/{playerId}/completed-matches-by-date", method = RequestMethod.GET)
    @ApiOperation(value = "Get player completed matches filtered by date",
            notes = "Return list with player's completed matches by player id and date")
    public ResponseEntity<List<Match>> getPlayerCompletedMatchesByDate(
            @PathVariable String playerId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
        List<Match> matches;
        Sort sort = SortUtils.getSortAscending();
        if (from != null && to != null)
            matches = matchService.findCompletedByPlayerIdAndDate(playerId, from, to, sort);
        else if (from != null)
            matches = matchService.findCompletedByPlayerIdAndDate(playerId, from, sort);
        else
            matches = matchService.findCompletedByPlayerId(playerId, sort);

        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/players/{playerId}/scheduled-matches", method = RequestMethod.GET)
    @ApiOperation(value = "Get player scheduled matches page",
            notes = "Return page with player's scheduled matches by player id")
    public ResponseEntity<List<Match>> getPlayerScheduledMatches(@PathVariable String playerId,
                                                                 @RequestParam(required = false) String sort) {
        Sort sortByDate = SortUtils.getSort(sort);
        List<Match> matches = matchService.findScheduledByPlayerId(playerId, sortByDate);
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/players/{playerId}/matches/{opponentId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get player matches list against a specific opponent", notes = "Return matches between two players")
    public ResponseEntity<List<Match>> getPlayerMatchesAgainstOpponent(@PathVariable String playerId, @PathVariable String opponentId,
                                                                       @RequestParam(required = false) String sort) {
        Sort sortByDate = SortUtils.getSort(sort);
        List<Match> matches = matchService.findCompletedByPlayerIds(playerId, opponentId, sortByDate);
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/players/{playerId}/match-forecast/{opponentId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get match forecast against a specific opponent",
            notes = "Return list of matches with all possible scores")
    public ResponseEntity<List<Match>> getMatchForecast(@PathVariable String playerId,
                                                        @PathVariable String opponentId) {
        Player player = playerService.getById(playerId);
        Player opponent = playerService.getById(opponentId);
        List<Match> matches = generateForecast(player, opponent);
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    private List<Match> generateForecast(Player player, Player opponent) {
        List<Match> matches = new ArrayList<>();
        Elo elo = new Elo(new Match(player, opponent, 2, 0));
        matches.add(elo.getMatch());
        elo = new Elo(new Match(player, opponent, 2, 1));
        matches.add(elo.getMatch());
        elo = new Elo(new Match(player, opponent, 1, 2));
        matches.add(elo.getMatch());
        elo = new Elo(new Match(player, opponent, 0, 2));
        matches.add(elo.getMatch());
        return matches;
    }
}
