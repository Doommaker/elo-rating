package com.elorating.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.*;

public class Match {

    @Id
    private String id;

    @DBRef
    @JsonIgnoreProperties("users")
    private League league;

    @DBRef
    @JsonIgnoreProperties({"user", "league"})
    private Player playerOne;

    @DBRef
    @JsonIgnoreProperties({"user", "league"})
    private Player playerTwo;

    private Map<String, Integer> scores;

    private Map<String, Integer> ratings;

    private Date date;

    private boolean completed = false;

    private int ratingDelta;

    public Match() {
        this.date = new Date();
        this.scores = new HashMap<>();
        this.ratings = new HashMap<>();
    }

    public Match(Player playerOne, Player playerTwo) {
        this();
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public Match(Player playerOne, Player playerTwo, League league) {
        this(playerOne, playerTwo);
        this.league = league;
    }

    public Match(Player playerOne, Player playerTwo, int playerOneScore, int playerTwoScore) {
        this(playerOne, playerTwo);
        this.scores.put(playerOne.getId(), playerOneScore);
        this.scores.put(playerTwo.getId(), playerTwoScore);
        this.setCompleted();
    }

    public Match(Player playerOne, Player playerTwo, int playerOneScore, int playerTwoScore, Date date) {
        this(playerOne, playerTwo, playerOneScore, playerTwoScore);
        this.date = date;
    }

    public Match(Player playerOne, Player playerTwo, int playerOneScore, int playerTwoScore, League league) {
        this(playerOne, playerTwo, playerOneScore, playerTwoScore);
        this.league = league;
    }

    public boolean isCompleted() {
        if (scores.size() != 2)
            return false;
        List<Integer> values = new ArrayList<Integer>(scores.values());
        return (values.get(0) == 2 || values.get(1) == 2)
                && values.get(0) + values.get(1) <= 3;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public int getScore(Player player) {
        return scores.get(player.getId());
    }

    public void setScore(Player player, Integer score) {
        scores.put(player.getId(), score);
    }

    public Map<String, Integer> getRatings() {
        return ratings;
    }

    public int getRating(Player player) {
        return ratings.get(player.getId());
    }

    public void setRating(Player player, Integer rating) {
        ratings.put(player.getId(), rating);
    }

    public int getRatingDelta() {
        return ratingDelta;
    }

    public void setRatingDelta(int ratingDelta) {
        this.ratingDelta = ratingDelta;
    }

    public int getRatingDelta(Player player) {
        if (getPlayerOne().getId().equals(player.getId())) {
            return ratingDelta;
        } else {
            return -ratingDelta;
        }
    }

    public void updateRatingsWithDelta(int delta) {
        ratings.put(playerOne.getId(), playerOne.getRating() + delta);
        ratings.put(playerTwo.getId(), playerTwo.getRating() - delta);
        this.ratingDelta = delta;
    }

    public void removePlayerId(String playerId) {
        if (playerOne != null && playerOne.getId().equals(playerId))
            playerOne = null;
        else if (playerTwo != null && playerTwo.getId().equals(playerId))
            playerTwo = null;

        removePlayerScore(playerId);
    }

    private void removePlayerScore(String playerId) {
        Integer score = scores.remove(playerId);
        scores.put("", score);
    }

    public void setCompleted() {
        this.completed = isCompleted();
    }

    public Player winner() {
        int playerOneScore = 0;
        int playerTwoScore = 0;
        Player emptyPlayer = new Player();
        emptyPlayer.setId("");
        if (playerOne == null && playerTwo == null) {
            return emptyPlayer;
        }
        if (playerOne == null && playerTwo != null) {
            //playerOneScore = scores.get("");
            playerTwoScore = scores.get(playerTwo.getId());
            //return playerOneScore > playerTwoScore ? emptyPlayer : playerTwo;
            return playerTwoScore == 2 ? playerTwo : emptyPlayer;
        }
        if (playerOne != null && playerTwo == null) {
            playerOneScore = scores.get(playerOne.getId());
            //playerTwoScore = scores.get("");
            //return playerOneScore > playerTwoScore ? playerOne : emptyPlayer;
            return playerOneScore == 2 ? playerOne : emptyPlayer;
        }
        if(scores.get(playerOne.getId()) > scores.get(playerTwo.getId())) {
            return playerOne;
        }

        return playerTwo;
    }

    public String getWinnerId() {
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            if (entry.getValue() == 2) {
                return entry.getKey();
            }
        }
        return null;
    }

    public String getLooserId() {
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            if (entry.getValue() != 2) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean isPlayerInMatch(String pid) {
        if (playerOne != null && playerOne.getId().equals(pid)) {
            return true;
        }
        if (playerTwo != null && playerTwo.getId().equals(pid)) {
            return true;
        }
        return false;
    }
}
