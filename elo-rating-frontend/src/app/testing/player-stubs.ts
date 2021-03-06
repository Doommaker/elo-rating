import { Observable } from 'rxjs/Observable';
import { OpponentStats } from './../players/shared/opponent-stats.model';
import { Match } from './../matches/shared/match.model';
import { PLAYERS} from './data/players';
import { Player } from './../players/shared/player.model';
import { Injectable, OnInit, Component, Input } from '@angular/core';

@Injectable()
export class PlayerServiceStub {

  getPlayers(league_id: string): Promise<Player[]> {
    return Promise.resolve(PLAYERS);
  }

  getPlayer(id: string): Promise<Player> {
    return Promise.resolve(PLAYERS.find(player => player.id === id));
  }

  getRanking(leagueId: string): Promise<Player[]> {
    return Promise.resolve(PLAYERS
      .sort((playerOne, playerTwo) => playerTwo.rating - playerOne.rating)
      .filter(player => player.active == true));
  }

  addPlayer(leagueId: string, player: Player): Promise<Player> {
    player.id = '111';
    player.rating = 1000;
    PLAYERS.push(player);
    return Promise.resolve(player);
  }

  delete(leagueId: string, playerId: string): Promise<boolean> {
    PLAYERS.splice(0, 1);
    return Promise.resolve(true);
  }

  update(leagueId: string, player: Player): Promise<Player> {
    let playerToUpdate = PLAYERS.find(p => p.id == player.id)
    playerToUpdate.username = player.username;
    playerToUpdate.rating = player.rating;
    return Promise.resolve(playerToUpdate);
  }

  getMatchForecast(playerId: string, opponentId: string): Promise<Match[]> {
    let player = PLAYERS.find(p => p.id == playerId);
    let opponent = PLAYERS.find(p => p.id == playerId);
    let score = [[2, 0], [2, 1], [1, 2], [0, 2]];
    let ratio = [20, 10, -10, -20];
    let matches = [];
    for(let i = 0; i < 4; i++) {
      let match = new Match();
      match.playerOne = player;
      match.playerTwo = opponent;
      match.scores = {playerId: score[i][0], opponentId: score[i][1]}
      match.ratingDelta = ratio[i];
      matches.push(match);
    }
    return Promise.resolve(matches);
  }

  getOpponentsStats(playerId: string): Promise<OpponentStats[]> {
    // TODO create mock data for opponents stats
    return Promise.resolve(null);
  }

  findByUsername(leagueId: string, username: string): Observable<Player[]> {
    return Observable.of(PLAYERS);
  }
}

@Component({
  selector: 'app-player-matches',
  template: ''
})
export class PlayerMatchesStubComponent implements OnInit {
  @Input() leagueId: string;
  @Input() playerId: string;

  constructor() { }
  ngOnInit() { }
}

@Component({
  selector: 'app-player-statistics',
  template: ''
})
export class PlayerStatisticsStubComponent implements OnInit {
  @Input() leagueId: string;
  @Input() playerId: string;

  constructor() { }
  ngOnInit() { }
}

@Component({
  selector: 'app-player-ranking',
  template: ''
})
export class PlayerRankingStubComponent implements OnInit {
  @Input() leagueId;
  constructor() { }
  ngOnInit() { }
}

@Component({
  selector: 'app-player-forecast',
  template: ''
})
export class PlayerForecastStubComponent implements OnInit {
  @Input() leagueId;
  @Input() playerId;
  constructor() { }
  ngOnInit() { }
}

@Component({
  selector: 'app-player-opponents',
  template: ''
})
export class PlayerOpponentsStubComponent implements OnInit {
  @Input() leagueId;
  @Input() playerId;
  ngOnInit() {}
}

@Component({
  selector: 'app-player-cell',
  template: '{{player?.username}}'
})
export class PlayerCellStubComponent implements OnInit {
  @Input() match: Match;
  @Input() player: Player;
  @Input() currentPlayerId: string;
  constructor() {};
  ngOnInit() {}
}

@Component({
  selector: 'app-player-user-info',
  template: ''
})
export class PlayerUserInfoStubComponent implements OnInit {
  @Input() player: Player;
  constructor() {};
  ngOnInit() {}
}
