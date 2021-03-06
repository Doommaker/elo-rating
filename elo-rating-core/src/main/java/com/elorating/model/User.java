package com.elorating.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    @Id
    private String id;
    private String googleId;
    private String name;
    private String givenName;
    private String familyName;
    private String email;
    private String pictureUrl;
    private Date lastSignIn;
    private String invitationToken;
    @DBRef(lazy = true)
    @JsonIgnoreProperties("users")
    private List<League> leagues;
    @DBRef(lazy = true)
    @JsonIgnoreProperties({"user"})
    private List<Player> players;

    public User() { }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String email) {
        this(name);
        this.email = email;
    }

    public void update(User user) {
        this.name = user.name;
        this.givenName = user.givenName;
        this.familyName = user.familyName;
        this.email = user.email;
        this.pictureUrl = user.pictureUrl;
        this.lastSignIn = new Date();
    }

    public String getId() {
        return id;
    }

    public String getGoogleId() {
        return this.googleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Date getLastSignIn() {
        return lastSignIn;
    }

    public void setLastSignIn(Date lastSignIn) {
        this.lastSignIn = lastSignIn;
    }

    public List<League> getLeagues() {
        return leagues;
    }

    public void addLeague(League league) {
        if (leagues == null)
            leagues = new ArrayList<>();
        leagues.add(league);
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        if (players == null)
            players = new ArrayList<>();
        players.add(player);
    }

    public void setInvitationToken(String invitationToken) {
        this.invitationToken = invitationToken;
    }

    public String getInvitationToken() {
        return invitationToken;
    }

    public void clearInvitationToken() {
        invitationToken = null;
    }
}
